#Cloud Comp Organization Management System.
Topics to cover.
- Why microservices ?
- How, my journey started with microservices ?
- Components involved ?
- How project is built ?
- How deployment is done on kubernetes ?



#Monolythic Application ::
- All the code resides in one big app and separate database is used to store database. 
- at the end of the day all one big program does your work
- Easy when your team and project is small, what if your project grows.

#Monolythic Application ::
- Microservices are Awesomesome
- Agility
- speed

Disadvantage:: 
- Microservice make terribe to analyze the issue 

single command is used for compiling, building , unit testing, building and pushing image on docker hub.
```mvn clean install```
![Alt text](.info/images/mvn-allinone.PNG?raw=true "Json-op")
 

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



# Proxy setting at Oracle

jvm argument

-Dhttp.proxyHost=www-proxy.us.oracle.com -Dhttp.proxyPort=80 -Dhttp.nonProxyHosts="localhost|127.0.0.1"