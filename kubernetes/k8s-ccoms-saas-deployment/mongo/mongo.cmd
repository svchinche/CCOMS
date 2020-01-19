kubectl apply   -f cluster-role.yaml -f ../persistent_volume/pv.yml -f mongodb-namespace.yaml -f mongodb-configmap.yaml -f mongodb-secret.yaml -f mongodb-stateful-set.yaml
