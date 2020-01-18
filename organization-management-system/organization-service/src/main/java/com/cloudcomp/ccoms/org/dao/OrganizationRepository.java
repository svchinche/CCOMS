package com.cloudcomp.ccoms.org.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloudcomp.ccoms.org.model.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long>{


}
