package com.cloudcomp.ccoms.deptsvc.client;


import java.math.BigInteger;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloudcomp.ccoms.deptsvc.model.Employee;



@FeignClient(name = "employee-service", url = "http://localhost:8080")
public interface EmployeeClient {

    @GetMapping("/employee/dept/{deptId}")
    public List<Employee> findEmpsByDeptId(@PathVariable("deptId") BigInteger deptId);
    
}

