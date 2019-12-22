package com.cloudcomp.ccoms.dept.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudcomp.ccoms.dept.Application;
import com.cloudcomp.ccoms.dept.client.EmployeeClient;
import com.cloudcomp.ccoms.dept.dao.DepartmentRepository;
import com.cloudcomp.ccoms.dept.model.Department;
import com.cloudcomp.ccoms.dept.model.Employee;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DepartmentRestControllerTest {

    List<Department> depts = new ArrayList<Department>();
    Department dept1 = new Department(11L, 1, "ORMB");
    Department dept2 = new Department(12L, 1, "I-FLEX");

    Employee emp1 = new Employee(1L, "Suyog", 29, "DevOps Engineer");
    Employee emp2 = new Employee(2L, "Sachin", 29, "DevOps Engineer-2");

    @InjectMocks
    @Autowired
    private DepartmentRestController deptRestController;

    @InjectMocks
    @Autowired
    private DepartmentViewController deptViewController;

    @Mock
    private Model model;

    @MockBean
    private EmployeeClient empClient;

    @MockBean
    private DepartmentRepository deptRepo;

    @Test
    public void testGetAllDepts() {
        // Given -- Setup eg. initializing or preparing
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        int deptcount = deptRestController.getAllDepts().size();

        // then
        Assertions.assertEquals(2, deptcount);

    }

    @Test
    public void testAddDept() {

        // Given -- Setup eg. initializing or preparing
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(deptRepo.save(dept1)).thenReturn(dept1);

        // when
        ResponseEntity<Object> responseEntity = deptRestController.addDept(dept1);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/11");
    }

    @Test
    public void testAddDepts() {

        // Given -- Setup eg. initializing or preparing
        depts.add(dept1);
        depts.add(dept2);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(deptRepo.saveAll(depts)).thenReturn(depts);

        // when
        ResponseEntity<Object> responseEntity = deptRestController.addDepts(depts);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/all");
    }

    @Test
    public void testDeleteAllDepts() {

        depts.add(dept1);
        depts.add(dept2);

        // Given -- Setup eg. initializing or preparing
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        deptRestController.deleteAllDepts();

        // then
        verify(deptRepo, times(1)).deleteAll();
    }

    @Test
    public void testGetDeptById() {

        // Given
        when(deptRepo.findById(11L)).thenReturn(Optional.of(dept1));
        when(deptRepo.findById(13L)).thenReturn(Optional.empty());

        // when
        Department dept = deptRestController.getDeptById(11L);
        Department neg_dept = deptRestController.getDeptById(13L);

        // then
        Assertions.assertEquals("ORMB", dept.getName());
        Assertions.assertEquals(null,neg_dept);

    }

    @Test
    public void testGetDeptsByOrgId() {
        // Given
        when(deptRepo.findDeptByOrgId(1L)).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        List<Department> depts = deptRestController.getDeptsByOrgId(1L);

        // then
        Assertions.assertEquals(2, depts.size());
    }

    @Test
    public void testGetDeptswithEmpsUsingOrgid() {

        // Given

        when(empClient.findEmpsByDeptId(11L)).thenReturn(Stream.of(emp1, emp2).collect(Collectors.toList()));

        when(deptRepo.findDeptByOrgId(1L)).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        List<Department> depts = deptRestController.getDeptswithEmpsUsingOrgid(1L);

        // then
        Assertions.assertEquals(2, depts.size());
    }

    @Test
    public void testGetDeptswithEmps() {

        // Given
        when(empClient.findEmpsByDeptId(11L)).thenReturn(Stream.of(emp1, emp2).collect(Collectors.toList()));

        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        List<Department> depts = deptRestController.getDeptswithEmps();

        // then
        Assertions.assertEquals(2, depts.size());
    }

    @Test
    public void testGet() {
        String actual = deptRestController.get();
        String expected = "Please give url " + "Use dept/id to get department using id and depts to get the departments";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testShowPrettyOutput() {
        // Given
        when(empClient.findEmpsByDeptId(11L)).thenReturn(Stream.of(emp1, emp2).collect(Collectors.toList()));
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        String return_val = deptViewController.showPrettyOutput(model);
        List<Department> depts = deptRestController.getDeptswithEmps();

        // then
        Assertions.assertEquals(2, depts.size());
        Assertions.assertEquals("show_pretty_output", return_val);

    }
}
