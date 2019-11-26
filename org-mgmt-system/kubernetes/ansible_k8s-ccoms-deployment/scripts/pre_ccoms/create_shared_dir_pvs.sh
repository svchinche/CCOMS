#!/bin/bash

###
SHARED_DIR="/u02/pvs"
DIRS=(pv0,pv1,pv2,pv3,pv4)
NFS_CONF_DIR='/etc/exports'

### Creating shared directories if not present
[[ ! -d $SHARED_DIR ]] && mkdir -p /u02/pvs
[[ ! -d $SHARED_DIR/pv0 ]] && ( mkdir -p $SHARED_DIR/{pv0,pv1,pv2,pv3,pv4} && chmod -R 777 $SHARED_DIR/ )


### Add entry in etc export file and restart if entry added
grep 'pvs' $NFS_CONF_DIR >/dev/null || ( sed -i '$ a\/u02/pvs *(rw,sync,no_root_squash,nohide)' $NFS_CONF_DIR && ( systemctl restart nfs && exportfs ) )

#replace hostip in k8s manifest file

