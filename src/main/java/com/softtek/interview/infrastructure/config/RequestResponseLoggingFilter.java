package com.softtek.interview.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        logRequest(wrappedRequest);
        filterChain.doFilter(wrappedRequest, wrappedResponse);
        logResponse(wrappedRequest, wrappedResponse);

        wrappedResponse.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) throws IOException {
        LOGGER.info("Incoming request - Method: {}, URL: {}", request.getMethod(), request.getRequestURI());
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            LOGGER.info("Request Body: {}", getContent(request));
        }
    }

    private void logResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws IOException {
        LOGGER.info("Outgoing response - Status: {}", response.getStatus());
        if (response.getContentType() != null && response.getContentType().startsWith("application/json")) {
            LOGGER.info("Response Body: {}", getContent(response));
        }
    }

    private String getContent(ContentCachingRequestWrapper request) throws IOException {
        return new String(request.getContentAsByteArray());
    }

    private String getContent(ContentCachingResponseWrapper response) throws IOException {
        return new String(response.getContentAsByteArray());
    }
}
