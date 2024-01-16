package com.service.webflux.customer.service;

import org.springframework.stereotype.Service;

import com.service.webflux.customer.constants.CustomerConstants;
import com.service.webflux.customer.dto.CustomerDto;
import com.service.webflux.customer.entity.Customer;
import com.service.webflux.customer.repository.CustomerRepository;
import com.service.webflux.customer.util.Utility;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class CustomerService.
 */
@Service
public class CustomerService {

	/** The repo. */
	private final CustomerRepository repo;

	/** The validation service. */
	private final ValidationService validationService;

	/**
	 * Instantiates a new customer service.
	 *
	 * @param repo              the repo
	 * @param validationService the validation service
	 */
	CustomerService(final CustomerRepository repo, final ValidationService validationService) {

		this.repo = repo;
		this.validationService = validationService;
	}

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 */
	public Mono<CustomerDto> getCustomerById(final Long id) {

		return repo.findById(id).map(Utility::covertEntityToDto)
				.switchIfEmpty(Mono.error(new Exception(CustomerConstants.CUSTOMER_NOT_FOUND)));
	}

	/**
	 * Gets the all customer.
	 *
	 * @return the all customer
	 */
	public Flux<CustomerDto> getAllCustomer() {

		return repo.findAll().map(Utility::covertEntityToDto);
	}

	/**
	 * Creates the customer.
	 *
	 * @param input the input
	 * @return the mono
	 */
	public Mono<String> createCustomer(final CustomerDto input) {

		return createOrUpdateCustomer(input, new Customer(), true);
	}

	/**
	 * Creates the or update customer.
	 *
	 * @param input            the input
	 * @param entity           the entity
	 * @param isCreateCustomer the is create customer
	 * @return the mono
	 */
	public Mono<String> createOrUpdateCustomer(final CustomerDto input, final Customer entity,
			final boolean isCreateCustomer) {

		return validationService.validatePayload(input).flatMap(value -> {
			entity.setName(input.getName());
			entity.setContactNo(input.getContactNo());
			entity.setEmail(input.getEmail());

			return repo.save(entity).flatMap(i -> {
				if (isCreateCustomer) {
					return Mono.just(CustomerConstants.CUSTOMER_CREATED_SUCCESSFULLY);
				}
				return Mono.just(CustomerConstants.CUSTOMER_UPDATED_SUCCEFULLY);
			}).onErrorResume(err -> Mono.error(new Exception(err.getMessage())));
		});

	}

	/**
	 * Update customer.
	 *
	 * @param input the input
	 * @return the mono
	 */
	public Mono<String> updateCustomer(final CustomerDto input) {

		if (input.getId() != 0) {
			return repo.findById(input.getId()).flatMap(i -> {

				return createOrUpdateCustomer(input, i, false);
			}).switchIfEmpty(Mono.error(new Exception(CustomerConstants.CUSTOMER_NOT_FOUND)));

		}
		return Mono.error(new Exception(CustomerConstants.CUSTOMER_ID_NOT_VALID));

	}

	/**
	 * Delete customer by id.
	 *
	 * @param id the id
	 * @return the mono
	 */
	public Mono<Void> deleteCustomerById(final Long id) {

		if (id != 0) {
			return repo.deleteById(id);
		}
		return Mono.error(new Exception(CustomerConstants.CUSTOMER_ID_NOT_VALID));

	}

}
