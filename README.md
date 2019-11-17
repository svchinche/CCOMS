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
   * [Data flow Diagrams](#data-flow-diagram)
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
      * [Autoscalling of Microservices](#autoscalling-microservices)
      * [Backup and Restoration using Heptio Velero](#backup-and-restoration-using-heptio-velero)
   * [Best Dev-Ops Practices](#best-devops-practices)
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
<p align="center"><img width="460" height="300" src=".images/dockertagging.PNG"></p>

System Requirements
===================

Existing System
===============

Demerits of Existing System
===========================

Data flow Diagrams
==================
<p align="center"><img width="460" height="300" src=".images/weave.PNG"></p>

Proposed System
===============

Advantages
=========

How Automation Works
====================

Operational Activities
======================
	  
Application Deployment on k8s
-----------------------------
Below command is used to deploy application.

```
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
Kubernetes Cluster Deployemnt
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

Best Dev-Ops Practices
======================
