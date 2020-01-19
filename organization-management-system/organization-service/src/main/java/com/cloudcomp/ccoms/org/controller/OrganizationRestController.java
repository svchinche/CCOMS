package com.cloudcomp.ccoms.org.controller;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cloudcomp.ccoms.org.model.Organization;
import com.cloudcomp.ccoms.org.service.OrganizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = { "/api" })
@Api(value = "Organization Management System")
public class OrganizationRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationRestController.class);

    @Autowired
    OrganizationService orgSvc;

    @ApiOperation(value = "Add one ore more organization at a time")
    @PostMapping("/addorgs")
    public List<Organization> addorgs(@RequestBody List<Organization> organization) {
        LOGGER.info("Organization add: {}", organization);
        return  orgSvc.addorgs(organization);
    }

    @ApiOperation(value = "Get all organization details")
    @GetMapping("/get")
    public List<Organization> findAllOrganizations() {
        LOGGER.info("Organization find");
        return orgSvc.findAllOrganizations();
    }

    @ApiOperation(value = "Get organization information by id ")
    @GetMapping("/{id}")
    public Organization findById(@PathVariable("id") Long id) {
        return orgSvc.findById(id);
    }

    @ApiOperation(value = "Get organization info using organization id")
    @GetMapping("/{id}/withdepts")
    public Organization findOrgUsingId(@PathVariable("id") long id) {

        return orgSvc.findOrgUsingId(id);

    }

    @ApiOperation(value = "Get organization, depts and employees information using organization id")
    @GetMapping("/{id}/withdeptsandemps")
    public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {

        return orgSvc.findByIdWithDepartmentsAndEmployees(id);
    }

    @ApiOperation(value = "Get organization and employees information using organization id")
    @GetMapping("/{id}/withemps")
    public Organization findByIdWithEmployees(@PathVariable("id") Long id) {
        LOGGER.info("Organization find: id={}", id);
        return orgSvc.findByIdWithEmployees(id);

    }

    @ApiOperation(value = "Get organization, depts and employees information")
    @GetMapping("/getall")
    public List<Organization> getDeptsEmpsAndOrgsInfo() {

        return orgSvc.getDeptsEmpsAndOrgsInfo();
    }

}
