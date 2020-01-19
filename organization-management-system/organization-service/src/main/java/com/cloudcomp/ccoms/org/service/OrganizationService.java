package com.cloudcomp.ccoms.org.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcomp.ccoms.org.client.DepartmentClient;
import com.cloudcomp.ccoms.org.client.EmployeeClient;
import com.cloudcomp.ccoms.org.dao.OrganizationRepository;

import com.cloudcomp.ccoms.org.model.Department;
import com.cloudcomp.ccoms.org.model.Organization;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository orgRepo;

    @Autowired
    DepartmentClient deptClient;

    @Autowired
    EmployeeClient empClient;

    public List<Organization> addorgs(List<Organization> organization) {

        return (List<Organization>) orgRepo.saveAll(organization);
    }

    public List<Organization> findAllOrganizations() {

        return (List<Organization>) orgRepo.findAll();
    }

    public Organization findById(Long id) {

        Optional<Organization> org = orgRepo.findById(id);
        if (org.isPresent()) {
            return org.get();
        } else {
            return null;
        }
    }

    public Organization findOrgUsingId(long id) {

        Optional<Organization> org = orgRepo.findById(id);
        if (org.isPresent()) {
            Organization org1 = org.get();
            org1.setDepts(deptClient.findDeptsUsingOrgId(id));
            return org1;
        } else {
            return null;
        }
    }

    public Organization findByIdWithDepartmentsAndEmployees(Long id) {

        Optional<Organization> org = orgRepo.findById(id);
        if (org.isPresent()) {
            Organization org1 = org.get();
            List<Department> depts = deptClient.findDeptsWithEmpsUsingOrgId(id);
            org1.setDepts(depts);
            return org1;
        } else {
            return null;
        }
    }

    public Organization findByIdWithEmployees(Long id) {

        Optional<Organization> org = orgRepo.findById(id);
        if (org.isPresent()) {
            Organization org1 = org.get();
            org1.setEmps(empClient.findEmpsByOrgId(id));
            return org1;
        } else {
            return null;
        }

    }

    public List<Organization> getDeptsEmpsAndOrgsInfo() {

        List<Organization> forgs = new ArrayList<>();

        for (Organization org : orgRepo.findAll()) {

            List<Department> depts = deptClient.findDeptsWithEmpsUsingOrgId(org.getId());
            org.setDepts(depts);
            forgs.add(org);
        }

        return forgs;
    }

}
