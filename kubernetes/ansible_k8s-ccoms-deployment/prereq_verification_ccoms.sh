#!/bin/bash -x

#"**********  Below are the pre-requisites ****************"
# 1. All nodes mentioned in inventory should be reachable"
# 2. Vault key id should be rotable"
# 3. After Git Checkout soft links should work properly"
#*********************************************************"

DIR_NAME=$(dirname $(realpath $0))

envs=(prod uat dev)
VAULT_SCRIPT="${DIR_NAME}/scripts/pre_ccoms/vault_pwdrotation.sh"

export ANSIBLE_CONFIG="${DIR_NAME}/ansible.cfg"

echo -e "Verifying ------ All nodes mentioned in inventory should be reachable \n"
### We update host information here ---  environments/uat/hosts 

for env in ${envs[@]}
do
   ansible -m ping -i ${DIR_NAME}/environments/$env all | grep -q "SUCCESS"
   exit_val=$?
   [[ $exit_val -ne 0 ]] && ( echo "some of the nodes are not reachable" )
done

if [[ $exit_val -eq 0 ]]; 
then
   echo "all nodes are reachable";
   exit 0
fi


for env in ${envs[@]}
do
   [[ -L ${DIR_NAME}/environments/$env/group_vars/all/000_cross_env_vars ]] || ( echo "Link mismatch found" ; exit 1; )
   echo "Succesfully verified $env soft link";
done



export ANSIBLE_CONFIG="${DIR_NAME}/ansible.cfg"


## Uppdate home directory with new keys
rm -rf ~/.ansible_keys; cp -r ${DIR_NAME}/.ansible_keys/  ~/.ansible_keys/

## take backup of existing key files
rm -rf ~/.ansible_keys_back && cp -r ~/.ansible_keys ~/.ansible_keys_back

## Remove existing files
rm -rf ~/.ansible_keys && mkdir -p ~/.ansible_keys

## Generate random passwords
tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.dev
tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.prod
tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1 > ~/.ansible_keys/.vault_ccoms.uat

## Rekeying vault ids
ansible-vault rekey --vault-id dev@~/.ansible_keys_back/.vault_ccoms.dev --new-vault-id dev@~/.ansible_keys/.vault_ccoms.dev ${DIR_NAME}/environments/dev/group_vars/all/vault/ccoms_db

if [ $? -ne 0 ]; then
  exit 1;
fi

ansible-vault rekey --vault-id prod@~/.ansible_keys_back/.vault_ccoms.prod --new-vault-id prod@~/.ansible_keys/.vault_ccoms.prod ${DIR_NAME}/environments/prod/group_vars/all/vault/ccoms_db

if [ $? -ne 0 ]; then
  exit 1;
fi

ansible-vault rekey --vault-id uat@~/.ansible_keys_back/.vault_ccoms.uat --new-vault-id uat@~/.ansible_keys/.vault_ccoms.uat ${DIR_NAME}/environments/uat/group_vars/all/vault/ccoms_db

if [ $? -ne 0 ]; then
  exit 1;
fi

## replace the new files
rm -rf ./.ansible_keys && cp -r ~/.ansible_keys ${DIR_NAME}/
rm -rf ~/.ansible_keys_back

exit 0;

