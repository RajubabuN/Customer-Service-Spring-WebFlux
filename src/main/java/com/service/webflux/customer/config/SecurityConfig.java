package com.service.webflux.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	/** The permit all url. */
	@Value("${url.swagger.permitall:/**}")
	private String[] permitAllUrl;

	/**
	 * Encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * User details.
	 *
	 * @param encoder the encoder
	 * @return the map reactive user details service
	 */
	@Bean
	MapReactiveUserDetailsService userDetails(final PasswordEncoder encoder) {
		final UserDetails userDetails = User.builder().username("admin").password(encoder.encode("admin"))
				.roles("ADMIN").build();

		final UserDetails userDetails1 = User.builder().username("user").password(encoder.encode("user")).roles("USER")
				.build();

		return new MapReactiveUserDetailsService(userDetails, userDetails1);
	}

	/**
	 * Security web filter chain.
	 *
	 * @param http      the http
	 * @param converter the converter
	 * @param manager   the manager
	 * @return the security web filter chain
	 */
	@Bean
	SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http, final AuthConverter converter,
			final AuthManager manager) {
		final AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(manager);
		jwtFilter.setServerAuthenticationConverter(converter);
		return http.headers(h -> h.frameOptions(k -> k.disable())).authorizeExchange(req -> {
			req.pathMatchers(permitAllUrl).permitAll();
			req.pathMatchers(HttpMethod.GET, "/v2/api/customers/**").hasAnyRole("ADMIN", "USER");
			req.pathMatchers(HttpMethod.POST, "/v2/api/customers/**").hasAnyRole("ADMIN");
			req.pathMatchers(HttpMethod.PUT, "/v2/api/customers/**").hasAnyRole("ADMIN");
			req.anyExchange().authenticated();

		}).addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION).httpBasic(httpBasic -> httpBasic.disable())
				.csrf(csrf -> csrf.disable()).formLogin(formLogin -> formLogin.disable()).build();
	}

}
