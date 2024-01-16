package com.service.webflux.customer.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * The Class AuthConverter.
 */
@Component
public class AuthConverter implements ServerAuthenticationConverter {

	/**
	 * Convert.
	 *
	 * @param exchange the exchange
	 * @return the mono
	 */
	@Override
	public Mono<Authentication> convert(final ServerWebExchange exchange) {
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
				.filter(s -> s.startsWith("Bearer ")).map(i -> i.substring(7)).map(t -> new BearerToken(t));
	}

}
