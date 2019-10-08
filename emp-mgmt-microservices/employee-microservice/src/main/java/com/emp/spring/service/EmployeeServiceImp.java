package com.emp.spring.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emp.spring.model.Employee;
import com.emp.spring.repository.EmployeeRepository;

import org.springframework.data.mongodb.repository.Query;


@Service
@Transactional
public class EmployeeServiceImp implements EmployeeService  {
	
    @Autowired
	EmployeeRepository empRepository;

	public void createEmployee(Employee emp) {
		// TODO Auto-generated method stub
	    empRepository.save(emp);
	}

	public List<Employee> getEmployee() {
		// TODO Auto-generated method stub
		return (List<Employee>) empRepository.findAll();
	}

	public Optional<Employee> findEmpById(BigInteger id) {
		// TODO Auto-generated method stub
		return empRepository.findById(id);
	}

	public Employee update(Employee emp, BigInteger id) {
		// TODO Auto-generated method stub
		return empRepository.save(emp);
	}

	public void deleteEmpById(BigInteger id) {
		// TODO Auto-generated method stub
	    empRepository.deleteById(id);
	}

	public Employee updatePartially(Employee emp, BigInteger id) {
		// TODO Auto-generated method stub
		return empRepository.save(emp);
	}

	
    @Override
    public Employee findEmpsByDeptId(int deptId) {
        // TODO Auto-generated method stub
        return empRepository.findByDeptId(deptId);
    }

    @Override
    public Employee findEmpsByOrgId(int orgId) {
        // TODO Auto-generated method stub
        return empRepository.findByOrgId(orgId);
    }	
    

}
