package com.cloudcomp.ccoms.dept.controller;

import java.net.URI;
import java.util.List;


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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudcomp.ccoms.dept.model.Department;
import com.cloudcomp.ccoms.dept.service.DepartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Department Management System")
@RestController
@RequestMapping(value = { "/api" })
public class DepartmentRestController {



    @Autowired
    DepartmentService deptSvc;

    @GetMapping("/")
    public String get() {
        return "Please give url " + "Use dept/id to get department using id and depts to get the departments";

    }

    @ApiOperation(value = "List all departments")
    @GetMapping("/depts")
    public List<Department> getAllDepts() {
        return deptSvc.getAllDepts();
    }

    @ApiOperation(value = "Add single department")
    @PostMapping("/adddept")
    public ResponseEntity<Object> addDept(@RequestBody Department dept) {

        deptSvc.addDept(dept);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/api/{id}").buildAndExpand(dept.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Add multiple departments at same time")
    @PostMapping("/adddepts")
    public ResponseEntity<Object> addDepts(@RequestBody List<Department> depts) {

        deptSvc.addDepts(depts);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/api/all").buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "delete all departments")
    @DeleteMapping("/deleteall")
    public ResponseEntity<Department> deleteAllDepts() {

        deptSvc.deleteAllDepts();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get department by departmet id")
    @GetMapping("/dept/{deptId}")
    public Department getDeptById(@PathVariable("deptId") Long deptId) {
        return deptSvc.getDeptById(deptId);
    }

    @ApiOperation(value = "Get department using organization id")
    @GetMapping("/org/{orgId}")
    public List<Department> getDeptsByOrgId(@PathVariable("orgId") Long orgId) {
        return deptSvc.getDeptsByOrgId(orgId);
    }

    @ApiOperation(value = "Get departments with employee using organizaation id")
    @GetMapping("/org/{orgId}/withemp")
    public List<Department> getDeptswithEmpsUsingOrgid(@PathVariable("orgId") Long orgId) {
        return deptSvc.getDeptswithEmpsUsingOrgid(orgId);

    }

    @ApiOperation(value = "Get all department and organization")
    @GetMapping("/org/withemp")
    public List<Department> getDeptswithEmps() {
        return deptSvc.getDeptswithEmps();

    }

}
