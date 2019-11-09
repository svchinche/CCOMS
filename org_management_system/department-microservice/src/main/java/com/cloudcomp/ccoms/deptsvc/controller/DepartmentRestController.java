package com.cloudcomp.ccoms.deptsvc.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudcomp.ccoms.deptsvc.client.EmployeeClient;
import com.cloudcomp.ccoms.deptsvc.controller.DepartmentRestController;
import com.cloudcomp.ccoms.deptsvc.model.Department;
import com.cloudcomp.ccoms.deptsvc.repository.DepartmentRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Department Management System", description = "Operations pertaining to department in Department Management System")
@RestController
@RequestMapping(value = { "/api" })
public class DepartmentRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentRestController.class);
    @Autowired
    DepartmentRepository deptRepository;

    @Autowired
    EmployeeClient employeeClient;

    @GetMapping("/")
    public String get() {
        return "Please give url " + "Use dept/id to get department using id and depts to get the departments";

    }

    @ApiOperation(value = "List all departments")
    @GetMapping("/depts")
    public List<Department> getAllDepartments() {

        List<Department> depts = (List<Department>) deptRepository.findAll();
        return depts;

    }

    @ApiOperation(value = "Add single department")
    @PostMapping("/adddept")
    public ResponseEntity<Void> createDepartment(@RequestBody Department dept, UriComponentsBuilder ucBuilder) {

        LOGGER.info("Creating Department " + dept.getName());
        deptRepository.save(dept);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add multiple departments at same time")
    @PostMapping("/adddepts")
    public ResponseEntity<Void> createDepartments(@RequestBody List<Department> depts, UriComponentsBuilder ucBuilder) {

        deptRepository.saveAll(depts);
        HttpHeaders headers = new HttpHeaders();
        // headers.setLocation(ucBuilder.path("/Department/{id}").buildAndExpand(Department.getDeptId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete all departments")
    @DeleteMapping("/deleteall")
    public ResponseEntity<Department> deleteAllDepartments() {

        deptRepository.deleteAll();
        return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get department by departmet id")
    @GetMapping("/dept/{deptId}")
    public Optional<Department> getDepartmentById(@PathVariable("deptId") Long deptId) {
        LOGGER.info(" Department with id " + deptId);
        Optional<Department> depts = deptRepository.findById(deptId);
        return depts;
    }

    @ApiOperation(value = "Get department using organization id")
    @GetMapping("/org/{orgId}")
    public List<Department> getDeptsByOrgId(@PathVariable("orgId") Long orgId) {
        LOGGER.info("Fetching Department with id " + orgId);
        List<Department> depts = deptRepository.findDeptByOrgId(orgId);
        return depts;

    }

    @ApiOperation(value = "Get departments with employee using organizaation id")
    @GetMapping("/org/{orgId}/withemp")
    public List<Department> getDeptswithEmpsUsingOrgid(@PathVariable("orgId") Long orgId) {
        LOGGER.info("Department find: orgId={}", orgId);

        List<Department> final_depts = new ArrayList<Department>();
        List<Department> depts = deptRepository.findDeptByOrgId(orgId);

        for (Department dept : depts) {
            Department tmp = new Department();
            tmp.setId(dept.getId());
            tmp.setOrgId(dept.getOrgId());
            tmp.setName(dept.getName());
            tmp.setEmps(employeeClient.findEmpsByDeptId(dept.getId()));
            final_depts.add(tmp);
        }

        return final_depts;
    }

    @ApiOperation(value = "Get all department and organization")
    @GetMapping("/org/withemp")
    public List<Department> getDeptswithEmps() {

        List<Department> final_depts = new ArrayList<Department>();
        for (Department dept : deptRepository.findAll()) {
            dept.setEmps(employeeClient.findEmpsByDeptId(dept.getId()));
            final_depts.add(dept);
        }
        return final_depts;

    }

}
