Learning Ansible 
================


Table of contents
=================

<!--ts-->
   * [Installing Ansible](#installing-ansible)
   * [Inventory](#inventory)
   * [Modules](#modules)
      * [Shell module](#shell-module)
   * [Roles](#roles)
      * [Creating a roles](#creating-a-roles)
      * [Run roles](#run-roles)
   * [Kubernetes python client](#kubernetes-python-client)
   * [Multistage environment variables](#multistage-environment-variables)
<!--te-->

Installing Ansible
==================================

- Install ansible using yum
```
yum search epel-release
yum install epel-release
yum install ansible
```

Inventory
=================================================================================================
Default iventory location is /etc/ansible/hosts, we can specify our own inventory using -i option
```
[root@mum00aqm ~]# cat /etc/ansible/hosts | tail -n 2
[k8s-master]
mum00aqm
```

Modules
=======
Shell module
------------
Run command on nodes using shell module
```
[root@mum00aqm ~]# ansible -m shell -a date all
mum00aqm | CHANGED | rc=0 >>
Wed Oct 30 04:52:20 PDT 2019
```
or you can run ansible -m shell -a date k8s-master


Roles
=====
Creating a roles
----------------

ansible-galaxy init ccoms
```
[root@mum00aqm ansible_first_role]# tree -d
.
└── ccoms
    ├── defaults
    ├── files
    ├── handlers
    ├── meta
    ├── tasks
    ├── templates
    ├── tests
    └── vars

9 directories
```

Run roles
---------
ansible-playbook ccoms_role.yaml </br>
where role yaml file contains roles which you want to run
```
---
- name: This is CCOMS role
  hosts: all
  user: root
  roles:
  - ccoms

```

Kubernetes python client
========================

Install python-setuptools package using yum

yum search python-setuptools

installl kubenetes module in python 

```
pip install kubernetes
pip install --upgrade openshift
```
pip show module_name to verify the installation . pip show kubernetes </br>



Multistage environment variables
================================
- Create below directory structure for multistage environment variables in ansible.
```
mkdir -p environments/{dev,prod,uat}/{group_vars/{all/env_specific,db,web},hosts}
```
- Sharing variables within environments

Create file</br> 
touch 000_cross_env_vars </br> 

Go to all directory of each environment and create soft link for sharing all environments variables within all environments. </br>
```
cd environments/dev/group_vars/all/
ln -s ../../../000_cross_env_vars .
```

Final directory structure will be look like below
```
[root@mum00aqm ansible_first_role]# tree environments/
environments/
├── 000_cross_env_vars
├── ansible.cfg
├── dev
│   ├── group_vars
│   │   ├── all
│   │   │   ├── 000_cross_env_vars -> ../../../000_cross_env_vars
│   │   │   └── env_specific
│   │   ├── db
│   │   └── web
│   └── hosts
├── prod
│   ├── group_vars
│   │   ├── all
│   │   │   ├── 000_cross_env_vars -> ../../../000_cross_env_vars
│   │   │   └── env_specific
│   │   ├── db
│   │   └── web
│   └── hosts
└── uat
    ├── group_vars
    │   ├── all
    │   │   ├── 000_cross_env_vars -> ../../../000_cross_env_vars
    │   │   └── env_specific
    │   ├── db
    │   └── web
    └── hosts

21 directories, 5 files
```

- Its recommended that your development env as the default directory

```
[root@mum00aqm environments]# cat ansible.cfg
[defaults]
inventory = ./environments/dev
```

- Run ansible plays using 

ansible -i environments/dev -m ping // no need to provide since its default</br>
ansible -m ping
