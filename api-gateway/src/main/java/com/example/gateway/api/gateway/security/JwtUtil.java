package com.example.gateway.api.gateway.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.key}")
    private String keyStorePath;
    @Value("${jwt.password}")
    private String keyStorePassword;
    @Value("${jwt.key-alias}")
    private String keyAlias;

    private RSAPrivateKey jwtSigningKey;
    private RSAPublicKey jwtValidationKey;
    private JWTVerifier jwtVerifier;

    @PostConstruct
    public void init() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            keyStore.load(inputStream, keyStorePassword.toCharArray());

            Key key = keyStore.getKey(keyAlias, keyStorePassword.toCharArray());
            if (key instanceof RSAPrivateKey) {
                jwtSigningKey = (RSAPrivateKey) key;
            }

            PublicKey publicKey = keyStore.getCertificate(keyAlias).getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                jwtValidationKey = (RSAPublicKey) publicKey;
            }

            jwtVerifier = JWT.require(Algorithm.RSA256(jwtValidationKey, jwtSigningKey)).build();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e) {
            log.error("unable to load keystore", e);
        }
    }

    public void validateToken(String token) {
        Date expiresAt = jwtVerifier.verify(token).getExpiresAt();
        log.info("token expires at {}", expiresAt);
    }

    public Authentication getAuthentication(String token){
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim authoritiesClaims = claims.get("authorities");
        Map<String, Object> claimsMap = new HashMap<>();
        claims.forEach((key, value) -> claimsMap.put(key, value));
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "RSA256");
        headers.put("typ","JWT");
        Collection<? extends GrantedAuthority> authorities = authoritiesClaims == null? AuthorityUtils.NO_AUTHORITIES : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaims.asString());

        Jwt jwt = new Jwt(decodedJWT.getToken(), decodedJWT.getIssuedAt().toInstant(), decodedJWT.getExpiresAt().toInstant(), headers, claimsMap);
        return new JwtAuthenticationToken(jwt);
    }

    public TokenResponse createToken(Authentication authentication){

        String userName = authentication.getName();
        Map<String,String > claims = new HashMap<>();
        claims.put("username", userName);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", authorities);

        String token = JWT.create().withSubject(userName)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(900)))
                .withIssuedAt(Date.from(Instant.now()))
                .withNotBefore(Date.from(Instant.now()))
                .sign(Algorithm.RSA256(jwtValidationKey,jwtSigningKey));
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccess_token(token);
        tokenResponse.setExpiresAt(Date.from(Instant.now().plusSeconds(900)));
        tokenResponse.setIssuedAt(Date.from(Instant.now()));
        return tokenResponse;

    }
}
