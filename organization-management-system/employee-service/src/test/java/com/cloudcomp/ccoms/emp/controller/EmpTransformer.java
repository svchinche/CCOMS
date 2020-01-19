package com.cloudcomp.ccoms.emp.controller;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

import java.util.Locale;
import java.util.Map;

import com.cloudcomp.ccoms.emp.model.Employee;

public class EmpTransformer implements TypeRegistryConfigurer {
    public Locale locale() {
        return Locale.ENGLISH;
    }

    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(new DataTableType(Employee.class,
                        (Map<String, String> row) -> {
                            Long id =  Long.parseLong(row.get("id"));
                            String name = row.get("name");
                            String position = row.get("position");
                            Integer age = Integer.parseInt(row.get("age"));
                            Integer deptId = Integer.parseInt(row.get("deptId"));
                            Integer orgId = Integer.parseInt(row.get("orgId"));
                            return new Employee(id,name,age,position,deptId,orgId);
                        }
                )
        );
    }
}
    


