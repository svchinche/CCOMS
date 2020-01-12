package com.cloudcomp.ccoms.dept.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cloudcomp.ccoms.dept.Application;
import com.cloudcomp.ccoms.dept.dao.DepartmentRepository;



@ContextConfiguration
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AbstractDeptRestCntr {
    
    @MockBean
    private DepartmentRepository deptrepo;
    
}


