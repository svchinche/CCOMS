package com.cloudcomp.ccoms.empsvc.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.cloudcomp.ccoms.empsvc.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

    
    @Query("{ 'orgId' : ?0 }")
    public List<Employee> findEmpsByOrgId(int orgId);
    
    @Query("{ 'deptId' : ?0 }")
    public List<Employee> findEmpsByDeptId(int deptId);
    

}
