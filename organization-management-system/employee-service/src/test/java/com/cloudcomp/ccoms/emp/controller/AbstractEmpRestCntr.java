package com.cloudcomp.ccoms.emp.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cloudcomp.ccoms.emp.Application;
import com.cloudcomp.ccoms.emp.dao.EmployeeRepository;



@ContextConfiguration
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AbstractEmpRestCntr {
    
    @MockBean
    private EmployeeRepository emprepo;
    
}
