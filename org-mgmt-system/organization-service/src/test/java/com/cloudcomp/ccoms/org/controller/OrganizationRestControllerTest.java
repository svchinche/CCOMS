package com.cloudcomp.ccoms.org.controller;

import static org.junit.Assert.*;
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

import com.cloudcomp.ccoms.org.client.DepartmentClient;
import com.cloudcomp.ccoms.org.client.EmployeeClient;
import com.cloudcomp.ccoms.org.dao.OrganizationRepository;
import com.cloudcomp.ccoms.org.model.Department;
import com.cloudcomp.ccoms.org.model.Employee;
import com.cloudcomp.ccoms.org.model.Organization;
import com.cloudcomp.ccoms.org.service.OrganizationService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrganizationRestControllerTest {

    @MockBean
    OrganizationRepository orgRepo;

    @MockBean
    EmployeeClient empClient;

    @MockBean
    DepartmentClient deptClient;

    @Autowired
    OrganizationRestController orgRestController;

    List<Organization> orgs = new ArrayList<Organization>();
    Organization org1 = new Organization(1L, "Oracle", "Pune");
    Organization org2 = new Organization(2L, "NEC", "Noida");

    List<Employee> emps = new ArrayList<Employee>();
    Employee emp1 = new Employee(1, "Suyog", 29, "DevOps Engineer");
    Employee emp2 = new Employee(2, "Sachin", 29, "DevOps Engineer-2");

    List<Department> depts = new ArrayList<Department>();
    Department dept1 = new Department(11, 1, "ORMB");
    Department dept2 = new Department(12, 1, "I-FLEX");

    @Test
    public void testAddorgs() {

        // Given -- Setup eg. initializing or preparing
        orgs.add(org1);
        orgs.add(org2);
        when(orgRepo.saveAll(orgs)).thenReturn(orgs);

        // when
        List<Organization> orgs1 = orgRestController.addorgs(orgs);

        // then
        assertEquals(2, orgs1.size());
    }

    @Test
    public void testFindAllOrganizations() {
        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findAll()).thenReturn(Stream.of(org1, org2).collect(Collectors.toList()));

        // when
        int orgcount = orgRestController.findAllOrganizations().size();

        // then
        assertEquals(2, orgcount);
    }

    @Test
    public void testFindById() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));

        // when
        Organization org = orgRestController.findById(org_id);

        // then
        assertEquals("Oracle", org.getName());

    }

    @Test
    public void testFindOrgUsingId() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));

        // when
        Organization org = orgRestController.findOrgUsingId(org_id);

        // then
        assertEquals("Oracle", org.getName());

    }

    @Test
    public void testFindByIdWithDepartmentsAndEmployees() {

        Long org_id = 1L;

        depts.add(dept1);

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));
        when(deptClient.findDeptsWithEmpsUsingOrgId(org_id)).thenReturn(depts);

        // when
        Organization org = orgRestController.findByIdWithDepartmentsAndEmployees(org_id);

        // then
        assertEquals("Oracle", org.getName());
    }

    @Test
    public void testFindByIdWithEmployees() {

        Long org_id = 1L;

        emps.add(emp1);
        // Given -- Setup eg. initializing or preparing

        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));
        when(empClient.findEmpsByOrgId(org_id)).thenReturn(emps);

        // when
        Organization org = orgRestController.findByIdWithEmployees(org_id);

        // then
        assertEquals("Oracle", org.getName());

    }

    @Test
    public void testGetDeptsEmpsAndOrgsInfo() {

        depts.add(dept1);

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findAll()).thenReturn(Stream.of(org1, org2).collect(Collectors.toList()));
        when(deptClient.findDeptsWithEmpsUsingOrgId(1L)).thenReturn(depts);

        // when
        List<Organization> orgs = orgRestController.getDeptsEmpsAndOrgsInfo();

        // then
        assertEquals(2, orgs.size());
    }

}
