package com.service.webflux.customer.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * The Class BearerToken.
 */
public class BearerToken extends AbstractAuthenticationToken {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The token. */
	private final String token;

	/**
	 * Instantiates a new bearer token.
	 *
	 * @param token the token
	 */
	public BearerToken(final String token) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.token = token;
	}

	/**
	 * Gets the credentials.
	 *
	 * @return the credentials
	 */
	@Override
	public String getCredentials() {

		return token;
	}

	/**
	 * Gets the principal.
	 *
	 * @return the principal
	 */
	@Override
	public String getPrincipal() {

		return token;
	}

}
