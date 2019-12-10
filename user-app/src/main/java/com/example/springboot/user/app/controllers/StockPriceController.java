package com.example.springboot.user.app.controllers;

import com.example.springboot.user.app.entity.UserProfile;
import com.example.springboot.user.app.models.*;
import com.example.springboot.user.app.service.LoginService;
import com.example.springboot.user.app.service.ProfileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Collections;

@RestController
public class StockPriceController {

    private static final Logger logger = LogManager.getLogger(StockPriceController.class);


    @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Mono<StockQueryResponse> signUp(@PathVariable("symbol") String symbol) {

        WebClient client = WebClient.builder()
                .baseUrl("https://investors-exchange-iex-trading.p.rapidapi.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.filter(logRequest())
                .build();

		System.out.println(Instant.now().toString());
		Mono<StockQueryResponse> response = client
				.get()
				.uri("/stock/{symbol}/book", symbol)
				.header("X-RapidAPI-Key", "10f222ca5fmsh72c4fdcda0071d8p10c9aajsn8975f5aa04c3")
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse ->
						Mono.error(new HttpClientErrorException(clientResponse.statusCode())))
				.onStatus(HttpStatus::is5xxServerError, clientResponse ->
						Mono.error(new HttpClientErrorException(clientResponse.statusCode())))
				.bodyToMono(StockQueryResponse.class);

		System.out.println(Instant.now().toString());
		return response;

	}

	private ExchangeFilterFunction logRequest() {
		ExchangeFilterFunction loggingFilter = (clientRequest, next) -> {

			logger.info("Request: {} {} {}", clientRequest.method(), clientRequest.url(), clientRequest.hashCode());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
			return next.exchange(clientRequest);
		};

		return loggingFilter;
	}


}
