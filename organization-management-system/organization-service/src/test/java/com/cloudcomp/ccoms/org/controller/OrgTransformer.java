package com.cloudcomp.ccoms.org.controller;

import java.util.Locale;
import java.util.Map;

import com.cloudcomp.ccoms.org.model.Organization;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

public class OrgTransformer implements TypeRegistryConfigurer {
    public Locale locale() {
        return Locale.ENGLISH;
    }

    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(new DataTableType(Organization.class, (Map<String, String> row) -> {
            Long id = Long.parseLong(row.get("orgId"));
            String name = row.get("name");
            String address = row.get("address");
            return new Organization(id, name, address);
        }));
    }
}
