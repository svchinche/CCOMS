Feature: Testing an Organization Management Service REST API's - CRUD Operations
   Users should be able to submit GET,POST,DELETE,PUT requests to a web service

Background: 
Given Following Organization information is available in store
  | id  | orgId| name   |
  |  11 | 1    | ORMB   | 
  |  12 | 1    | I-Flex | 

Scenario: Get all Organizations information with employees and departments
When I set HTTP GET request RESTful API with getall URI
Then I should get valid HTTP response code 200 of GET request for all emps depts and orgs

Scenario: Get all Organizations information with Pretty
When I set HTTP GET request RESTful API with pretty URI
Then I should get valid HTTP response code 200 of GET request with all Organizations information pretty UI 

Scenario: Get Organization information using org-id
When I set HTTP GET request RESTful API with URI 1
Then I should get 200 response code of HTTP GET request using orgid
    
Scenario: Get Employee with Department information using org-id
When I set HTTP GET request RESTful API with org with 1 id URI to get department info
Then I should get valid HTTP response code 200 of GET request to get department info using org

Scenario: Get Employees information using org-id
When I set HTTP GET request RESTful API with org with 11 id URI to get employee information
Then I should get valid HTTP response code 200 of GET request to get employee info using org

Scenario: Add all Organizations information at once
When I set HTTP POST request RESTful API with resource URI - all at once
Then I should get valid HTTP response code 200 of POST request to add all Organization at once

Scenario: Error message should shown when no api input is provided
When I set HTTP GET request without any resource mentioned in API
Then I should get valid HTTP response code 200 for that request with warning message

Scenario: All Geters and Setters should be invoked to improve code quality
When I initialize all Getters and Setters
Then All Getters and Setters should be initialized 
