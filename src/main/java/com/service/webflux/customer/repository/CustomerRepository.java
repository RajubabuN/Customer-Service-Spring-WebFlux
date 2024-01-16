package com.service.webflux.customer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.service.webflux.customer.entity.Customer;

/**
 * The Interface CustomerRepository.
 */
@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

}
