package com.cloudcomp.ccoms.empsvc.controller;

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
import com.cloudcomp.ccoms.empsvc.model.Employee;
import com.cloudcomp.ccoms.empsvc.repository.EmployeeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = { "/api" })
@Api(value = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
public class EmployeeRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);
    @Autowired
    EmployeeRepository empRepository;

    @GetMapping("/")
    public String get() {
        return "Please give url as hostname/employee/get";

    }

    @ApiOperation(value = "Get an employee by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmpById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Employee emp = empRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(emp);

    }

    @ApiOperation(value = "Add an employee")
    @PostMapping("/addemp")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee emp, UriComponentsBuilder ucBuilder) {

        LOGGER.info("Creating Employee " + emp.getName());
        empRepository.save(emp);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Employee/{id}").buildAndExpand(emp.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add an employees at once")
    @PostMapping("/addemps")
    public void createEmployees(@RequestBody List<Employee> emps) {
        empRepository.saveAll(emps);
    }

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @GetMapping("/get")
    public List<Employee> getAllEmployee() {
        List<Employee> tasks = (List<Employee>) empRepository.findAll();
        return tasks;

    }

    @ApiOperation(value = "Update an employee")
    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee currentEmployee) {

        Optional<Employee> emp = empRepository.findById(currentEmployee.getId());
        if (emp == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        empRepository.save(currentEmployee);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a employee by employee id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id) {

        empRepository.deleteById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Update a employee partially by employee id")
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeePartially(@PathVariable("id") Long id,
            @RequestBody Employee currentEmployee) {
        Optional<Employee> emp = empRepository.findById(id);

        if (emp == null) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        Employee usr = empRepository.save(currentEmployee);
        return new ResponseEntity<Employee>(usr, HttpStatus.OK);
    }

    @ApiOperation(value = "List employee by department id")
    @GetMapping("/dept/{deptId}")
    public List<Employee> findByDepartment(@PathVariable("deptId") int deptId) {
        LOGGER.info("Employee find: departmentId={}", deptId);
        return empRepository.findEmpsByDeptId(deptId);
    }

    @ApiOperation(value = "List employee by organization id")
    @GetMapping("/org/{orgId}")
    public List<Employee> findByOrganization(@PathVariable("orgId") int orgId) {
        LOGGER.info("Employee find: organizationId={}", orgId);
        return empRepository.findEmpsByOrgId(orgId);
    }
}
