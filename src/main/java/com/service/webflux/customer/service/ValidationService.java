package com.service.webflux.customer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.service.webflux.customer.constants.CustomerConstants;
import com.service.webflux.customer.dto.CustomerDto;
import com.service.webflux.customer.exception.CustomException;

import io.micrometer.common.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * The Class ValidationService.
 */
@Service
public class ValidationService {

	/**
	 * Validate payload.
	 *
	 * @param input the input
	 * @return the mono
	 */
	public Mono<Boolean> validatePayload(final CustomerDto input) {

		final Map<String, String> errorMessage = new HashMap<>();
		if (StringUtils.isBlank(input.getName())) {

			errorMessage.put("Name", "Name should not be empty");
		}
		if (StringUtils.isBlank(input.getContactNo())) {

			errorMessage.put("Name", "Contact Number should not be empty");
		} else {

			final String regexString = CustomerConstants.CONTACT_NO_REGEX;
			final Pattern contactNoPattern = Pattern.compile(regexString);
			final Matcher matcher = contactNoPattern.matcher(input.getContactNo());
			if (!matcher.matches()) {
				errorMessage.put("ContactNo", "Contact Number should be 10 digits");
			}
		}

		if (!StringUtils.isBlank(input.getEmail())) {

			final String regexString = CustomerConstants.EMAILD_REGEX;
			final Pattern contactNoPattern = Pattern.compile(regexString);
			final Matcher matcher = contactNoPattern.matcher(input.getEmail());
			if (!matcher.matches()) {
				errorMessage.put("EMail", "Email ID should be valid(i.e. abc@gmail.com)");
			}
		}
		if (errorMessage.isEmpty()) {
			return Mono.just(true);
		}
		final StringJoiner joiner = new StringJoiner(",");
		int i = 1;
		for (final Entry<String, String> map : errorMessage.entrySet()) {

			joiner.add(i + "-" + map.getValue());
			i++;
		}
		return Mono.error(new CustomException(joiner.toString()));
	}
}
