package com.emp.spring.controller;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.emp.spring.model.Employee;

import com.emp.spring.service.EmployeeService;


@RestController
@RequestMapping(value={"/","/employee"})
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService empsvc;



    @GetMapping("/")
    public String get(){
        return "Please give url as hostname/employee/get";

    }
    
    /*
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
        LOGGER.info("Fetching Employee with id " + id);
        
        
        Employee emp = (Employee) empsvc.findEmpById(id);

        if (emp == null) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(HttpStatus.OK);
    }*/
    
    @GetMapping("/{id}")
    public Optional<Employee> getEmpById( @PathVariable("id") BigInteger id) {     
        Optional<Employee> tasks= empsvc.findEmpById(id);
        return tasks;

    }


    @PostMapping("/create")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee Employee, UriComponentsBuilder ucBuilder){

        LOGGER.info("Creating Employee "+Employee.getName());
        empsvc.createEmployee(Employee);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Employee/{id}").buildAndExpand(Employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public List<Employee> getAllEmployee() {	 
        List<Employee> tasks=empsvc.getEmployee();
        return tasks;

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee currentEmployee)
    {
        LOGGER.info("");
        Optional<Employee> emp =  empsvc.findEmpById(currentEmployee.getId());
        if (emp==null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        empsvc.update(currentEmployee, currentEmployee.getId());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") BigInteger id){
        /*
        Employee emp = (Employee) empsvc.findEmpById(id);
        if (emp == null) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        */
        empsvc.deleteEmpById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeePartially(@PathVariable("id") BigInteger id, @RequestBody Employee currentEmployee){
        Optional<Employee> emp = empsvc.findEmpById(id);
        
        
        if(emp ==null){
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        Employee usr =	empsvc.updatePartially(currentEmployee, id);
        return new ResponseEntity<Employee>(usr, HttpStatus.OK);
    }


    @GetMapping("/department/{deptId}")
    public Employee findByDepartment(@PathVariable("deptId") int deptId) {
        LOGGER.info("Employee find: departmentId={}", deptId);
        return empsvc.findEmpsByDeptId(deptId);
    }

    @GetMapping("/organization/{orgId}")
    public Employee findByOrganization(@PathVariable("orgId") int orgId) {
        LOGGER.info("Employee find: organizationId={}", orgId);
        return empsvc.findEmpsByOrgId(orgId);
    }
}
