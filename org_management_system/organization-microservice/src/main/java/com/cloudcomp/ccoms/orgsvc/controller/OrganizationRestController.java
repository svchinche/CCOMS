package com.cloudcomp.ccoms.orgsvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcomp.ccoms.orgsvc.client.DepartmentClient;
import com.cloudcomp.ccoms.orgsvc.client.EmployeeClient;
import com.cloudcomp.ccoms.orgsvc.model.Department;

import com.cloudcomp.ccoms.orgsvc.model.Organization;
import com.cloudcomp.ccoms.orgsvc.repository.OrganizationRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = { "/api" })
@Api(value = "Organization Management System")
public class OrganizationRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationRestController.class);

    @Autowired
    OrganizationRepository repository;
    @Autowired
    DepartmentClient departmentClient;
    @Autowired
    EmployeeClient employeeClient;

    @ApiOperation(value = "Add one ore more organization at a time")
    @PostMapping("/addorgs")
    public List<Organization> addorgs(@RequestBody List<Organization> organization) {
        LOGGER.info("Organization add: {}", organization);
        return (List<Organization>) repository.saveAll(organization);
    }

    @ApiOperation(value = "Get all organization details")
    @GetMapping("/get")
    public List<Organization> findAllOrganizations() {
        LOGGER.info("Organization find");
        return (List<Organization>) repository.findAll();
    }

    @ApiOperation(value = "Get organization information by id ")
    @GetMapping("/{id}")
    public Organization findById(@PathVariable("id") Long id) {

        Optional<Organization> org = repository.findById(id);
        if (org.isPresent()) {
            return org.get();
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Get organization info using organization id")
    @GetMapping("/{id}/withdepts")
    public Organization findOrgUsingId(@PathVariable("id") long id) {
        LOGGER.info("Organization find: id={}", id);
        Optional<Organization> org = repository.findById(id);
        if (org.isPresent()) {
            Organization org1 = org.get();
            org1.setDepts(departmentClient.findDeptsUsingOrgId(id));
            return org1;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Get organization, depts and employees information using organization id")
    @GetMapping("/{id}/withdeptsandemps")
    public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {

        Optional<Organization> org = repository.findById(id);
        if (org.isPresent()) {
            Organization org1 = org.get();
            List<Department> depts = departmentClient.findDeptsWithEmpsUsingOrgId(id);
            org1.setDepts(depts);
            return org1;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Get organization and employees information using organization id")
    @GetMapping("/{id}/withemps")
    public Organization findByIdWithEmployees(@PathVariable("id") Long id) {
        LOGGER.info("Organization find: id={}", id);
        Optional<Organization> org = repository.findById(id);
        if (org.isPresent()) {
            Organization org1 = org.get();
            org1.setEmps(employeeClient.findEmpsByOrgId(id));
            return org1;
        } else {
            return null;
        }

    }

    @ApiOperation(value = "Get organization, depts and employees information")
    @GetMapping("/getall")
    public List<Organization> getDeptsEmpsAndOrgsInfo() {

        List<Organization> forgs = new ArrayList<>();

        for (Organization org : repository.findAll()) {

            List<Department> depts = departmentClient.findDeptsWithEmpsUsingOrgId(org.getId());
            org.setDepts(depts);
            forgs.add(org);
        }

        return forgs;
    }

}
