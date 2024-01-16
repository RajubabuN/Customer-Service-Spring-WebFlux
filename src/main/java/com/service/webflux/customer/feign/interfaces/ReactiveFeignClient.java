package com.service.webflux.customer.feign.interfaces;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Mono;

@reactivefeign.spring.config.ReactiveFeignClient(value = "feignClientInterface", url = "${url.external.api.base:null}")
public interface ReactiveFeignClient {

	@PostMapping("${url.external.api.endpoint:null}")
	Mono<String> getDetailsFromSalesForce(@RequestHeader("Authorization") String token,
			@RequestBody JSONObject payload);
}
