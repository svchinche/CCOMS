package com.cloudcomp.ccoms.emp.controller;

import java.util.List;


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

import com.cloudcomp.ccoms.emp.model.Employee;
import com.cloudcomp.ccoms.emp.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = { "/api" })
@Api(value = "Employee Management System")
public class EmployeeRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    EmployeeService empsvc;

    @GetMapping("/")
    public String get() {
        return "Please give url as hostname/employee/get";

    }

    @ApiOperation(value = "Get an employee by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmpById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(empsvc.getEmpById(id));
    }

    @ApiOperation(value = "Add an employee")
    @PostMapping("/addemp")
    public ResponseEntity<Void> createEmp(@RequestBody Employee emp, UriComponentsBuilder ucBuilder) {
        empsvc.createEmp(emp);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Employee/{id}").buildAndExpand(emp.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add an employees at once")
    @PostMapping("/addemps")
    public void createEmps(@RequestBody List<Employee> emps) {
        empsvc.createEmps(emps);
    }

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @GetMapping("/get")
    public List<Employee> getAllEmps() {
        return empsvc.getAllEmps();

    }

    @ApiOperation(value = "Update an employee")
    @PutMapping("/update")
    public ResponseEntity<String> updateEmp(@RequestBody Employee emp) throws ResourceNotFoundException {
        empsvc.updateEmp(emp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a employee by employee id")
    @DeleteMapping("/{id}")
    public void deleteEmp(@PathVariable("id") Long id) throws ResourceNotFoundException {
        empsvc.deleteEmp(id);
    }

    @ApiOperation(value = "Update a empoyee partially by employee id")
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmpPartially(@PathVariable("id") Long id,
            @RequestBody Employee emp) throws ResourceNotFoundException {
        return new ResponseEntity<>(empsvc.updateEmpPartially(id), HttpStatus.OK);
    }

    @ApiOperation(value = "List employee by department id")
    @GetMapping("/dept/{deptId}")
    public List<Employee> findByDept(@PathVariable("deptId") int deptId) {
        return empsvc.findByDept(deptId);
    }

    @ApiOperation(value = "List employee by organization id")
    @GetMapping("/org/{orgId}")
    public List<Employee> findByOrg(@PathVariable("orgId") int orgId) {
        LOGGER.info("Employee find: organizationId={}", orgId);
        return empsvc.findByOrg(orgId);
    }
}
