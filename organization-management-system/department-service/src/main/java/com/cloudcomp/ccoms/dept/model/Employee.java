package com.cloudcomp.ccoms.dept.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private int age;
    private String position;
}
