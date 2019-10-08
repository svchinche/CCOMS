package com.dept.spring.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dept.spring.model.Department;
import com.dept.spring.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentServiceImp implements DepartmentService {
	@Autowired
	DepartmentRepository deptRepository;

	public void createDepartment(Department dept) {
		// TODO Auto-generated method stub
	    deptRepository.save(dept);
	}

	public List<Department> getDepartment() {
		// TODO Auto-generated method stub
		return (List<Department>) deptRepository.findAll();
	}


	public Department update(Department dept, BigInteger id) {
		// TODO Auto-generated method stub
		return deptRepository.save(dept);
	}

	public void deleteDepartmentById(BigInteger id) {
		// TODO Auto-generated method stub
	    deptRepository.deleteById(id);
	}

	public Department updatePartially(Department dept, BigInteger id) {
		// TODO Auto-generated method stub
		return deptRepository.save(dept);
	}

    @Override
    public Optional<Department> findDeptById(BigInteger deptId) {
        // TODO Auto-generated method stub
        return deptRepository.findById(deptId); 
      
    }  

    
    @Override
    public List<Department> findOrgById(BigInteger orgId) {
        // TODO Auto-generated method stub
        return deptRepository.findByOrgId(orgId); 
      
    }  

}
