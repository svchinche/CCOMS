package com.cloudcomp.ccoms.emp.controller;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudcomp.ccoms.emp.Application;
import com.cloudcomp.ccoms.emp.dao.EmployeeRepository;
import com.cloudcomp.ccoms.emp.model.Employee;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class EmployeeRestControllerTest {

    List<Employee> emps = new ArrayList<Employee>();

    Employee emp1 = new Employee(1L, "Suyog", 29, "DevOps Engineer", 21, 2);
    Employee emp2 = new Employee(2L, "Sachin", 29, "DevOps Engineer-2", 11, 1);

    @InjectMocks
    @Autowired
    private EmployeeRestController empRestController;

    @MockBean
    private EmployeeRepository emprepo;

    @InjectMocks
    @Autowired
    private EmployeeViewController empViewController;

    @Mock
    private Model model;

    @Test
    public void testGetEmpById() throws ResourceNotFoundException {

        // Given -- Setup eg. initializing or preparing
        emps.add(emp1);
        emps.add(emp2);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when -- Mock
        when(emprepo.findById(1L)).thenReturn(Optional.of(emp1));
        when(emprepo.findById(3L)).thenReturn(Optional.empty());

        // when -- actual
        ResponseEntity<Employee> responseEntity = empRestController.getEmpById(1L);
        ResponseEntity<Employee> negResponseEntity = empRestController.getEmpById(3L);
        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testAddEmp() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        when(emprepo.save(emp1)).thenReturn(emp1);
        ResponseEntity<Object> responseEntity = empRestController.addEmp(emp1);
        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/1");
    }

    @Test
    public void testAddEmps() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        when(emprepo.saveAll(emps)).thenReturn(emps);
        ResponseEntity<Object> responseEntity = empRestController.addEmps(emps);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/all");
    }

    @Test
    public void testGetAllEmps() {

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findAll()).thenReturn(Stream.of(emp1, emp2).collect(Collectors.toList()));
        // when
        int empcount = empRestController.getAllEmps().size();
        // then
        Assertions.assertEquals(2, empcount);
    }

    @Test
    public void testUpdateEmp() throws ResourceNotFoundException {

        Employee emp3 = new Employee(3L, "Suraj", 29, "DevOps Engineer-2", 13, 1);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        // Given -- Setup eg. initializing or preparing
        when(emprepo.save(emp1)).thenReturn(emp1);
        when(emprepo.findById(1L)).thenReturn(Optional.of(emp1));
        when(emprepo.findById(3L)).thenReturn(Optional.empty());

        // when
        ResponseEntity<Object> negResponseEntity = empRestController.updateEmp(emp3);
        ResponseEntity<Object> responseEntity = empRestController.updateEmp(emp1);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    public void testDeleteEmp() throws ResourceNotFoundException {

        emps.add(emp1);
        emps.add(emp2);

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findById(1L)).thenReturn(Optional.of(emp1));
        when(emprepo.findById(3L)).thenReturn(Optional.empty());

        // when
        empRestController.deleteEmp(1L);
        ResponseEntity<Object> negResponseEntity = empRestController.deleteEmp(3L);

        // then
        verify(emprepo, times(1)).deleteById(1L);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testUpdateEmpPartially() throws ResourceNotFoundException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        // Given -- Setup eg. initializing or preparing
        when(emprepo.findById(1L)).thenReturn(Optional.of(emp1));
        when(emprepo.save(emp1)).thenReturn(emp1);
        when(emprepo.findById(3L)).thenReturn(Optional.empty());

        // when
        ResponseEntity<Object> responseEntity = empRestController.updateEmpPartially(1L, emp1);
        ResponseEntity<Object> negResponseEntity = empRestController.updateEmpPartially(3L, emp2);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testFindByDept() {
        int dept_id = 11;

        emps.add(emp2);

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findEmpsByDeptId(dept_id)).thenReturn(emps);

        // when
        List<Employee> emps = empRestController.findByDept(dept_id);

        // then
        Assertions.assertEquals(1, emps.size());
    }

    @Test
    public void testFindByOrg() {
        int org_id = 2;

        emps.add(emp1);

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findEmpsByOrgId(org_id)).thenReturn(emps);

        // when
        List<Employee> emps = empRestController.findByOrg(org_id);

        // then
        Assertions.assertEquals(1, emps.size());
    }

    @Test
    public void testGet() {
        String actual = empRestController.get();
        Assertions.assertEquals("Please give url as hostname/employee/get", actual);
    }

    @Test
    void testShowSignUpForm() {

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findAll()).thenReturn(Stream.of(emp1, emp2).collect(Collectors.toList()));
        // when
        int empcount = empRestController.getAllEmps().size();

        String return_val = empViewController.showSignUpForm(model);

        // then
        Assertions.assertEquals(2, empcount);
        Assertions.assertEquals("show_pretty_output", return_val);

    }

}
