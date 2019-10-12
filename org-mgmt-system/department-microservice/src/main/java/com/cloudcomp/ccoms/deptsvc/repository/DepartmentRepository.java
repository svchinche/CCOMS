package com.cloudcomp.ccoms.deptsvc.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloudcomp.ccoms.deptsvc.model.Department;


@Repository
public interface DepartmentRepository extends CrudRepository<Department, BigInteger>{



    @Query("{ 'orgId' : ?0 }")
    public List<Department> findDeptByOrgId(int orgId);
    
    @Query("{ 'deptId' : ?0 }")
    public List<Department> findDeptsByDeptId(BigInteger deptId);
    
}
