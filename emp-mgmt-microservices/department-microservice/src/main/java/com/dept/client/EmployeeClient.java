package com.dept.client;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dept.spring.model.Employee;



@FeignClient(name = "employee-service", url = "http://localhost:8081")
public interface EmployeeClient {

    @GetMapping("/department/{departmentId}")
    List<Employee> findByDepartment(@PathVariable("departmentId") BigInteger departmentId);
    
}
