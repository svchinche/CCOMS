package com.cloudcomp.ccoms.org.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class Employee {

    private int id;
    private String name;
    private int age;
    private String position;
    
}
