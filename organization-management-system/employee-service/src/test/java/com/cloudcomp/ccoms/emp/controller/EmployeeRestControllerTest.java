package com.cloudcomp.ccoms.emp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)

public class EmployeeRestControllerTest {

    List<Employee> emps;
    private final Logger log = LoggerFactory.getLogger(EmployeeRestControllerTest.class);

    final Employee emp1 = new Employee(1L, "Suyog", 29, "DevOps Engineer", 21, 2);
    final Employee emp2 = new Employee(5L, "Sanjay", 29, "DevOps Engineer", 21, 2);

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

    private List<Employee> given_emp_dataset() {
        emps = new ArrayList<Employee>();
        Employee emp_a = new Employee(1L, "Suyog", 29, "DevOps Engineer", 21, 2);
        Employee emp_b = new Employee(2L, "Sachin", 29, "DevOps Engineer-2", 11, 1);
        Employee emp_c = new Employee(3L, "Suraj", 29, "DevOps Engineer-2", 13, 1);
        emps.add(emp_a);
        emps.add(emp_b);
        emps.add(emp_c);
        return emps;
    }

    public void get_rest_api_to_get_single_emp_info(final Long empid) {
        emps = given_emp_dataset();
        if (empid >= 3L) {
            when(emprepo.findById(empid)).thenReturn(Optional.empty());
        } else {
            when(emprepo.findById(empid)).thenReturn(Optional.of(emps.get(empid.intValue())));
        }
    }

    public void get_rest_api_to_get_emps_info() {
        emps = given_emp_dataset();
        when(emprepo.findAll()).thenReturn(emps);
    }

    public void get_rest_api_to_get_emp_info_using_deptid(final int dept_id) {
        emps = given_emp_dataset();
        ArrayList<Employee> emp_with_depts = new ArrayList<Employee>();
        for (Employee emp : emps) {
            if (emp.getDeptId() == dept_id) {
                emp_with_depts.add(emp);
            }
        }
        when(emprepo.findEmpsByDeptId(dept_id)).thenReturn(emp_with_depts);
    }

    public void get_rest_api_to_get_emps_using_orgid(final int org_id) {
        emps = given_emp_dataset();
        ArrayList<Employee> emp_with_orgs = new ArrayList<Employee>();
        for (Employee emp : emps) {
            if (emp.getOrgId() == org_id) {
                emp_with_orgs.add(emp);
            }
        }
        when(emprepo.findEmpsByOrgId(org_id)).thenReturn(emp_with_orgs);
    }

    public void post_rest_api_to_save_empl_using_id(final Employee emp) {
        emps = given_emp_dataset();
        if (emp.getId() >= 3L) {
            when(emprepo.findById(emp.getId())).thenReturn(Optional.empty());
        } else {
            when(emprepo.findById(emp.getId())).thenReturn(Optional.of(emps.get(emp.getId().intValue())));
            when(emprepo.save(emp)).thenReturn(emp);
        }
    }

    public void post_rest_api_to_save_emps() {
        when(emprepo.saveAll(emps)).thenReturn(emps);
    }

    @Test
    public void testGetEmpById() throws ResourceNotFoundException {

        // Given
        given_emp_dataset();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        get_rest_api_to_get_single_emp_info(1L);
        get_rest_api_to_get_single_emp_info(4L);
        ResponseEntity<Employee> responseEntity = empRestController.getEmpById(1L);
        ResponseEntity<Employee> negResponseEntity = empRestController.getEmpById(4L);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testAddEmp() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        post_rest_api_to_save_empl_using_id(emp1);
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
        post_rest_api_to_save_emps();
        ResponseEntity<Object> responseEntity = empRestController.addEmps(emps);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/api/all");
    }

    @Test
    public void testGetAllEmps() {

        // Given -- Setup eg. initializing or preparing
        get_rest_api_to_get_emps_info();
        // when
        int empcount = empRestController.getAllEmps().size();
        // then
        Assertions.assertEquals(3, empcount);
    }

    @Test
    public void testUpdateEmp() throws ResourceNotFoundException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        // Given -- Setup eg. initializing or preparing

        post_rest_api_to_save_empl_using_id(emp1);
        post_rest_api_to_save_empl_using_id(emp2);

        // when
        ResponseEntity<Object> negResponseEntity = empRestController.updateEmp(emp2);
        ResponseEntity<Object> responseEntity = empRestController.updateEmp(emp1);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    public void testDeleteEmp() throws ResourceNotFoundException {

        // Given

        Employee emp1 = new Employee(1L, "Suyog", 29, "DevOps Engineer", 21, 2);
        Employee emp2 = new Employee(5L, "Sanjay", 29, "DevOps Engineer", 21, 2);
        post_rest_api_to_save_empl_using_id(emp1);
        post_rest_api_to_save_empl_using_id(emp2);

        // when
        empRestController.deleteEmp(1L);
        ResponseEntity<Object> negResponseEntity = empRestController.deleteEmp(5L);

        // then
        verify(emprepo, times(1)).deleteById(1L);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testUpdateEmpPartially() throws ResourceNotFoundException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        post_rest_api_to_save_empl_using_id(emp1);
        post_rest_api_to_save_empl_using_id(emp2);

        // when
        ResponseEntity<Object> responseEntity = empRestController.updateEmpPartially(1L, emp1);
        ResponseEntity<Object> negResponseEntity = empRestController.updateEmpPartially(5L, emp2);

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(negResponseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testFindByDept() {
        int dept_id = 11;

        get_rest_api_to_get_emp_info_using_deptid(dept_id);
        // when
        List<Employee> emps = empRestController.findByDept(dept_id);

        // then
        Assertions.assertEquals(1, emps.size());
    }

    @Test
    public void testFindByOrg() {
        int org_id = 2;

        // Given -- Setup eg. initializing or preparing
        get_rest_api_to_get_emps_using_orgid(org_id);
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
    void testShowPrettyOutput() {

        // Given -- Setup eg. initializing or preparing
        get_rest_api_to_get_emps_info();
        // when
        int empcount = empRestController.getAllEmps().size();

        String return_val = empViewController.showPrettyOutput(model);

        // then
        Assertions.assertEquals(3, empcount);
        Assertions.assertEquals("show_pretty_output", return_val);

    }

    @Test
    public void testGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(Employee.class);
        Validator validator = ValidatorBuilder.create().with(new SetterMustExistRule()).with(new GetterMustExistRule())
                .with(new SetterTester()).with(new GetterTester()).build();
        validator.validate(pojoclass);
    }

}
