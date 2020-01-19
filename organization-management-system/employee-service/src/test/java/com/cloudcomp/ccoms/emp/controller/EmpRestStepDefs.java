package com.cloudcomp.ccoms.emp.controller;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.cloudcomp.ccoms.emp.dao.EmployeeRepository;
import com.cloudcomp.ccoms.emp.model.Employee;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class EmpRestStepDefs extends AbstractEmpRestCntr implements En {

    List<Employee> emps = new ArrayList<Employee>();
    private final Logger log = LoggerFactory.getLogger(EmpRestStepDefs.class);
    
    Long empid;

    final Employee emp1 = new Employee(1L, "Suyog", 29, "DevOps Engineer", 21, 2);
    final Employee emp2 = new Employee(5L, "Sanjay", 29, "DevOps Engineer", 21, 2);

    @InjectMocks
    @Autowired
    private EmployeeRestController empRestController;

    @Autowired
    private EmployeeRepository emprepo;

    @InjectMocks
    @Autowired
    private EmployeeViewController empViewController;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }

    @After
    public void end() {
        log.info("-------------- End scenario --------------");
    }

    @Given("Following Employee information is available in store")
    public void given_emp_dataset(DataTable empDataTable) {
        this.emps = empDataTable.asList(Employee.class);
    }

    @When("I set HTTP GET request RESTful API with URI {long}")
    public void get_rest_api_to_get_single_emp_info(final Long empid) {
        this.empid = empid;
        if (empid >= 3L) {
            when(emprepo.findById(empid)).thenReturn(Optional.empty());
        } else {
            when(emprepo.findById(empid)).thenReturn(Optional.of(emps.get(empid.intValue())));
        }
    }

    @When("I set HTTP GET request RESTful API with get URI")
    public void get_rest_api_to_get_emps_info() {
        when(emprepo.findAll()).thenReturn(emps);
    }

    @When("I set HTTP GET request RESTful API with pretty URI")
    public void i_set_HTTP_GET_request_RESTful_API_with_pretty_URI() {
        when(emprepo.findAll()).thenReturn(emps);
    }

    @When("I set HTTP DELETE REST API with URI {int}")
    public void i_set_HTTP_DELETE_REST_API_with_URI(Integer int1) {

    }

    @When("I set HTTP GET request RESTful API with dept with {int} id URI")
    public void get_rest_api_to_get_emp_info_using_deptid(final int dept_id) {
        ArrayList<Employee> emp_with_depts = new ArrayList<Employee>();
        for (Employee emp : emps) {
            if (emp.getDeptId() == dept_id) {
                emp_with_depts.add(emp);
            }
        }
        when(emprepo.findEmpsByDeptId(dept_id)).thenReturn(emp_with_depts);
    }

    @When("I set HTTP GET request RESTful API with org with {int} id URI")
    public void get_rest_api_to_get_emps_using_orgid(final int org_id) {
        ArrayList<Employee> emp_with_orgs = new ArrayList<Employee>();
        for (Employee emp : emps) {
            if (emp.getOrgId() == org_id) {
                emp_with_orgs.add(emp);
            }
        }
        when(emprepo.findEmpsByOrgId(org_id)).thenReturn(emp_with_orgs);
    }

    @When("I set HTTP GET request without any resource mentioned in API")
    public String set_rest_api_without_resource() {
        return empRestController.get();
    }

    @When("I set HTTP POST request RESTful API with resource URI")
    public void post_rest_api_to_save_empl_using_id() {
        Employee emp = emp1;
        if (emp.getId() >= 3L) {
            when(emprepo.findById(emp.getId())).thenReturn(Optional.empty());
        } else {
            when(emprepo.findById(emp.getId())).thenReturn(Optional.of(emps.get(emp.getId().intValue())));
            when(emprepo.save(emp)).thenReturn(emp);
        }
    }

    @When("I set HTTP POST request RESTful API with resource URI - all at once")
    public void post_rest_api_to_save_emps() {
        when(emprepo.saveAll(emps)).thenReturn(emps);
    }

    @Then("I should get {int} response code of HTTP GET request")
    public void get_http_response_code_of_http_get_request(Integer http_code){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Employee> responseEntity = empRestController.getEmpById(this.empid);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(http_code);
    }
    
    @Then("I should get valid HTTP response code 200 of POST request to add one employee at a time")
    public void testAddEmp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Object> responseEntity = empRestController.addEmp(emp1);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/1");
    }

    @Then("I should get valid HTTP response code 200 of POST request to add all employee at once")
    public void testAddEmps() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Object> responseEntity = empRestController.addEmps(emps);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/all");
    }

    @Then("I should get valid HTTP response code 200 of GET request for all emps")
    public void testGetAllEmps() {
        int empcount = empRestController.getAllEmps().size();
        Assertions.assertEquals(3, empcount);
    }

    @Then("I should get valid HTTP response code 200 of GET request for respective dept")
    public void testFindByDept() {
        int dept_id = 11;
        List<Employee> emps = empRestController.findByDept(dept_id);
        Assertions.assertEquals(1, emps.size());
    }

    @Then("I should get valid HTTP response code 200 of GET request for respective org")
    public void testFindByOrg() {
        int org_id = 2;
        List<Employee> emps = empRestController.findByOrg(org_id);
        Assertions.assertEquals(1, emps.size());
    }

    @Then("I should get valid HTTP response code 200 for that request with warning message")
    public void testGet() {
        String actual = "Please give url as hostname/employee/get";
        Assertions.assertEquals(actual, set_rest_api_without_resource());
    }

    @Then("I should get valid HTTP response code 200 of GET request with all employees information in UI")
    void testShowPrettyOutput() {
        int empcount = empRestController.getAllEmps().size();
        String return_val = empViewController.showPrettyOutput(model);
        Assertions.assertEquals(3, empcount);
        Assertions.assertEquals("show_pretty_output", return_val);

    }

    @Then("I should get valid HTTP response code 200 of GET request with all employees information in UI")
    public void i_should_get_valid_HTTP_response_code_of_GET_request_with_all_employees_information_in_UI() {
        // Write code here that turns the phrase above into concrete actions
        int empcount = empRestController.getAllEmps().size();
        Assertions.assertEquals(3, empcount);
    }

    @Then("I should get valid HTTP response code 200 of DELETE request to delete Employee")
    public void i_should_get_valid_HTTP_response_code_of_DELETE_request_to_delete_Employee() {
        // Write code here that turns the phrase above into concrete actions
        empRestController.deleteEmp(1L);
        ResponseEntity<Object> negResponseEntity = empRestController.deleteEmp(5L);

        // then
        verify(emprepo, times(1)).deleteById(1L);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @When("I initialize all Getters and Setters")
    public void setGetterSetter() {
        log.info("----------Initializing Getters and Settters----------");
    }

    @Then("All Getters and Setters should be initialized")
    public void verifyGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(Employee.class);
        Validator validator = ValidatorBuilder.create().with(new SetterMustExistRule()).with(new GetterMustExistRule())
                .with(new SetterTester()).with(new GetterTester()).build();
        validator.validate(pojoclass);
    }

}
