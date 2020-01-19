package com.cloudcomp.ccoms.org.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.cloudcomp.ccoms.org.client.DepartmentClient;
import com.cloudcomp.ccoms.org.client.EmployeeClient;
import com.cloudcomp.ccoms.org.dao.OrganizationRepository;
import com.cloudcomp.ccoms.org.model.Department;
import com.cloudcomp.ccoms.org.model.Employee;
import com.cloudcomp.ccoms.org.model.Organization;
import com.cloudcomp.ccoms.org.service.OrganizationService;
import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class OrgStepDefs extends AbstractOrgRestCntr {

    List<Organization> orgs = new ArrayList<Organization>();
    List<Employee> emps = new ArrayList<Employee>();
    List<Department> depts = new ArrayList<Department>();

    Employee emp1 = new Employee(1, "Suyog", 29, "DevOps Engineer");
    Employee emp2 = new Employee(2, "Sachin", 29, "DevOps Engineer-2");

    Department dept1 = new Department(11, 1, "ORMB");
    Department dept2 = new Department(12, 1, "I-FLEX");

    @Autowired
    OrganizationRepository orgRepo;

    @Autowired
    EmployeeClient empClient;

    @Autowired
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

    private final Logger log = LoggerFactory.getLogger(OrgStepDefs.class);

    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }

    @After
    public void end() {
        log.info("-------------- End scenario --------------");
    }

    @Given("Following Organization information is available in store")
    public void following_Organization_information_is_available_in_store(DataTable dataTable) {
        this.orgs = dataTable.asList(Organization.class);

    }

    @When("I set HTTP GET request RESTful API with getall URI")
    public void i_set_HTTP_GET_request_RESTful_API_with_getall_URI() {
        when(orgRepo.findAll()).thenReturn(this.orgs);
    }

    @Then("I should get valid HTTP response code {int} of GET request for all emps depts and orgs")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_for_all_emps_depts_and_orgs(Integer int1) {
        Assertions.assertEquals(2, orgRestController.findAllOrganizations().size());
    }

    @When("I set HTTP GET request RESTful API with URI {long}")
    public void i_set_HTTP_GET_request_RESTful_API_with_URI(Long org_id) {
        when(orgRepo.findById(org_id)).thenReturn(Optional.of(orgs.get(0)));
        when(orgRepo.findById(3L)).thenReturn(Optional.empty());

    }

    @Then("I should get {int} response code of HTTP GET request using orgid")
    public void i_should_get_response_code_of_HTTP_GET_request_using_orgid(Integer int1) {
        Organization org = orgRestController.findById(1L);
        Organization neg_org = orgRestController.findById(3L);

        // then
        Assertions.assertEquals("Oracle", org.getName());
        Assertions.assertEquals(null, neg_org);
    }

    @When("I set HTTP GET request RESTful API with org with {long} id URI to get employee information")
    public void i_set_HTTP_GET_request_RESTful_API_with_org_with_id_URI_to_get_employee_information(Long orgid) {
        emps.add(emp1);
        // Given -- Setup eg. initializing or preparing

        when(orgRepo.findById(orgid)).thenReturn(Optional.of(orgs.get(0)));
        when(empClient.findEmpsByOrgId(orgid)).thenReturn(emps);
        when(orgRepo.findById(3L)).thenReturn(Optional.empty());
    }

    @Then("I should get valid HTTP response code {int} of GET request to get employee info using org")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_to_get_employee_info_using_org(Integer int1) {
        Organization org = orgRestController.findByIdWithEmployees(1L);
        Organization neg_org = orgRestController.findByIdWithEmployees(3L);

        // then
        Assertions.assertEquals("Oracle", org.getName());
        Assertions.assertEquals(null, neg_org);

    }

    @When("I set HTTP POST request RESTful API with resource URI - all at once")
    public void i_set_HTTP_POST_request_RESTful_API_with_resource_URI_all_at_once() {
        when(orgRepo.saveAll(orgs)).thenReturn(orgs);

    }

    @Then("I should get valid HTTP response code {int} of POST request to add all Organization at once")
    public void i_should_get_valid_HTTP_response_code_of_POST_request_to_add_all_Organization_at_once(Integer int1) {
        List<Organization> orgs1 = orgRestController.addorgs(orgs);
        Assertions.assertEquals(2, orgs1.size());
    }

    @When("I initialize all Getters and Setters")
    public void i_initialize_all_Getters_and_Setters() {
        // Write code here that turns the phrase above into concrete actions
        log.info("----------Initializing Getters and Settters----------");
    }

    @Then("All Getters and Setters should be initialized")
    public void all_Getters_and_Setters_should_be_initialized() {

        Validator validator = ValidatorBuilder.create().with(new SetterMustExistRule()).with(new GetterMustExistRule())
                .with(new SetterTester()).with(new GetterTester()).build();
        validator.validate(PojoClassFactory.getPojoClass(Department.class));
        validator.validate(PojoClassFactory.getPojoClass(Employee.class));
        validator.validate(PojoClassFactory.getPojoClass(Organization.class));
    }

}
