package com.cloudcomp.ccoms.orgsvc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloudcomp.ccoms.orgsvc.model.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long>{


}
