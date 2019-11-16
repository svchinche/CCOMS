#!/bin/bash

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
