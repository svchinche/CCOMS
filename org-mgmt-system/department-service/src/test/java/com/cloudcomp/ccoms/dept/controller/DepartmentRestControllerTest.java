package com.cloudcomp.ccoms.dept.controller;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.cloudcomp.ccoms.dept.dao.DepartmentRepository;
import com.cloudcomp.ccoms.dept.model.Department;
import com.cloudcomp.ccoms.dept.service.DepartmentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentRestControllerTest {

    List<Department> depts = new ArrayList<Department>();
    Department dept1 = new Department(11L, 1, "ORMB");
    Department dept2 = new Department(12L, 1, "I-FLEX");

    @Autowired
    private DepartmentService deptSvc;

    @MockBean
    private DepartmentRepository deptRepo;

    @Test
    public void testGet() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllDepts() {
        // Given -- Setup eg. initializing or preparing
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        int deptcount = deptSvc.getAllDepts().size();

        // then
        assertEquals(2, deptcount);

    }

    @Test
    public void testCreateDept() {

        // Given -- Setup eg. initializing or preparing

        when(deptRepo.save(dept1)).thenReturn(dept1);

        // when
        Department dept = deptSvc.createDept(dept1);

        // then
        assertEquals("ORMB", dept.getName());
    }

    @Test
    public void testCreateDepts() {

        depts.add(dept1);
        depts.add(dept2);
        when(deptRepo.saveAll(depts)).thenReturn(depts);

        // when
        List<Department> depts1 = deptSvc.createDepts(depts);

        // then
        assertEquals(2, depts1.size());
    }

    @Test
    public void testDeleteAllDepts() {

        depts.add(dept1);
        depts.add(dept2);

        // Given -- Setup eg. initializing or preparing
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept1));

        // when
        deptSvc.deleteAllDepts();

        // then
        verify(deptRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testGetDeptById() {

        // Given
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept1));

        // when
        Department dept = deptSvc.getDeptById(11L);

        // then
        assertEquals("ORMB", dept.getName());

    }

    @Test
    public void testGetDeptsByOrgId() {
        // Given
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        List<Department> depts = deptSvc.getDeptsByOrgId(1L);

        // then
        assertEquals(1, depts.size());
    }

    @Test
    public void testGetDeptswithEmpsUsingOrgid() {

        // Given
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        List<Department> depts = deptSvc.getDeptswithEmpsUsingOrgid(1L);

        // then
        assertEquals(1, depts.size());
    }

    @Test
    public void testGetDeptswithEmps() {
        // Given
        when(deptRepo.findAll()).thenReturn(Stream.of(dept1, dept2).collect(Collectors.toList()));

        // when
        List<Department> depts = deptSvc.getDeptswithEmps();

        // then
        assertEquals(2, depts.size());
    }

}
