package com.cloudcomp.ccoms.deptsvc.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;


@Document(collection = "department")
@JsonInclude(value = Include.NON_EMPTY)



public class Department {

    @Id
    private BigInteger deptId;
    private String deptName;
    private int orgId;
    private List<Employee> emps = new ArrayList<>();

    public Department() {
    }

    
    public Department(BigInteger deptId , int orgId , String deptName) {        
        super();
        this.deptId=deptId;
        this.orgId=orgId;
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public BigInteger getDeptId() {
        return deptId;
    }

    public void setDeptId(BigInteger deptId) {
        this.deptId = deptId;
    }


    public List<Employee> getEmps() {
        return emps;
    }


    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }


}
