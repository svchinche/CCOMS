package com.cloudcomp.ccoms.org.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloudcomp.ccoms.org.model.*;

@FeignClient(name = "employee-service", url = "${emp.service.url: http://localhost:8080}")
public interface EmployeeClient {

    @GetMapping("/employee/api/org/{orgId}")
    public List<Employee> findEmpsByOrgId(@PathVariable("orgId") Long orgId);

    @GetMapping("/employee/api/dept/{deptId}")
    public List<Employee> findEmpsByDeptId(@PathVariable("deptId") Long deptId);

}
