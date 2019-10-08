package com.dept.spring.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dept.spring.model.Department;
import com.dept.spring.model.Employee;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, BigInteger>{

  
    


    @Query("{ 'orgId' : ?0 }")
    public List<Department> findByOrgId(BigInteger orgId);
    
    
}
