package com.emp.spring.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.emp.spring.model.Employee;

public interface EmployeeService   {
	
    public void createEmployee(Employee emp);
    
	public List<Employee> getEmployee();
	
	
	public Employee update(Employee emp, BigInteger id);
	
	public void deleteEmpById(BigInteger id);
	
	public Employee updatePartially(Employee emp, BigInteger id);
	
	public Optional<Employee> findEmpById(BigInteger id);

    Employee findEmpsByDeptId(int deptId);

    Employee findEmpsByOrgId(int orgId);
    
    
		
}
