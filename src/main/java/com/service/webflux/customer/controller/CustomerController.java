package com.service.webflux.customer.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.webflux.customer.dto.CustomerDto;
import com.service.webflux.customer.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class CustomerController.
 */
@RestController
@RequestMapping("/v2/api/customers/")
public class CustomerController {

	/** The service. */
	private final CustomerService service;

	/**
	 * Instantiates a new customer controller.
	 *
	 * @param service the service
	 */
	public CustomerController(final CustomerService service) {

		this.service = service;
	}

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 */
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Mono<CustomerDto> getCustomerById(@PathVariable final Long id) {

		return service.getCustomerById(id);
	}

	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	@GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Flux<CustomerDto> getAllCustomers() {

		return service.getAllCustomer();
	}

	/**
	 * Creates the customer.
	 *
	 * @param input the input
	 * @return the mono
	 */
	@PostMapping("")
	public Mono<String> createCustomer(@RequestBody final CustomerDto input) {

		return service.createCustomer(input);
	}

	/**
	 * Update customer.
	 *
	 * @param input the input
	 * @return the mono
	 */
	@PutMapping("")
	public Mono<String> updateCustomer(@RequestBody final CustomerDto input) {
		return service.updateCustomer(input);
	}

	/**
	 * Delete customer by id.
	 *
	 * @param id the id
	 * @return the mono
	 */
	@DeleteMapping("{id}")
	public Mono<Void> deleteCustomerById(@PathVariable final Long id) {
		return service.deleteCustomerById(id);
	}

}
