package com.service.webflux.customer.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.service.webflux.customer.service.JWTService;

import reactor.core.publisher.Mono;

/**
 * The Class AuthManager.
 */
@Component
public class AuthManager implements ReactiveAuthenticationManager {

	/** The jwt service. */
	@Autowired
	private JWTService jwtService;

	/** The user details service. */
	@Autowired
	private ReactiveUserDetailsService userDetailsService;

	/**
	 * Authenticate.
	 *
	 * @param authentication the authentication
	 * @return the mono
	 */
	@Override
	public Mono<Authentication> authenticate(final Authentication authentication) {
		return Mono.justOrEmpty(authentication).cast(BearerToken.class).flatMap(auth -> {

			final String userNameString = jwtService.getUserName(auth.getCredentials());
			final Mono<UserDetails> userDetailsMono = userDetailsService.findByUsername(userNameString)
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
			return userDetailsMono.flatMap(o -> {

				if (null == o.getUsername()) {
					return Mono.error(new IllegalAccessException("User Not Found"));
				}
				if (jwtService.validateToken(auth.getCredentials(), o)) {
					return Mono.just(new UsernamePasswordAuthenticationToken(o.getUsername(), o.getPassword(),
							o.getAuthorities()));
				}
				return Mono.error(new IllegalAccessException("Invalid token"));
			});
		});
	}

}
