package com.org.java.spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.org.java.spring.bean.Organization;
public interface OrganizationRepository extends CrudRepository<Organization, String>{

}
