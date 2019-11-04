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
   * [Vaults](#vaults)
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

ansible -i environments/dev -m ping   ### Noneed to provide since its default</br>
ansible -m ping



Vaults
================

- Vault Operation 
```
ansible-vault --vault-password-file=.vault_ccoms view ccoms_vault
ansible-vault --vault-password-file=.vault_ccoms create ccoms_vault
ansible-vault --vault-password-file=.vault_ccoms edit ccoms_vault
```
Note: 'ansible-vault encrypt' command is use for encrypting file

For password rotation :: </br>

We can reset the ansible vault using rekey [this will encrypt the file with new key] </br>
```
ansible-vault --vault-password-file=.vault_ccoms rekey ccoms_vault
```
- Creating vaults with vault-id


- Create strong password.

```
mkdir -p ~/.ansible_keys

tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.dev
cat ~/.ansible_keys/.vault_ccoms.dev

tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.prod
cat ~/.ansible_keys/.vault_ccoms.prod

tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.uat
cat ~/.ansible_keys/.vault_ccoms.uat

tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.def
cat ~/.ansible_keys/.vault_ccoms.def
```

- Then add below content in ansible.conf
```
[root@mum00aqm ansible_based_k8s-ccoms-saas-deployment]# grep -inr -F -A 2 "vault-id" ansible.cfg
26:# Vault-id identity file
27-vault_identity_list = def@~/.vault_ccoms.def, dev@~/.vault_ccoms.dev, prod@~/.vault_ccoms.prod, uat@~/.vault_ccoms.uat
````
Note:: Storing vault password files in project home directory is not recommended, since this part will be kept on central repository. best way is to keep identity files on user home directory </br>

- Create separate id for individual environment. for ex. for dev env create dev id </br>
This id is used for decrypt the data to ansible </br>

- Prior to ansible 2.4 there was only one password was used accross the environment. in order to handle different password for differ environment we use ansible id.</br>
directory:: environments/dev/group_vars/vault </br>
``` 
ansible-vault create --vault-id dev@~/.ansible_keys/.vault_ccoms.dev  ccoms_db
cd ../../../prod/group_vars/vault/
ansible-vault create --vault-id prod@~/.ansible_keys/.vault_ccoms.prod  ccoms_db
cd ../../../uat/group_vars/vault/
ansible-vault create --vault-id uat@~/.ansible_keys/.vault_ccoms.uat  ccoms_db
```
- Rekeying with new id -- this needs existing vaultid [--vault-id ]credential and updated vault id credentials [--new-vault-id] </br>
```
[root@mum00aqm all]# ansible-vault rekey --vault-id dev@.vault_ccoms --new-vault-id dev@~/.ansible_keys/.vault_ccoms.dev ccoms_db_vault
Rekey successful
```

- View content of file

``` 
[root@mum00aqm vault]# ansible-vault view --vault-id dev@~/.ansible_keys/.vault_ccoms.dev  ccoms_db

---
common:
  username: admin
  password: admin123
  environment: development
  servicename: admin
emp:
  servicename: admin
dept:
  servicename: admin
org:
  servicename: admin
```


- Final directory structure will look like below</br>

```
[root@mum00aqm ansible_based_k8s-ccoms-saas-deployment]# tree  environments ; tree -d  roles/ccoms
environments
├── 000_cross_env_vars
├── dev
│   ├── group_vars
│   │   ├── all
│   │   │   ├── 000_cross_env_vars -> ../../../000_cross_env_vars
│   │   │   ├── env_specific
│   │   │   └── vault
│   │   │       └── ccoms_db
│   │   ├── db
│   │   ├── k8s-mgmt-node
│   │   └── web
│   └── hosts
├── prod
│   ├── group_vars
│   │   ├── all
│   │   │   ├── 000_cross_env_vars -> ../../../000_cross_env_vars
│   │   │   ├── env_specific
│   │   │   └── vault
│   │   │       └── ccoms_db
│   │   ├── db
│   │   ├── k8s-mgmt-node
│   │   └── web
│   └── hosts
└── uat
    ├── group_vars
    │   ├── all
    │   │   ├── 000_cross_env_vars -> ../../../000_cross_env_vars
    │   │   ├── env_specific
    │   │   └── vault
    │   │       └── ccoms_db
    │   ├── db
    │   ├── k8s-mgmt-node
    │   └── web
    └── hosts

12 directories, 22 files
roles/ccoms
├── defaults
├── files
├── handlers
├── meta
├── tasks
├── templates
│   ├── deployment
│   ├── namespace
│   └── service
├── tests
└── vars

11 directories
```
