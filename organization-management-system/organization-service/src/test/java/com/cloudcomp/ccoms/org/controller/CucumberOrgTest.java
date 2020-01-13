package com.cloudcomp.ccoms.org.controller;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = {
        "json:target/cucumber-report" }, monochrome = true)
public class CucumberOrgTest {

}
