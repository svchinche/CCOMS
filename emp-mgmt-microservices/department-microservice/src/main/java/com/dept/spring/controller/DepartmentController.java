package com.dept.spring.controller;

import java.math.BigInteger;
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

import com.dept.spring.service.DepartmentService;
import com.dept.client.EmployeeClient;
import com.dept.spring.controller.DepartmentController;
import com.dept.spring.model.Department;
import com.dept.spring.model.Employee;


@RestController
@RequestMapping(value = { "/", "/department" })
public class DepartmentController {


    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    DepartmentService deptSvc;

    @Autowired
    EmployeeClient employeeClient;
 
    
    @GetMapping("/")
    public String get(){
        return "Please give url as hostname/department/get";

    }
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") BigInteger id) {
        LOGGER.info("Fetching Department with id " + id);
        Optional<Department> Department = deptSvc.findDeptById(id);
        if (Department == null) {
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Department>(HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createDepartment(@RequestBody Department Department, UriComponentsBuilder ucBuilder){

        LOGGER.info("Creating Department "+Department.getDeptName());
        deptSvc.createDepartment(Department);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Department/{id}").buildAndExpand(Department.getDeptId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public List<Department> getAllDepartment() {     
        List<Department> tasks=deptSvc.getDepartment();
        return tasks;

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDepartment(@RequestBody Department currentDepartment)
    {
        LOGGER.info("");
        Optional<Department> Department = deptSvc.findDeptById(currentDepartment.getDeptId());
        if (Department==null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        deptSvc.update(currentDepartment, currentDepartment.getDeptId());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") BigInteger id){
        Optional<Department> Department = deptSvc.findDeptById(id);
        if (Department == null) {
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
        deptSvc.deleteDepartmentById(id);
        return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Department> updateDepartmentPartially(@PathVariable("id") BigInteger id, @RequestBody Department currentDepartment){
        Optional<Department> Department = deptSvc.findDeptById(id);
        if(Department ==null){
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
        Department usr =  deptSvc.updatePartially(currentDepartment, id);
        return new ResponseEntity<Department>(usr, HttpStatus.OK);
    }
    
    @GetMapping("/organization/{orgId}")
    public List<Department> findByOrganization(@PathVariable("orgId") BigInteger orgId) {
        LOGGER.info("Department find: organizationId={}", orgId);
      
        List<Department> departments= deptSvc.findOrgById(orgId);
        
        return departments;
        
    }
    
    
    @GetMapping("/organization/{organizationId}/with-employees")
    public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") BigInteger orgId) {
        LOGGER.info("Department find: organizationId={}", orgId);
        List<Department> departments = deptSvc.findOrgById(orgId);
        departments.forEach(d -> d.setEmployees(employeeClient.findByDepartment(d.getDeptId())));
        return departments;
    }
    
}
