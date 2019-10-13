package com.cloudcomp.ccoms.deptsvc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Document(collection = "department")
@JsonInclude(value = Include.NON_EMPTY)
public class Department {

    @Id
    @Indexed
    private Long id;
    private String name;
    private int orgId;
    private List<Employee> emps = new ArrayList<>();

    


    public Department() {
        // TODO Auto-generated constructor stub
    }

    public Department(Long deptId , int orgId , String name) {        
        super();
        this.id=deptId;
        this.orgId=orgId;
        this.name = name;
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


    public int getOrgId() {
        return orgId;
    }


    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }


    public List<Employee> getEmps() {
        return emps;
    }


    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    } 

}
