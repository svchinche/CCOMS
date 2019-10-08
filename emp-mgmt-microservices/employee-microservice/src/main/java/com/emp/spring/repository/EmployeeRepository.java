package com.emp.spring.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.emp.spring.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, BigInteger>{

    
    @Query("{ 'orgId' : ?0 }")
    public Employee findByOrgId(int orgId);
    
    @Query("{ 'deptId' : ?0 }")
    public Employee findByDeptId(int deptId);
    
    /*
    @Query("{ 'id' : ?0 }")
    public List<Employee> findById(int id);
   
    @Query(value="{id : ?0}", delete = true)
    public void deleteById(int id);
     */
    

}
