package com.service.webflux.customer.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.service.webflux.customer.dto.TokenRequest;
import com.service.webflux.customer.service.JWTService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Mono;

/**
 * The Class AuthController.
 */
@RestController
public class AuthController {

	/** The user details service. */
	private final ReactiveUserDetailsService userDetailsService;

	/** The jwt service. */
	private final JWTService jwtService;

	/** The encoder. */
	private final PasswordEncoder encoder;

	/**
	 * Instantiates a new auth controller.
	 *
	 * @param userDetailsService the user details service
	 * @param jwtService         the jwt service
	 * @param encoder            the encoder
	 */
	public AuthController(final ReactiveUserDetailsService userDetailsService, final JWTService jwtService,
			final PasswordEncoder encoder) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.encoder = encoder;
	}

	/**
	 * Gets the token.
	 *
	 * @param req the req
	 * @return the token
	 */
	public Mono<String> getToken(@RequestBody final TokenRequest req) {
		final Mono<UserDetails> user = userDetailsService.findByUsername(req.getUserName())
				.defaultIfEmpty(new UserDetails() {

					private static final long serialVersionUID = 1L;

					@Override
					public Collection<? extends GrantedAuthority> getAuthorities() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getPassword() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getUsername() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public boolean isAccountNonExpired() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean isAccountNonLocked() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean isCredentialsNonExpired() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean isEnabled() {
						// TODO Auto-generated method stub
						return false;
					}

				});
		return user.flatMap(i -> {
			if (null == i.getUsername()) {
				return Mono.error(new Exception("User is invalid"));
			}

			if (encoder.matches(i.getPassword(), req.getPassword())) {
				return Mono.just(jwtService.generateToken(req.getUserName()));
			}
			return Mono.error(new Exception("Invalid Credentials"));

		});
	}
}
