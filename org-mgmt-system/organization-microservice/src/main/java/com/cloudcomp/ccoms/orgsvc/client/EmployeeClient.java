package com.cloudcomp.ccoms.orgsvc.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloudcomp.ccoms.orgsvc.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;




@FeignClient(name = "employee-service", url = "http://localhost:8080")
public interface EmployeeClient {

    @GetMapping("/employee/org/{orgId}")
    public List<Employee> findEmpsByOrgId(@PathVariable("orgId") Long orgId);
    
    @GetMapping("/employee/dept/{deptId}")
    public List<Employee> findEmpsByDeptId(@PathVariable("deptId") Long deptId);
    
}

