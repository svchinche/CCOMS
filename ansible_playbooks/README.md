# Installing ansible on linux system

```
yum search epel-release
yum install epel-release
yum install ansible
```

# Default inventory location, we can specify our own inventory using -i option
[root@mum00aqm ~]# cat /etc/ansible/hosts | tail -n 2
[k8s-master]
mum00aqm

# run comman on nodes using shell module
```
[root@mum00aqm ~]# ansible -m shell -a date all
mum00aqm | CHANGED | rc=0 >>
Wed Oct 30 04:52:20 PDT 2019
```
or you can run ansible -m shell -a date k8s-master


# Creating a role in ansible

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

# Steps for installing kubernetes python client to use it as a module in Ansible.

yum search python-setuptools

# installl kubenetes module in python 

```
pip install kubernetes
pip install --upgrade openshift
```
pip show module_name to verify the installation . pip show kubernetes </br>


# Run roles in ansible
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
