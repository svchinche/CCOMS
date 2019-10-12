package com.cloudcomp.ccoms.orgsvc.client;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cloudcomp.ccoms.orgsvc.model.*;

@FeignClient(name = "department-service", url = "${dept.service.url: http://localhost:8081}")
public interface DepartmentClient {

    @GetMapping("/department/org/{orgId}")
    public List<Department> findDeptsUsingOrgId(@PathVariable("orgId") Long orgId);
    
    @GetMapping("/department/org/{orgId}/withemp")
    public List<Department> findDeptsWithEmpsUsingOrgId(@PathVariable("orgId") Long orgId);
    
}

