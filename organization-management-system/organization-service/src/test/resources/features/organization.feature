Feature: Testing an Organization Management Service REST API's - CRUD Operations
   Users should be able to submit GET,POST,DELETE,PUT requests to a web service

Background: 
Given Following Organization information is available in store
  | orgId  | name   | address  |
  |  1     | Oracle | Pune     | 
  |  2     | NEC    | Noida    | 

Scenario: Get all Organizations information with employees and departments
When I set HTTP GET request RESTful API with getall URI
Then I should get valid HTTP response code 200 of GET request for all emps depts and orgs

Scenario: Get Organization information using org-id
When I set HTTP GET request RESTful API with URI 1
Then I should get 200 response code of HTTP GET request using orgid

Scenario: Get Employees information using org-id
When I set HTTP GET request RESTful API with org with 11 id URI to get employee information
Then I should get valid HTTP response code 200 of GET request to get employee info using org

Scenario: Add all Organizations information at once
When I set HTTP POST request RESTful API with resource URI - all at once
Then I should get valid HTTP response code 200 of POST request to add all Organization at once

Scenario: All Geters and Setters should be invoked to improve code quality
When I initialize all Getters and Setters
Then All Getters and Setters should be initialized 
