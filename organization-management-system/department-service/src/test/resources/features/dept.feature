Feature: Testing an Department Management Service REST API's - CRUD Operations
   Users should be able to submit GET,POST,DELETE,PUT requests to a web service

Background: 
Given Following Department information is available in store
  | id  | orgId| name   |
  |  11 | 1    | ORMB   | 
  |  12 | 1    | I-Flex | 

Scenario: Get all Departments information
When I set HTTP GET request RESTful API with depts URI
Then I should get valid HTTP response code 200 of GET request with all Departments information in UI 

Scenario: Get Department information using dept-id
When I set HTTP GET request RESTful API with URI dept 11
Then I should get 200 response code of HTTP GET request for depatrtment

Scenario: Get Department information using Org-id
When I set HTTP GET request RESTful API with org with 2 id URI
Then I should get valid HTTP response code 200 of GET request department for respective org

Scenario: Get Employee with department information using org-id
When I set HTTP GET request RESTful API with org 11 withemp URI to get emp and dept info
Then I should get valid HTTP response code 200 of GET request to get emp and dept info using org

Scenario: Get all Departments with employees and organizations
When I set HTTP GET request RESTful API with org/withemp URI
Then I should get valid HTTP response code 200 of GET request with all Depts information with emp and orgs

Scenario: Add one Department information at a time
When I set HTTP POST request RESTful API with resource URI
Then I should get valid HTTP response code 200 of POST request to add one Department at a time

Scenario: Add all Departments information at once
When I set HTTP POST request RESTful API with resource URI - all at once
Then I should get valid HTTP response code 200 of POST request to add all Department at once

Scenario: Delete all departments information at once
When I set HTTP DELETE REST API 
Then I should get valid HTTP response code 200 of DELETE request to delete all Department at once

Scenario: All Geters and Setters should be invoked to improve code quality
When I initialize all Getters and Setters
Then All Getters and Setters should be initialized 
