# Pretty urls output
- http://k8s-master:8111/organization/pretty
- http://k8s-master:8111/employee/pretty
- http://k8s-master:8111/department/pretty


#Get urls
- http://k8s-master:8111/employee/api/get
- http://k8s-master:8111/department/api/depts
- http://k8s-master:8111/organization/api/get

#Swagger Urls::
- http://localhost:8111/swagger-ui.html
- http://k8s-master:8111/employee/v2/api-docs
- http://k8s-master:8111/organization/v2/api-docs
- http://k8s-master:8111/department/v2/api-docs

Note :: For this i have used centralized swagger and spring boot zull proxy server 

#Once application is up you can add employees information using RestAPI  

url is: http://k8s-master:8111/employee/api/addemps

![Alt text](.info/images/adsemps.PNG?raw=true "Postman utility for POST method")

#Json output on browser


![Alt text](.info/images/json_op_pretty.PNG?raw=true "Json-op")