package com.example.springboot.locale.demo.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggingFilter extends GenericFilterBean {
    private static Logger logger = LogManager.getLogger(LoggingFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        logger.info("request received");
        logger.info("Request Method: {}", httpRequest.getMethod());
        logger.info("URI: {}", httpRequest.getRequestURI());
        logger.info("Accept: {}", httpRequest.getHeaders("Accept").nextElement());
        filterChain.doFilter(servletRequest,servletResponse);
        logger.info("response received");
    }
}
