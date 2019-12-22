package com.cloudcomp.ccoms.org.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_EMPTY)
public class Department {

    private int id;
    private String name;
    private int orgId;
    private List<Employee> emps;

    public Department(int deptId, int orgId, String deptName) {
        super();
        this.id = deptId;
        this.orgId = orgId;
        this.name = deptName;
    }

}
