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




@RestController
@RequestMapping(value={"/","/employee"})
public class EmployeeRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);
    @Autowired
    EmployeeRepository empRepository;



    @GetMapping("/")
    public String get(){
        return "Please give url as hostname/employee/get";

    }
     
    // Swagger doesnt support biginteger
    @GetMapping("/{id}")
    public Optional<Employee> getEmpById( @PathVariable("id") Long id) {     
        Optional<Employee> tasks= empRepository.findById(id);
        return tasks;

    }


    @PostMapping("/addemp")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee emp, UriComponentsBuilder ucBuilder){

        LOGGER.info("Creating Employee "+emp.getName());
        empRepository.save(emp);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Employee/{id}").buildAndExpand(emp.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    @PostMapping("/addemps")
    public void createEmployees(@RequestBody List<Employee> emps){
        empRepository.saveAll(emps);
    }

    
    
    @GetMapping("/get")
    public List<Employee> getAllEmployee() {	 
        List<Employee> tasks= (List<Employee>) empRepository.findAll();
        return tasks;

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee currentEmployee)
    {
        
        Optional<Employee> emp =  empRepository.findById(currentEmployee.getId());
        if (emp==null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        empRepository.save(currentEmployee);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
      
        empRepository.deleteById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeePartially(@PathVariable("id") Long id, @RequestBody Employee currentEmployee){
        Optional<Employee> emp = empRepository.findById(id);
        
        
        if(emp ==null){
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        Employee usr =	empRepository.save(currentEmployee);
        return new ResponseEntity<Employee>(usr, HttpStatus.OK);
    }

    
    @GetMapping("/dept/{deptId}")
    public List<Employee> findByDepartment(@PathVariable("deptId") int deptId) {
        LOGGER.info("Employee find: departmentId={}", deptId);
        return empRepository.findEmpsByDeptId(deptId);
    }

    @GetMapping("/org/{orgId}")
    public List<Employee> findByOrganization(@PathVariable("orgId") int orgId) {
        LOGGER.info("Employee find: organizationId={}", orgId);
        return empRepository.findEmpsByOrgId(orgId);
    }
}
