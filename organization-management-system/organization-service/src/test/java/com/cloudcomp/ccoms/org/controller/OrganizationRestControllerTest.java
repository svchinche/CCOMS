package com.cloudcomp.ccoms.org.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;

import com.cloudcomp.ccoms.org.Application;
import com.cloudcomp.ccoms.org.client.DepartmentClient;
import com.cloudcomp.ccoms.org.client.EmployeeClient;
import com.cloudcomp.ccoms.org.dao.OrganizationRepository;
import com.cloudcomp.ccoms.org.model.Department;
import com.cloudcomp.ccoms.org.model.Employee;
import com.cloudcomp.ccoms.org.model.Organization;
import com.cloudcomp.ccoms.org.service.OrganizationService;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OrganizationRestControllerTest {

    @MockBean
    OrganizationRepository orgRepo;

    @MockBean
    EmployeeClient empClient;

    @MockBean
    DepartmentClient deptClient;

    @InjectMocks
    @Autowired
    OrganizationRestController orgRestController;

    @InjectMocks
    @Autowired
    OrganizationViewController orgViewController;

    @Mock
    private Model model;

    @Autowired
    OrganizationService orgSvc;

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
        Assertions.assertEquals(2, orgs1.size());
    }

    @Test
    public void testFindAllOrganizations() {
        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findAll()).thenReturn(Stream.of(org1, org2).collect(Collectors.toList()));

        // when
        int orgcount = orgRestController.findAllOrganizations().size();

        // then
        Assertions.assertEquals(2, orgcount);
    }

    @Test
    public void testFindById() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));
        when(orgRepo.findById(3L)).thenReturn(Optional.empty());

        // when
        Organization org = orgRestController.findById(org_id);
        Organization neg_org = orgRestController.findById(3L);

        // then
        Assertions.assertEquals("Oracle", org.getName());
        Assertions.assertEquals(null,neg_org);

    }

    @Test
    public void testFindOrgUsingId() {

        Long org_id = 1L;

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));
        when(orgRepo.findById(3L)).thenReturn(Optional.empty());

        // when
        Organization org = orgRestController.findOrgUsingId(org_id);
        Organization neg_org = orgRestController.findOrgUsingId(3L);

        // then
        Assertions.assertEquals("Oracle", org.getName());
        Assertions.assertEquals(null,neg_org);


    }

    @Test
    public void testFindByIdWithDepartmentsAndEmployees() {

        Long org_id = 1L;

        depts.add(dept1);

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));
        when(deptClient.findDeptsWithEmpsUsingOrgId(org_id)).thenReturn(depts);
        when(orgRepo.findById(3L)).thenReturn(Optional.empty());
        
        // when
        Organization org = orgRestController.findByIdWithDepartmentsAndEmployees(org_id);
        Organization neg_org = orgRestController.findByIdWithDepartmentsAndEmployees(3L);

        // then
        Assertions.assertEquals("Oracle", org.getName());
        Assertions.assertEquals(null,neg_org);
    }

    @Test
    public void testFindByIdWithEmployees() {

        Long org_id = 1L;

        emps.add(emp1);
        // Given -- Setup eg. initializing or preparing

        when(orgRepo.findById(org_id)).thenReturn(Optional.of(org1));
        when(empClient.findEmpsByOrgId(org_id)).thenReturn(emps);
        when(orgRepo.findById(3L)).thenReturn(Optional.empty());

        // when
        Organization org = orgRestController.findByIdWithEmployees(org_id);
        Organization neg_org = orgRestController.findByIdWithEmployees(3L);

        // then
        Assertions.assertEquals("Oracle", org.getName());
        Assertions.assertEquals(null,neg_org);

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
        Assertions.assertEquals(2, orgs.size());
    }

    @Test
    void testShowPrettyOutput() {
        
        depts.add(dept1);

        // Given -- Setup eg. initializing or preparing
        when(orgRepo.findAll()).thenReturn(Stream.of(org1, org2).collect(Collectors.toList()));
        when(deptClient.findDeptsWithEmpsUsingOrgId(1L)).thenReturn(depts);

        // when
        List<Organization> orgs = orgRestController.getDeptsEmpsAndOrgsInfo();
        String return_val = orgViewController.showPrettyOutput(model);

        // then
        Assertions.assertEquals(2, orgs.size());
        Assertions.assertEquals("show_pretty_output", return_val);

    }
    
    @Test
    public void testOrgGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(Organization.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.validate(pojoclass);
    }
    
    
    @Test
    public void testEmpGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(Employee.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.validate(pojoclass);
    }
    
    @Test
    public void testDeptGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(Department.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.validate(pojoclass);
    }

}
