package com.cloudcomp.ccoms.dept.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloudcomp.ccoms.dept.model.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Query("{ 'orgId' : ?0 }")
    public List<Department> findDeptByOrgId(Long orgId);

}
