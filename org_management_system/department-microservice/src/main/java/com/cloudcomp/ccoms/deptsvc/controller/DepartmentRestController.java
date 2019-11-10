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

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudcomp.ccoms.deptsvc.client.EmployeeClient;

import com.cloudcomp.ccoms.deptsvc.model.Department;
import com.cloudcomp.ccoms.deptsvc.repository.DepartmentRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Department Management System")
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
        return (List<Department>) deptRepository.findAll();
    }

    @ApiOperation(value = "Add single department")
    @PostMapping("/adddept")
    public ResponseEntity<Void> createDepartment(@RequestBody Department dept, UriComponentsBuilder ucBuilder) {

        deptRepository.save(dept);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add multiple departments at same time")
    @PostMapping("/adddepts")
    public ResponseEntity<Void> createDepartments(@RequestBody List<Department> depts, UriComponentsBuilder ucBuilder) {

        deptRepository.saveAll(depts);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete all departments")
    @DeleteMapping("/deleteall")
    public ResponseEntity<Department> deleteAllDepartments() {

        deptRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get department by departmet id")
    @GetMapping("/dept/{deptId}")
    public Department getDepartmentById(@PathVariable("deptId") Long deptId) {

        Optional<Department> depts = deptRepository.findById(deptId);
        if (depts.isPresent()) {
            return depts.get();
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Get department using organization id")
    @GetMapping("/org/{orgId}")
    public List<Department> getDeptsByOrgId(@PathVariable("orgId") Long orgId) {
        return deptRepository.findDeptByOrgId(orgId);
    }

    @ApiOperation(value = "Get departments with employee using organizaation id")
    @GetMapping("/org/{orgId}/withemp")
    public List<Department> getDeptswithEmpsUsingOrgid(@PathVariable("orgId") Long orgId) {
        LOGGER.info("Department find: orgId={}", orgId);

        List<Department> finaldepts = new ArrayList<>();
        List<Department> depts = deptRepository.findDeptByOrgId(orgId);

        for (Department dept : depts) {
            Department tmp = new Department();
            tmp.setId(dept.getId());
            tmp.setOrgId(dept.getOrgId());
            tmp.setName(dept.getName());
            tmp.setEmps(employeeClient.findEmpsByDeptId(dept.getId()));
            finaldepts.add(tmp);
        }

        return finaldepts;
    }

    @ApiOperation(value = "Get all department and organization")
    @GetMapping("/org/withemp")
    public List<Department> getDeptswithEmps() {

        List<Department> finaldepts = new ArrayList<>();
        for (Department dept : deptRepository.findAll()) {
            dept.setEmps(employeeClient.findEmpsByDeptId(dept.getId()));
            finaldepts.add(dept);
        }
        return finaldepts;

    }

}
