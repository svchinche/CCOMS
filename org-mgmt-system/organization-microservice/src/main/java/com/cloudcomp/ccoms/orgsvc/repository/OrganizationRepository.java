package com.cloudcomp.ccoms.orgsvc.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cloudcomp.ccoms.orgsvc.model.Department;
import com.cloudcomp.ccoms.orgsvc.model.Organization;
public interface OrganizationRepository extends CrudRepository<Organization, BigInteger>{

    
    @Query("{ 'orgId' : ?0 }")
    public Organization findOrgUsingId(BigInteger orgId);


}
