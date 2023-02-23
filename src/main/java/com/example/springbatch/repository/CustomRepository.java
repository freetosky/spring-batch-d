package com.example.springbatch.repository;

import com.example.springbatch.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomRepository extends PagingAndSortingRepository<Customer, Long>, CrudRepository<Customer, Long> {
    List<Customer> findByLastName(@Param("name") String lastName);
}
