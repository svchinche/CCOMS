package com.dept.spring.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.dept.spring.model.Department;

public interface DepartmentService {
	public void createDepartment(Department dept);
	public List<Department> getDepartment();
	
	public Department update(Department dept, BigInteger deptId);
    public void deleteDepartmentById(BigInteger deptId);
	public Department updatePartially(Department dept, BigInteger deptId);
    
	public Optional<Department> findDeptById(BigInteger deptId);

    List<Department> findOrgById(BigInteger orgId);
   

}