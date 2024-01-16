package com.service.webflux.customer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * The Class ExceptionHandler.
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler implements ErrorWebExceptionHandler {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Handle.
	 *
	 * @param exchange the exchange
	 * @param ex       the ex
	 * @return the mono
	 */
	@Override
	public Mono<Void> handle(final ServerWebExchange exchange, final Throwable ex) {

		log.error(ex.getMessage());
		final DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		final DataBuffer dataBuffer = bufferFactory.wrap(ex.getMessage().getBytes());
		exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		return exchange.getResponse().writeWith(Mono.just(dataBuffer));

	}

}
