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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.cloudcomp.ccoms.org.dao.OrganizationRepository;
import com.cloudcomp.ccoms.org.model.Organization;
import com.cloudcomp.ccoms.org.service.OrganizationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationRestControllerTest {

    List<Organization> orgs = new ArrayList<Organization>();
    Organization org1 = new Organization(1L, "Oracle", "Pune");
    Organization org2 = new Organization(2L, "NEC", "Noida");

    @Mock
    OrganizationRepository orgRepo;

    @Autowired
    OrganizationService orgSvc;

    @Test
    public void testAddorgs() {

        // Given -- Setup eg. initializing or preparing
        orgs.add(org1);
        orgs.add(org2);
        when(orgRepo.saveAll(orgs)).thenReturn(orgs);

        // when
        List<Organization> orgs1 = orgSvc.addorgs(orgs);

        // then
        assertEquals(2, orgs1.size());
    }

    @Test
    public void testFindAllOrganizations() {
        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findAll()).thenReturn(Stream.of(org1, org2).collect(Collectors.toList()));

        // when
        int orgcount = orgSvc.findAllOrganizations().size();

        // then
        assertEquals(2, orgcount);
    }

    @Test
    public void testFindById() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));

        // when
        Organization org = orgSvc.findById(org_id);

        // then
        assertEquals("Oracle", org.getName());

    }

    @Test
    public void testFindOrgUsingId() {
        fail("Not yet implemented");

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));

        // when
        Organization org = orgSvc.findOrgUsingId(org_id);

        // then
        assertEquals("Oracle", org.getName());

    }

    @Test
    public void testFindByIdWithDepartmentsAndEmployees() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));

        // when
        Organization org = orgSvc.findByIdWithDepartmentsAndEmployees(org_id);

        // then
        assertEquals("Oracle", org.getName());
    }

    @Test
    public void testFindByIdWithEmployees() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));

        // when
        Organization org = orgSvc.findByIdWithEmployees(org_id);

        // then
        assertEquals("Oracle", org.getName());

    }

    @Test
    public void testGetDeptsEmpsAndOrgsInfo() {

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findAll()).thenReturn(Stream.of(org1, org2).collect(Collectors.toList()));

        // when
        List<Organization> orgs = orgSvc.getDeptsEmpsAndOrgsInfo();

        // then
        assertEquals(2, orgs.size());
    }

}
