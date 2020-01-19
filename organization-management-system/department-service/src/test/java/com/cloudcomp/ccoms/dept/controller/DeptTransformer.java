package com.cloudcomp.ccoms.dept.controller;

import java.util.Locale;
import java.util.Map;

import com.cloudcomp.ccoms.dept.model.Department;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

public class DeptTransformer implements TypeRegistryConfigurer {
    public Locale locale() {
        return Locale.ENGLISH;
    }

    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(new DataTableType(Department.class,
                        (Map<String, String> row) -> {
                            Long id =  Long.parseLong(row.get("id"));
                            String name = row.get("name");
                            Integer orgId = Integer.parseInt(row.get("orgId"));
                            return new Department(id,orgId,name);
                        }
                )
        );
    }

}
