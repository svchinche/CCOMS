package com.cloudcomp.ccoms.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcomp.ccoms.emp.controller.ResourceNotFoundException;
import com.cloudcomp.ccoms.emp.dao.EmployeeRepository;
import com.cloudcomp.ccoms.emp.model.Employee;

@Service
public class EmployeeService {

    private static String empIdNotFound = "Employee not found for id ";

    @Autowired
    EmployeeRepository empRepo;

    Employee emp;
    
    public Employee getEmpById(Long id) throws ResourceNotFoundException {
        return empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(empIdNotFound + id));
    }

    public Employee addEmp(Employee emp) {
        empRepo.save(emp);
        return emp;
    }

    public List<Employee> addEmps(List<Employee> emps) {
        empRepo.saveAll(emps);
        return emps;
    }

    public List<Employee> getAllEmps() {
        return (List<Employee>) empRepo.findAll();
    }

    public void updateEmp(Employee emp){
        empRepo.save(this.emp);
    }

    public void deleteEmp(Long id) throws ResourceNotFoundException {
        emp = empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(empIdNotFound + id));
        empRepo.deleteById(id);
    }

    public Employee updateEmpPartially(Long id) throws ResourceNotFoundException {
        emp = empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(empIdNotFound + id));
        return empRepo.save(emp);
    }

    public List<Employee> findByDept(int deptId) {
        return empRepo.findEmpsByDeptId(deptId);
    }

    public List<Employee> findByOrg(int orgId) {
        return empRepo.findEmpsByOrgId(orgId);
    }

}
