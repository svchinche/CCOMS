package com.cloudcomp.ccoms.deptsvc.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloudcomp.ccoms.deptsvc.model.Employee;

@FeignClient(name = "employee-service", url = "${emp.service.url: http://localhost:8080}")
public interface EmployeeClient {

    @GetMapping("/api/dept/{deptId}")
    public List<Employee> findEmpsByDeptId(@PathVariable("deptId") Long deptId);

}
