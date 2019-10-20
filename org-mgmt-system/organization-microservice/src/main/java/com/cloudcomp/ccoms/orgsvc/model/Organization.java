package com.cloudcomp.ccoms.orgsvc.model;



import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Document(collection = "organization")
@JsonInclude(value = Include.NON_EMPTY)

public class Organization {

    @Id
    @Indexed
    private Long id;
    private String name;
    private String address;
    private List<Department> depts;
    private Department dept;
    private List<Employee> emps;
    
    
    Organization() {
        
    }
    
    Organization(Long orgId,String name, String address){
        this.id=orgId;
        this.name=name;
        this.address=address;
    }
     
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Department> getDepts() {
        return depts;
    }
    public void setDepts(List<Department> depts) {
        this.depts = depts;
    }
    public List<Employee> getEmps() {
        return emps;
    }
    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
    
    
}
