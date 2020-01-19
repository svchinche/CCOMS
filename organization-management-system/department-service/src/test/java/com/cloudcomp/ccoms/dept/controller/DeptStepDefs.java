package com.cloudcomp.ccoms.dept.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudcomp.ccoms.dept.client.EmployeeClient;
import com.cloudcomp.ccoms.dept.dao.DepartmentRepository;
import com.cloudcomp.ccoms.dept.model.Department;
import com.cloudcomp.ccoms.dept.model.Employee;
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

public class DeptStepDefs extends AbstractDeptRestCntr {

    List<Department> depts = new ArrayList<Department>();
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

    @Autowired
    private EmployeeClient empClient;

    @Autowired
    private DepartmentRepository deptRepo;

    private final Logger log = LoggerFactory.getLogger(DeptStepDefs.class);

    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }

    @After
    public void end() {
        log.info("-------------- End scenario --------------");
    }

    @Given("Following Department information is available in store")
    public void following_Department_information_is_available_in_store(io.cucumber.datatable.DataTable dataTable) {
        this.depts = dataTable.asList(Department.class);
    }

    @When("I set HTTP GET request RESTful API with depts URI")
    public void i_set_HTTP_GET_request_RESTful_API_with_depts_URI() {
        when(deptRepo.findAll()).thenReturn(this.depts);
    }

    @Then("I should get valid HTTP response code {int} of GET request with all Departments information in UI")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_with_all_Departments_information_in_UI(
            Integer int1) {
        Assert.assertEquals(this.depts.size(), deptRestController.getAllDepts().size());
    }

    @When("I set HTTP GET request RESTful API with URI dept {long}")
    public void i_set_HTTP_GET_request_RESTful_API_with_URI_dept(Long dept_id) {
        List<Department> tmp_depts = new ArrayList<Department>();
        for (Department department : depts) {
            if (department.getId() == dept_id) {
                when(deptRepo.findById(dept_id)).thenReturn(Optional.of(department));
                tmp_depts.add(department);
                break;
            }
        }

        if (tmp_depts.size() == 0) {
            when(deptRepo.findById(dept_id)).thenReturn(Optional.empty());
        }
    }

    @Then("I should get {int} response code of HTTP GET request for depatrtment")
    public void i_should_get_response_code_of_HTTP_GET_request_for_depatrtment(Integer int1) {
        Department dept = deptRestController.getDeptById(11L);
        Department neg_dept = deptRestController.getDeptById(13L);
        Assertions.assertEquals("ORMB", dept.getName());
        Assertions.assertEquals(null, neg_dept);
    }

    @When("I set HTTP GET request RESTful API with org with {long} id URI")
    public void i_set_HTTP_GET_request_RESTful_API_with_org_with_id_URI(Long orgId) {
        List<Department> temp_depts = new ArrayList<Department>();
        for (Department department : depts) {
            if (department.getOrgId() == orgId) {
                temp_depts.add(department);
            }
        }
        if (temp_depts.size() == 0) {
            when(deptRepo.findDeptByOrgId(Long.valueOf(orgId))).thenReturn(temp_depts);
        } else {
            when(deptRepo.findDeptByOrgId(Long.valueOf(orgId))).thenReturn(temp_depts);
        }
    }

    @Then("I should get valid HTTP response code {int} of GET request department for respective org")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_department_for_respective_org(Integer int1) {
        List<Department> depts = deptRestController.getDeptsByOrgId(1L);
        Assertions.assertEquals(0, depts.size());
    }

    @When("I set HTTP GET request RESTful API with org {int} withemp URI to get emp and dept info")
    public void i_set_HTTP_GET_request_RESTful_API_with_org_with_org_withemp_URI(int orgId) {
        List<Department> temp_depts = new ArrayList<Department>();
        for (Department department : depts) {
            if (department.getOrgId() == orgId) {
                temp_depts.add(department);
            }
        }
        if (temp_depts.size() == 0) {
            when(deptRepo.findDeptByOrgId(Long.valueOf(orgId))).thenReturn(temp_depts);
        } else {
            when(deptRepo.findDeptByOrgId(Long.valueOf(orgId))).thenReturn(temp_depts);
        }

    }

    @Then("I should get valid HTTP response code 200 of GET request to get emp and dept info using org")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_for_respective_dept() {
        List<Department> depts = deptRestController.getDeptsByOrgId(1L);
        Assertions.assertEquals(0, depts.size());
    }

    @When("I set HTTP GET request RESTful API with org\\/withemp URI")
    public void i_set_HTTP_GET_request_RESTful_API_with_org_withemp_URI() {
        when(deptRepo.findAll()).thenReturn(depts);
    }

    @Then("I should get valid HTTP response code {int} of GET request with all Depts information with emp and orgs")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_with_all_Depts_information_with_emp_and_orgs(
            Integer int1) {
        List<Department> depts = deptRestController.getDeptswithEmps();
        Assertions.assertEquals(2, depts.size());
    }

    @When("I set HTTP POST request RESTful API with resource URI")
    public void i_set_HTTP_POST_request_RESTful_API_with_resource_URI() {
        for (Department department : depts) {
            when(deptRepo.save(department)).thenReturn(department);
        }
    }

    @Then("I should get valid HTTP response code {int} of POST request to add one Department at a time")
    public void i_should_get_valid_HTTP_response_code_of_POST_request_to_add_one_Department_at_a_time(Integer int1) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Object> responseEntity = deptRestController.addDept(depts.get(0));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/11");
    }

    @When("I set HTTP POST request RESTful API with resource URI - all at once")
    public void i_set_HTTP_POST_request_RESTful_API_with_resource_URI_all_at_once() {
        when(deptRepo.saveAll(depts)).thenReturn(depts);
    }

    @Then("I should get valid HTTP response code {int} of POST request to add all Department at once")
    public void i_should_get_valid_HTTP_response_code_of_POST_request_to_add_all_Department_at_once(Integer int1) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Object> responseEntity = deptRestController.addDepts(depts);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/all");
    }

    @When("I set HTTP DELETE REST API")
    public void i_set_HTTP_DELETE_REST_API() {
        when(deptRepo.findAll()).thenReturn(depts);
    }

    @Then("I should get valid HTTP response code {int} of DELETE request to delete all Department at once")
    public void i_should_get_valid_HTTP_response_code_of_DELETE_request_to_delete_all_Department_at_once(Integer int1) {
        deptRestController.deleteAllDepts();
        verify(deptRepo, times(1)).deleteAll();
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
    }

}
