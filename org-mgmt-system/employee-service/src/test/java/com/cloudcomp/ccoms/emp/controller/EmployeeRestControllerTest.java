package com.cloudcomp.ccoms.emp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cloudcomp.ccoms.emp.dao.EmployeeRepository;
import com.cloudcomp.ccoms.emp.model.Employee;
import com.cloudcomp.ccoms.emp.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRestControllerTest {

    List<Employee> emps = new ArrayList<Employee>();
    Employee emp1 = new Employee(1L, "Suyog", 29, "DevOps Engineer", 21, 2);
    Employee emp2 = new Employee(2L, "Sachin", 29, "DevOps Engineer-2", 11, 1);

    @Autowired
    private EmployeeService empsvc;

    @Autowired
    private EmployeeRestController empRestController;

    @MockBean
    private EmployeeRepository emprepo;

    @Test
    public void testGetEmpById() throws ResourceNotFoundException {

        // Given -- Setup eg. initializing or preparing

        emps.add(emp1);
        emps.add(emp2);

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findById(1L)).thenReturn(Optional.of(emp1));

        // when
        Employee emp = empsvc.getEmpById(1L);

        // then
        assertEquals("Suyog", emp.getName());
    }

    @Test
    public void testCreateEmp() {
        // Given -- Setup eg. initializing or preparing

        when(emprepo.save(emp1)).thenReturn(emp1);

        // when
        Employee emp = empsvc.createEmp(emp1);

        // then
        assertEquals("Suyog", emp.getName());
    }

    @Test
    public void testCreateEmps() {

        // Given -- Setup eg. initializing or preparing
        emps.add(emp1);
        emps.add(emp2);
        when(emprepo.saveAll(emps)).thenReturn(emps);

        // when
        List<Employee> emps1 = empsvc.createEmps(emps);

        // then
        assertEquals(2, emps1.size());

    }

    @Test
    public void testGetAllEmps() {

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findAll()).thenReturn(Stream.of(emp1, emp2).collect(Collectors.toList()));

        // when
        int empcount = empRestController.getAllEmps().size();

        // then
        assertEquals(2, empcount);
    }

    @Test
    public void testUpdateEmp() {

        // Given -- Setup eg. initializing or preparing
        when(emprepo.save(emp1)).thenReturn(emp1);

        // when
        Employee emp = empsvc.createEmp(emp1);

        // then
        assertEquals("Suyog", emp.getName());
    }

    @Test
    public void testDeleteEmp() throws ResourceNotFoundException {

        emps.add(emp1);
        emps.add(emp2);

        // Given -- Setup eg. initializing or preparing
        when(emprepo.findById(1L)).thenReturn(Optional.of(emp1));

        // when
        empRestController.deleteEmp(1L);

        // then
        verify(emprepo, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateEmpPartially() {

        // Given -- Setup eg. initializing or preparing
        when(emprepo.save(emp1)).thenReturn(emp1);

        // when
        Employee emp = empsvc.createEmp(emp1);

        // then
        assertEquals("Suyog", emp.getName());
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
        assertEquals(1, emps.size());
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
        assertEquals(1, emps.size());
    }

    @Test
    public void testGet() {
        String actual = "expected";
        assertEquals("expected", actual);
    }
}
