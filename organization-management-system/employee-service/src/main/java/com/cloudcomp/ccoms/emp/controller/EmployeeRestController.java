package com.cloudcomp.ccoms.emp.controller;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<Employee> getEmpById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(empsvc.getEmpById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Add an employee")
    @PostMapping("/addemp")
    public ResponseEntity<Object> addEmp(@RequestBody Employee emp) {
        empsvc.addEmp(emp);
        // Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/api/{id}").buildAndExpand(emp.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Add an employees at once")
    @PostMapping("/addemps")
    public ResponseEntity<Object> addEmps(@RequestBody List<Employee> emps) {

        empsvc.addEmps(emps);
        // Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/api/all").buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @GetMapping("/get")
    public List<Employee> getAllEmps() {
        return empsvc.getAllEmps();
    }

    @ApiOperation(value = "Update an employee")
    @PutMapping("/update")
    public ResponseEntity<Object> updateEmp(@RequestBody Employee emp) {
        try {
            empsvc.getEmpById(emp.getId());
            empsvc.updateEmp(emp);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        // can use 205 http reset
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @ApiOperation(value = "Delete a employee by employee id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmp(@PathVariable("id") Long id) {
        try {
            empsvc.deleteEmp(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Update a empoyee partially by employee id")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateEmpPartially(@PathVariable("id") Long id, @RequestBody Employee emp) {
        try {
            empsvc.updateEmpPartially(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
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
