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
      * [Backup and Restoration of k8s resources using Heptio Velero](#backup-and-restoration-of-k8s-resources-using-heptio-velero)
   * [Dev-Ops Best Practices](#devops-best-practices)
   * [Objectives](#objectvies)
<!--te-->

Abstract
========
CCOMS is a Organization Managament System Microservices based project. Purpose of creating this project is to understand and work on all Devops technology. Live project will help you to understand or map all devops tools easily this is why i have created this project.

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
    * Recreate - Will terminate all your replica, and recreate a new deployment. Generally we use this in development environemnt
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

**Rollback:** To go back to last working revision / Prevision 
		
As you can see, in below screenshot empty replica set of previous version is present, so why not we delete these replicas [using kubectl delete rs ]
hold on, if we delete these replica, you wont be able to rollback.
		
**You can check rolled out status, using below command**
```linux
[root@mum00aqm ~]# kubectl rollout status -n ccoms deployment cfg-ms
deployment "cfg-ms" successfully rolled out
```
		
**You can check revision history using below command**
```linux
root@mum00aqm ~]# kubectl rollout history -n ccoms deployment cfg-ms
deployment.extensions/cfg-ms
REVISION  CHANGE-CAUSE
1         <none>
2         <none>

```

**You can change image id of deployment using**
```linux
kubectl set image deployment -n ccoms proxy-ms compucomm/gateway-service=compucomm/gateway-service:1.2
```

**See the information of second last revision.**
```linux
[root@mum00aqm ~]# kubectl rollout history -n ccoms deployment proxy-ms --revision=2
deployment.extensions/proxy-ms with revision #2
Pod Template:
  Labels:       app=proxy-ms
        pod-template-hash=6f854c4b44
  Containers:
   proxy-ms:
    Image:      compucomm/gateway-service:1.2
    Port:       8111/TCP
    Host Port:  0/TCP
    Liveness:   http-get http://:8111/emp/pretty delay=120s timeout=1s period=10s #success=1 #failure=3
    Readiness:  http-get http://:8111/emp/pretty delay=120s timeout=1s period=10s #success=1 #failure=3
    Environment:
      app.profile:      dev
      CCOMS_EMP_PORT:   8080
      CCOMS_DEPT_PORT:  8081
      CCOMS_ORG_PORT:   8082
      CCOMS_ZUUL_PORT:  8111
    Mounts:     <none>
  Volumes:      <none>

```

**Its good to set change cause**
We can set a cause using annotate and record option. but that we never use. we update this using yaml manifest file. </br>
Step for creating and maintaining revision history.</br>


**Steps for rollout the last revison.**
kubectl rollout
```
[root@mum00aqm ~]# kubectl  rollout undo deployment -n ccoms
deployment.extensions/cfg-ms
deployment.extensions/dept-ms
deployment.extensions/emp-ms
deployment.extensions/org-ms
deployment.extensions/proxy-ms
```

**If you want go back to specific revision**
```
kubectl  rollout undo deployment -n ccoms cfg-ms --to-revision=2
```
where 2 is the revision id.

**Status information**
```linux
[root@mum00aqm ~]# kubectl rollout status -n ccoms deployment dept-ms
Waiting for deployment "dept-ms" rollout to finish: 2 out of 3 new replicas have been updated...
```

**Stopping and resuming update.**

Assume that you have 100's of microservices and you find that there is some issue in the deployment after applying the rollup, 
then in this case you can pause the rolling update and rollbacked to previous version
```linux 
[root@mum00aqm ~]# kubectl rollout undo deployment -n ccoms
deployment.extensions/cfg-ms
deployment.extensions/dept-ms
deployment.extensions/emp-ms
deployment.extensions/org-ms
deployment.extensions/proxy-ms
[root@mum00aqm ~]# kubectl rollout pause deployment -n ccoms
failed to patch: the server could not find the requested resource
failed to patch: the server could not find the requested resource
failed to patch: the server could not find the requested resource
failed to patch: the server could not find the requested resource
failed to patch: the server could not find the requested resource
```
**You can resume the deployment using resume command**
```linux
[root@mum00aqm ~]# kubectl rollout resume deployment -n ccoms
```

Kubernetes Cluster Deployment
-----------------------------
Kubernetes Cluster Upgrade
--------------------------
Ansible Password Rotation
----------------------------
We do not keep Vault keys as part of code repository, so these keys should be kept as secured location.
since these keys help ansible to decrypt the data from vault. so i have created a utility to generate new keys and copy to home directory 

```linux
[root@mum00cuc ansible_k8s-ccoms-deployment]# sh vault_pwdrotation.sh
Rekey successful
Rekey successful
Rekey successful
```
**Shell script is as below**
```shell
#!/bin/bash


## Uppdate home directory with new keys
rm -rf ~/.ansible_keys; cp -r ./.ansible_keys/  ~/.ansible_keys/

## take backup of existing key files
rm -rf ~/.ansible_keys_back && cp -r ~/.ansible_keys ~/.ansible_keys_back

## Remove existing files
rm -rf ~/.ansible_keys && mkdir -p ~/.ansible_keys

## Generate random passwords
tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.dev
tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.prod
tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.uat

## Rekeying vault ids
ansible-vault rekey --vault-id dev@~/.ansible_keys_back/.vault_ccoms.dev --new-vault-id dev@~/.ansible_keys/.vault_ccoms.dev environments/dev/group_vars/all/vault/ccoms_db

if [ $? -ne 0 ]; then
  exit 1;
fi

ansible-vault rekey --vault-id prod@~/.ansible_keys_back/.vault_ccoms.prod --new-vault-id prod@~/.ansible_keys/.vault_ccoms.prod environments/prod/group_vars/all/vault/ccoms_db

if [ $? -ne 0 ]; then
  exit 1;
fi

ansible-vault rekey --vault-id uat@~/.ansible_keys_back/.vault_ccoms.uat --new-vault-id uat@~/.ansible_keys/.vault_ccoms.uat environments/uat/group_vars/all/vault/ccoms_db

if [ $? -ne 0 ]; then
  exit 1;
fi

## replace the new files
rm -rf ./.ansible_keys && cp -r ~/.ansible_keys ./
rm -rf ~/.ansible_keys_back
```
Application Password rotation
-------------------------------
Autoscalling of Microservices
-----------------------------
Backup and Restoration of k8s resources using Heptio Velero
-------------------------------------------

Dev-Ops Best Practices
======================

Objectives
==========

Everyone/Newbies should able build a project based on Devops technology, When i was moving from Developer to Devops most of the time i was spending in building a infrastructure. for ex. I had taken almost 2-3 weeks only for creating kubernetes cluster.
If the same thing happened with newbies, they might loss there interest in devops field. Thats why i have created this project where everyone shoud take a checkout and build a project in few meenutes and start exploring devops technologies
