# Cloud Comp Organization Management System [CCOMS]


<p align="center">
  <img width="460" height="300" src=".images/logo.PNG">
</p>

Table of contents
=================

<!--ts-->
   * [Abstract](#abstract)
   * [Introdution](#introduction)
   * [System Requirements](#system-requirements)
   * [Existing System](#existing-system)
      * [Monolithic Architecture](#monolithic-architecture)
   * [Demerits of Existing System](#demerits-of-existing-system)
   * [Data flow Diagrams](#data-flow-diagrams)
   * [Proposed System](#proposed-system)
   * [Advantages](#advantages)
   * [How automation works](#how-automation-works)
   * [Operational Activities](#operational-activities)
      * [Application Deployment on k8s](#application-deployment-on-k8s)
      * [Application Rolling Update on k8s](#application-rolling-update-on-k8s)
      * [Rolling back Application on k8s](#rolling-back-application-on-k8s)
      * [Kubernetes Cluster Deployemnt](#kubernetes-cluster-deployment)  
      * [Kubernetes Cluster Upgrade](#kubernetes-cluster-upgrade)
      * [Ansible Password Rotation](#ansible-password-rotation)
      * [Application Password Rotation](#application-password-rotation)
      * [Autoscalling of Microservices](#autoscalling-of-microservices)
      * [Backup and Restoration using Heptio Velero](#backup-and-restoration-using-heptio-velero)
   * [Dev-Ops Best Practices](#devops-best-practices)
<!--te-->

Abstract
========

Introduction
============

<p align="center"><img width="460" height="300" src=".images/empsvc.PNG"></p>
<p align="center"><img width="460" height="300" src=".images/deptsvc.PNG"></p>
<p align="center"><img width="460" height="300" src=".images/orgsvc.PNG"></p>
<p align="center"><img width="460" height="300" src=".images/swagger.PNG"></p>
<p align="center"><img width="460" height="300" src=".images/postman.PNG"></p>
<p align="center"><img width="460" height="300" src=".images/dockerhubimages.PNG"></p>

System Requirements
===================

Existing System
===============

Demerits of Existing System
===========================

Data Flow Diagrams
==================
<p align="center"><img width="460" height="300" src=".images/weave.PNG"></p>

Proposed System
===============

Advantages
=========

How Automation Works
====================

CCOMS is spring boot based application and built using maven

Clone github repository [https://github.com/svchinche/CCOMS.git] and then go to CCOMS/org-mgmt-system directory and run below command.
```linux
[root@mum00aqm org-mgmt-system]# mvn -Drevision=1.3 -DskipTests=true  clean:clean install
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO]
[INFO] cloud-comp-oms                                                     [pom]
[INFO] config-service                                                     [war]
[INFO] dept-service                                                       [war]
[INFO] emp-service                                                        [war]
[INFO] org-service                                                        [war]
[INFO] gateway-service                                                    [war]
[INFO]
[INFO] -----------------< com.cloudcomp.ccoms:cloud-comp-oms >-----------------
[INFO] Building cloud-comp-oms 1.3                                        [1/6]
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-cli) @ cloud-comp-oms ---
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ cloud-comp-oms ---
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/pom.xml to /root/.m2/repository/com/cloudcomp/ccoms/cloud-comp-oms/1.3/cloud-comp-oms-1.3.pom
[INFO]
[INFO] -----------------< com.cloudcomp.ccoms:config-service >-----------------
[INFO] Building config-service 1.3                                        [2/6]
[INFO] --------------------------------[ war ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-cli) @ config-service ---
[INFO] Deleting /root/CCOMS/CCOMS/org-mgmt-system/config-service/target
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ config-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ config-service ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /root/CCOMS/CCOMS/org-mgmt-system/config-service/target/classes
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ config-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /root/CCOMS/CCOMS/org-mgmt-system/config-service/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ config-service ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /root/CCOMS/CCOMS/org-mgmt-system/config-service/target/test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ config-service ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-war-plugin:3.2.3:war (default-war) @ config-service ---
[INFO] Packaging webapp
[INFO] Assembling webapp [config-service] in [/root/CCOMS/CCOMS/org-mgmt-system/config-service/target/ConfigServer-MicroService]
[INFO] Processing war project
[INFO] Webapp assembled in [87 msecs]
[INFO] Building war: /root/CCOMS/CCOMS/org-mgmt-system/config-service/target/ConfigServer-MicroService.war
[INFO]
[INFO] --- spring-boot-maven-plugin:2.2.1.RELEASE:repackage (repackage) @ config-service ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] --- maven-dependency-plugin:3.1.1:unpack (unpack) @ config-service ---
[INFO] Skipping plugin execution
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ config-service ---
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/config-service/target/ConfigServer-MicroService.war to /root/.m2/repository/com/cloudcomp/ccoms/config-service/1.3/config-service-1.3.war
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/config-service/pom.xml to /root/.m2/repository/com/cloudcomp/ccoms/config-service/1.3/config-service-1.3.pom
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:build (default) @ config-service ---
[INFO] dockerfile: null
[INFO] contextDirectory: /root/CCOMS/CCOMS/org-mgmt-system/config-service
[INFO] Building Docker context /root/CCOMS/CCOMS/org-mgmt-system/config-service
[INFO] Path(dockerfile): null
[INFO] Path(contextDirectory): /root/CCOMS/CCOMS/org-mgmt-system/config-service
[INFO]
[INFO] Image will be built as compucomm/config-service:latest
[INFO]
[INFO] Step 1/6 : FROM openjdk:8-jdk-alpine
[INFO]
[INFO] Trying to pull repository docker.io/library/openjdk ...
[INFO] Pulling from docker.io/library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/6 : VOLUME /tmp
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 8fc42099a3a7
[INFO] Step 3/6 : ENV APP_FILE ConfigServer-MicroService.war
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 8df64ab20889
[INFO] Step 4/6 : EXPOSE 8888
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 653682fcc489
[INFO] Step 5/6 : ADD target/${APP_FILE} ${APP_FILE}
[INFO]
[INFO]  ---> 8f5a8ba3b3b5
[INFO] Step 6/6 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ConfigServer-MicroService.war"]
[INFO]
[INFO]  ---> Running in aa40840ae2e2
[INFO] Removing intermediate container aa40840ae2e2
[INFO]  ---> de44e39ded7f
[INFO] Successfully built de44e39ded7f
[INFO] Successfully tagged compucomm/config-service:latest
[INFO]
[INFO] Detected build of image with id de44e39ded7f
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/config-service/target/ConfigServer-MicroService-docker-info.jar
[INFO] Successfully built compucomm/config-service:latest
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (default) @ config-service ---
[INFO] The push refers to repository [docker.io/compucomm/config-service]
[INFO] Image 0f6d4a2f2989: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] Image 0f6d4a2f2989: Pushing
[INFO] Image 0f6d4a2f2989: Pushed
[INFO] latest: digest: sha256:1987270b1da2a534921cfb89f79bedd313462f0ac48b740ccde178fa32819198 size: 1159
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:tag (tag-version) @ config-service ---
[INFO] Tagging image de44e39ded7f as compucomm/config-service:1.3
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/config-service/target/ConfigServer-MicroService-docker-info.jar
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (tag-version) @ config-service ---
[INFO] The push refers to repository [docker.io/compucomm/config-service]
[INFO] Image 0f6d4a2f2989: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image 0f6d4a2f2989: Layer already exists
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] 1.3: digest: sha256:1987270b1da2a534921cfb89f79bedd313462f0ac48b740ccde178fa32819198 size: 1159
[INFO]
[INFO] ------------------< com.cloudcomp.ccoms:dept-service >------------------
[INFO] Building dept-service 1.3                                          [3/6]
[INFO] --------------------------------[ war ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-cli) @ dept-service ---
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ dept-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] Copying 2 resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ dept-service ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 7 source files to /root/CCOMS/CCOMS/org-mgmt-system/department-service/target/classes
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ dept-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /root/CCOMS/CCOMS/org-mgmt-system/department-service/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ dept-service ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ dept-service ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-war-plugin:3.2.3:war (default-war) @ dept-service ---
[INFO] Packaging webapp
[INFO] Assembling webapp [dept-service] in [/root/CCOMS/CCOMS/org-mgmt-system/department-service/target/Department-MicroService]
[INFO] Processing war project
[INFO] Webapp assembled in [93 msecs]
[INFO] Building war: /root/CCOMS/CCOMS/org-mgmt-system/department-service/target/Department-MicroService.war
[INFO]
[INFO] --- spring-boot-maven-plugin:2.2.1.RELEASE:repackage (repackage) @ dept-service ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] --- maven-dependency-plugin:3.1.1:unpack (unpack) @ dept-service ---
[INFO] Skipping plugin execution
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ dept-service ---
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/department-service/target/Department-MicroService.war to /root/.m2/repository/com/cloudcomp/ccoms/dept-service/1.3/dept-service-1.3.war
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/department-service/pom.xml to /root/.m2/repository/com/cloudcomp/ccoms/dept-service/1.3/dept-service-1.3.pom
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:build (default) @ dept-service ---
[INFO] dockerfile: null
[INFO] contextDirectory: /root/CCOMS/CCOMS/org-mgmt-system/department-service
[INFO] Building Docker context /root/CCOMS/CCOMS/org-mgmt-system/department-service
[INFO] Path(dockerfile): null
[INFO] Path(contextDirectory): /root/CCOMS/CCOMS/org-mgmt-system/department-service
[INFO]
[INFO] Image will be built as compucomm/dept-service:latest
[INFO]
[INFO] Step 1/6 : FROM openjdk:8-jdk-alpine
[INFO]
[INFO] Trying to pull repository docker.io/library/openjdk ...
[INFO] Pulling from docker.io/library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/6 : VOLUME /tmp
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 8fc42099a3a7
[INFO] Step 3/6 : ENV APP_FILE Department-MicroService.war
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 3e5ae98a0a21
[INFO] Step 4/6 : EXPOSE 8081
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 1431e1422571
[INFO] Step 5/6 : ADD target/${APP_FILE} ${APP_FILE}
[INFO]
[INFO]  ---> 22eb7d1bc358
[INFO] Step 6/6 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Department-MicroService.war"]
[INFO]
[INFO]  ---> Running in 365f28a43e06
[INFO] Removing intermediate container 365f28a43e06
[INFO]  ---> d11551ff2791
[INFO] Successfully built d11551ff2791
[INFO] Successfully tagged compucomm/dept-service:latest
[INFO]
[INFO] Detected build of image with id d11551ff2791
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/department-service/target/Department-MicroService-docker-info.jar
[INFO] Successfully built compucomm/dept-service:latest
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (default) @ dept-service ---
[INFO] The push refers to repository [docker.io/compucomm/dept-service]
[INFO] Image 8ac56741522a: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] Image 8ac56741522a: Pushing
[INFO] Image 8ac56741522a: Pushed
[INFO] latest: digest: sha256:add4db8335858c8e8937a8ab4c9de56f4534db5f3216cf0fb935213430ec3fe8 size: 1159
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:tag (tag-version) @ dept-service ---
[INFO] Tagging image d11551ff2791 as compucomm/dept-service:1.3
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/department-service/target/Department-MicroService-docker-info.jar
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (tag-version) @ dept-service ---
[INFO] The push refers to repository [docker.io/compucomm/dept-service]
[INFO] Image 8ac56741522a: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image 8ac56741522a: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] 1.3: digest: sha256:add4db8335858c8e8937a8ab4c9de56f4534db5f3216cf0fb935213430ec3fe8 size: 1159
[INFO]
[INFO] ------------------< com.cloudcomp.ccoms:emp-service >-------------------
[INFO] Building emp-service 1.3                                           [4/6]
[INFO] --------------------------------[ war ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-cli) @ emp-service ---
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ emp-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] Copying 2 resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ emp-service ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 6 source files to /root/CCOMS/CCOMS/org-mgmt-system/employee-service/target/classes
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ emp-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /root/CCOMS/CCOMS/org-mgmt-system/employee-service/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ emp-service ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ emp-service ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-war-plugin:3.2.3:war (default-war) @ emp-service ---
[INFO] Packaging webapp
[INFO] Assembling webapp [emp-service] in [/root/CCOMS/CCOMS/org-mgmt-system/employee-service/target/Employee-MicroService]
[INFO] Processing war project
[INFO] Webapp assembled in [91 msecs]
[INFO] Building war: /root/CCOMS/CCOMS/org-mgmt-system/employee-service/target/Employee-MicroService.war
[INFO]
[INFO] --- spring-boot-maven-plugin:2.2.1.RELEASE:repackage (repackage) @ emp-service ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] --- maven-dependency-plugin:3.1.1:unpack (unpack) @ emp-service ---
[INFO] Skipping plugin execution
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ emp-service ---
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/employee-service/target/Employee-MicroService.war to /root/.m2/repository/com/cloudcomp/ccoms/emp-service/1.3/emp-service-1.3.war
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/employee-service/pom.xml to /root/.m2/repository/com/cloudcomp/ccoms/emp-service/1.3/emp-service-1.3.pom
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:build (default) @ emp-service ---
[INFO] dockerfile: null
[INFO] contextDirectory: /root/CCOMS/CCOMS/org-mgmt-system/employee-service
[INFO] Building Docker context /root/CCOMS/CCOMS/org-mgmt-system/employee-service
[INFO] Path(dockerfile): null
[INFO] Path(contextDirectory): /root/CCOMS/CCOMS/org-mgmt-system/employee-service
[INFO]
[INFO] Image will be built as compucomm/emp-service:latest
[INFO]
[INFO] Step 1/6 : FROM openjdk:8-jdk-alpine
[INFO]
[INFO] Trying to pull repository docker.io/library/openjdk ...
[INFO] Pulling from docker.io/library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/6 : VOLUME /tmp
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 8fc42099a3a7
[INFO] Step 3/6 : ENV APP_FILE Employee-MicroService.war
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 67fb06a81eca
[INFO] Step 4/6 : EXPOSE 8080
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 1abed1e8f41a
[INFO] Step 5/6 : ADD target/${APP_FILE} ${APP_FILE}
[INFO]
[INFO]  ---> 116515e5b540
[INFO] Step 6/6 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Employee-MicroService.war"]
[INFO]
[INFO]  ---> Running in 5a940e92af44
[INFO] Removing intermediate container 5a940e92af44
[INFO]  ---> d085df792ec7
[INFO] Successfully built d085df792ec7
[INFO] Successfully tagged compucomm/emp-service:latest
[INFO]
[INFO] Detected build of image with id d085df792ec7
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/employee-service/target/Employee-MicroService-docker-info.jar
[INFO] Successfully built compucomm/emp-service:latest
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (default) @ emp-service ---
[INFO] The push refers to repository [docker.io/compucomm/emp-service]
[INFO] Image 125fa39e84ab: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] Image 125fa39e84ab: Pushing
[INFO] Image 125fa39e84ab: Pushed
[INFO] latest: digest: sha256:4d35e292fb2450c1360436917e6cf4751276b9e2456db73ef489cba44973caf0 size: 1159
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:tag (tag-version) @ emp-service ---
[INFO] Tagging image d085df792ec7 as compucomm/emp-service:1.3
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/employee-service/target/Employee-MicroService-docker-info.jar
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (tag-version) @ emp-service ---
[INFO] The push refers to repository [docker.io/compucomm/emp-service]
[INFO] Image 125fa39e84ab: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image 125fa39e84ab: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] 1.3: digest: sha256:4d35e292fb2450c1360436917e6cf4751276b9e2456db73ef489cba44973caf0 size: 1159
[INFO]
[INFO] ------------------< com.cloudcomp.ccoms:org-service >-------------------
[INFO] Building org-service 1.3                                           [5/6]
[INFO] --------------------------------[ war ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-cli) @ org-service ---
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ org-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] Copying 2 resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ org-service ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 9 source files to /root/CCOMS/CCOMS/org-mgmt-system/organization-service/target/classes
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ org-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /root/CCOMS/CCOMS/org-mgmt-system/organization-service/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ org-service ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ org-service ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-war-plugin:3.2.3:war (default-war) @ org-service ---
[INFO] Packaging webapp
[INFO] Assembling webapp [org-service] in [/root/CCOMS/CCOMS/org-mgmt-system/organization-service/target/Organization-MicroService]
[INFO] Processing war project
[INFO] Webapp assembled in [89 msecs]
[INFO] Building war: /root/CCOMS/CCOMS/org-mgmt-system/organization-service/target/Organization-MicroService.war
[INFO]
[INFO] --- spring-boot-maven-plugin:2.2.1.RELEASE:repackage (repackage) @ org-service ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] --- maven-dependency-plugin:3.1.1:unpack (unpack) @ org-service ---
[INFO] Skipping plugin execution
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ org-service ---
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/organization-service/target/Organization-MicroService.war to /root/.m2/repository/com/cloudcomp/ccoms/org-service/1.3/org-service-1.3.war
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/organization-service/pom.xml to /root/.m2/repository/com/cloudcomp/ccoms/org-service/1.3/org-service-1.3.pom
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:build (default) @ org-service ---
[INFO] dockerfile: null
[INFO] contextDirectory: /root/CCOMS/CCOMS/org-mgmt-system/organization-service
[INFO] Building Docker context /root/CCOMS/CCOMS/org-mgmt-system/organization-service
[INFO] Path(dockerfile): null
[INFO] Path(contextDirectory): /root/CCOMS/CCOMS/org-mgmt-system/organization-service
[INFO]
[INFO] Image will be built as compucomm/org-service:latest
[INFO]
[INFO] Step 1/6 : FROM openjdk:8-jdk-alpine
[INFO]
[INFO] Trying to pull repository docker.io/library/openjdk ...
[INFO] Pulling from docker.io/library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/6 : VOLUME /tmp
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 8fc42099a3a7
[INFO] Step 3/6 : ENV APP_FILE Organization-MicroService.war
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> c3224a5336d5
[INFO] Step 4/6 : EXPOSE 8082
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 23de6018c836
[INFO] Step 5/6 : ADD target/${APP_FILE} ${APP_FILE}
[INFO]
[INFO]  ---> 031e41e7f521
[INFO] Step 6/6 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Organization-MicroService.war"]
[INFO]
[INFO]  ---> Running in 2ddb333e46b9
[INFO] Removing intermediate container 2ddb333e46b9
[INFO]  ---> a223212af762
[INFO] Successfully built a223212af762
[INFO] Successfully tagged compucomm/org-service:latest
[INFO]
[INFO] Detected build of image with id a223212af762
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/organization-service/target/Organization-MicroService-docker-info.jar
[INFO] Successfully built compucomm/org-service:latest
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (default) @ org-service ---
[INFO] The push refers to repository [docker.io/compucomm/org-service]
[INFO] Image 1b1203371c60: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image 1b1203371c60: Pushing
[INFO] Image 1b1203371c60: Pushed
[INFO] latest: digest: sha256:cfaaaa6908a90b6552e01ffc65d400235e7545cad08f4d819b0c81e97bf357e3 size: 1159
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:tag (tag-version) @ org-service ---
[INFO] Tagging image a223212af762 as compucomm/org-service:1.3
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/organization-service/target/Organization-MicroService-docker-info.jar
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (tag-version) @ org-service ---
[INFO] The push refers to repository [docker.io/compucomm/org-service]
[INFO] Image 1b1203371c60: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image 1b1203371c60: Layer already exists
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] 1.3: digest: sha256:cfaaaa6908a90b6552e01ffc65d400235e7545cad08f4d819b0c81e97bf357e3 size: 1159
[INFO]
[INFO] ----------------< com.cloudcomp.ccoms:gateway-service >-----------------
[INFO] Building gateway-service 1.3                                       [6/6]
[INFO] --------------------------------[ war ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-cli) @ gateway-service ---
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ gateway-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ gateway-service ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/target/classes
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ gateway-service ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ gateway-service ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ gateway-service ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-war-plugin:3.2.3:war (default-war) @ gateway-service ---
[INFO] Packaging webapp
[INFO] Assembling webapp [gateway-service] in [/root/CCOMS/CCOMS/org-mgmt-system/gateway-service/target/ProxyServer-MicroService]
[INFO] Processing war project
[INFO] Webapp assembled in [86 msecs]
[INFO] Building war: /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/target/ProxyServer-MicroService.war
[INFO]
[INFO] --- spring-boot-maven-plugin:2.2.1.RELEASE:repackage (repackage) @ gateway-service ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] --- maven-dependency-plugin:3.1.1:unpack (unpack) @ gateway-service ---
[INFO] Skipping plugin execution
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ gateway-service ---
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/target/ProxyServer-MicroService.war to /root/.m2/repository/com/cloudcomp/ccoms/gateway-service/1.3/gateway-service-1.3.war
[INFO] Installing /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/pom.xml to /root/.m2/repository/com/cloudcomp/ccoms/gateway-service/1.3/gateway-service-1.3.pom
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:build (default) @ gateway-service ---
[INFO] dockerfile: null
[INFO] contextDirectory: /root/CCOMS/CCOMS/org-mgmt-system/gateway-service
[INFO] Building Docker context /root/CCOMS/CCOMS/org-mgmt-system/gateway-service
[INFO] Path(dockerfile): null
[INFO] Path(contextDirectory): /root/CCOMS/CCOMS/org-mgmt-system/gateway-service
[INFO]
[INFO] Image will be built as compucomm/gateway-service:latest
[INFO]
[INFO] Step 1/6 : FROM openjdk:8-jdk-alpine
[INFO]
[INFO] Trying to pull repository docker.io/library/openjdk ...
[INFO] Pulling from docker.io/library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/6 : VOLUME /tmp
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> 8fc42099a3a7
[INFO] Step 3/6 : ENV APP_FILE ProxyServer-MicroService.war
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> e838e164d956
[INFO] Step 4/6 : EXPOSE 8111
[INFO]
[INFO]  ---> Using cache
[INFO]  ---> a20e66d97775
[INFO] Step 5/6 : ADD target/${APP_FILE} ${APP_FILE}
[INFO]
[INFO]  ---> 53e6769db489
[INFO] Step 6/6 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ProxyServer-MicroService.war"]
[INFO]
[INFO]  ---> Running in 9934671231dc
[INFO] Removing intermediate container 9934671231dc
[INFO]  ---> 516f4647e94c
[INFO] Successfully built 516f4647e94c
[INFO] Successfully tagged compucomm/gateway-service:latest
[INFO]
[INFO] Detected build of image with id 516f4647e94c
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/target/ProxyServer-MicroService-docker-info.jar
[INFO] Successfully built compucomm/gateway-service:latest
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (default) @ gateway-service ---
[INFO] The push refers to repository [docker.io/compucomm/gateway-service]
[INFO] Image 2bec5235f207: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] Image 2bec5235f207: Pushing
[INFO] Image 2bec5235f207: Pushed
[INFO] latest: digest: sha256:f1bd20ae7321560de57ba62b823d8d55e0189f630d293855bcda0d41d426ff81 size: 1159
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:tag (tag-version) @ gateway-service ---
[INFO] Tagging image 516f4647e94c as compucomm/gateway-service:1.3
[INFO] Building jar: /root/CCOMS/CCOMS/org-mgmt-system/gateway-service/target/ProxyServer-MicroService-docker-info.jar
[INFO]
[INFO] --- dockerfile-maven-plugin:1.4.13:push (tag-version) @ gateway-service ---
[INFO] The push refers to repository [docker.io/compucomm/gateway-service]
[INFO] Image 2bec5235f207: Preparing
[INFO] Image ceaf9e1ebef5: Preparing
[INFO] Image 9b9b7f3d56a0: Preparing
[INFO] Image f1b5933fe4b5: Preparing
[INFO] Image 2bec5235f207: Layer already exists
[INFO] Image 9b9b7f3d56a0: Layer already exists
[INFO] Image ceaf9e1ebef5: Layer already exists
[INFO] Image f1b5933fe4b5: Layer already exists
[INFO] 1.3: digest: sha256:f1bd20ae7321560de57ba62b823d8d55e0189f630d293855bcda0d41d426ff81 size: 1159
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for cloud-comp-oms 1.3:
[INFO]
[INFO] cloud-comp-oms ..................................... SUCCESS [  0.242 s]
[INFO] config-service ..................................... SUCCESS [02:06 min]
[INFO] dept-service ....................................... SUCCESS [02:44 min]
[INFO] emp-service ........................................ SUCCESS [02:42 min]
[INFO] org-service ........................................ SUCCESS [02:42 min]
[INFO] gateway-service .................................... SUCCESS [02:54 min]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  13:11 min
[INFO] Finished at: 2019-11-17T16:55:15-05:00
[INFO] ------------------------------------------------------------------------
```

In command, you can see maven command is being used to create application war file.
As docker spotify plugin is enrolled with install phase, it pushes docker image itself on dockerhub registry.

* I have tagged image with two tags
    * latest
	* with revision, that we passed as argument to maven
	
<p align="center"><img width="460" height="300" src=".images/dockertagging.PNG"></p>

Operational Activities
======================
	  
Application Deployment on k8s
-----------------------------
Below command is being used to install application on kubernetes

```linux
[root@mum00cuc ansible_k8s-ccoms-deployment]# ansible-playbook -e "ccoms_service_tag=1.2" ccoms_playbook.yaml

PLAY [Installing Kubernetes python client for ansible] ***************************************************************************************

TASK [common : Ensure Pip is installed on Proxy Based Env] ***********************************************************************************
skipping: [ansible_node]

TASK [common : Ensure OpenShift client is installed on Proxy Based Env.] *********************************************************************
skipping: [ansible_node]

TASK [common : Ensure Pip is installed] ******************************************************************************************************
skipping: [ansible_node]

TASK [common : Ensure OpenShift client is installed.] ****************************************************************************************
ok: [ansible_node]

PLAY [Cloud Comp OMS Deployment on kunernetes Cluster] ***************************************************************************************

TASK [preccoms : Updating existing Coredns ConfigMap] ****************************************************************************************

ok: [k8s_master]

TASK [database : Create MONGO DB Namespace] **************************************************************************************************
ok: [k8s_master]

TASK [database : Create MONGO DB Service] ****************************************************************************************************
ok: [k8s_master]

TASK [database : Create MONGO DB ConfigMap] **************************************************************************************************
ok: [k8s_master]

TASK [database : Create MONGO DB Secret] *****************************************************************************************************
ok: [k8s_master]

TASK [database : Create MONGO DB Deployment] *************************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Namespace] ********************************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Service for Config] ***********************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Service for Employee] *********************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Service for Departemnt] *******************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Service for Organization] *****************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Service for Proxy] ************************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Secrets] **********************************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Deployment for Config] ********************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Deployment for Employee] ******************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Deployment for Department] ****************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Deployment for Organization] **************************************************************************************
ok: [k8s_master]

TASK [ccoms : Create CCOMS Deployment for Proxy] *********************************************************************************************
ok: [k8s_master]

PLAY RECAP ***********************************************************************************************************************************
ansible_node               : ok=1    changed=0    unreachable=0    failed=0    skipped=3    rescued=0    ignored=0
k8s_master                 : ok=18   changed=0    unreachable=0    failed=0    skipped=0    rescued=0    ignored=0
```

Application Rolling Update on k8s 
---------------------------------

* There are two ways of update
    * Recreate - Will terminate all your replica, and recreate a new deployment. 
    * Rolling - Not updating the instances at once, updating application with stages. We use this for zero downtime to keep application available for customer. </br>
    
Below are the option that we use in rolling update
* MaxSurge : How many replicas in addition of existing replica. If we have four replica and if you mention 50% of surge, total available replica will be six  
* MaxUnavailable : How many pods can be unavailable. If you keep value as 1, it will terminate and update the pod one by one.
* MinimumReadySeconds : We say minimum wait when new/updated pod is ready for serving request, ideal way would be to have ReadynessProbe
* Revision history : It keep revision for deployment, this will help us to restore back using these revision
		
**Implementation** 
I have automated zero downtime patching by updating kubernetes manifest [deployment yaml] file, which is automated through ansible</br>
in below command, i have provided extra argument to update microservices with tag 1.2. and default value was latest</br>
	  
``` 
[root@ansible_node ansible_k8s-ccoms-deployment]# ansible-playbook -e "ccoms_service_tag=1.2" ccoms_playbook.yaml 
[root@mum00cuc ansible_k8s-ccoms-deployment]# grep -inr -F "ccoms_service_tag" *
environments/uat/group_vars/k8s_nodes:6:    IMAGE: config-service:{{ ccoms_service_tag }}
environments/uat/group_vars/k8s_nodes:12:    IMAGE: emp-service:{{ ccoms_service_tag }}
environments/uat/group_vars/k8s_nodes:18:    IMAGE: dept-service:{{ ccoms_service_tag }}
environments/uat/group_vars/k8s_nodes:24:    IMAGE: org-service:{{ ccoms_service_tag }}
environments/uat/group_vars/k8s_nodes:30:    IMAGE: gateway-service:{{ ccoms_service_tag }}
environments/000_cross_env_vars:2:ccoms_service_tag: latest
```

Deployment yaml or Manifest file </br>
   
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: proxy-ms
   namespace: ccoms
   labels:
      app: proxy-ms
spec:
   replicas: 2
   strategy:
      type: RollingUpdate
      rollingUpdate:
         maxSurge: 1
         maxUnavailable: 50%
   selector:
      matchLabels:
         app: proxy-ms
   template:
      metadata:
         labels:
            app: proxy-ms
      spec:
         containers:
         -  name: proxy-ms
            image: compucomm/gateway-service
            imagePullPolicy: Always
            ports:
            -  containerPort: 8111
            readinessProbe:
              httpGet:
                path: /emp/pretty
                port: 8111
              initialDelaySeconds: 120
              periodSeconds: 10
              successThreshold: 1
            livenessProbe:
              httpGet:
                path: /emp/pretty
                port: 8111
              initialDelaySeconds: 120
              periodSeconds: 10
              successThreshold: 1
```





Rolling back Application on k8s
--------------------------------
Kubernetes Cluster Deployment
-----------------------------
Kubernetes Cluster Upgrade
--------------------------
Ansible Password Rotation
----------------------------
Application Password rotation
-------------------------------
Autoscalling of Microservices
-----------------------------
Backup and Restoration using Heptio Velero
-------------------------------------------

Dev-Ops Best Practices
======================
