package com.cloudcomp.ccoms.org.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloudcomp.ccoms.org.model.*;

@FeignClient(name = "department-service", url = "${dept.service.url: http://localhost:8081}")
public interface DepartmentClient {

    @GetMapping("/api/org/{orgId}")
    public List<Department> findDeptsUsingOrgId(@PathVariable("orgId") Long orgId);

    @GetMapping("/api/org/{orgId}/withemp")
    public List<Department> findDeptsWithEmpsUsingOrgId(@PathVariable("orgId") Long orgId);

}
