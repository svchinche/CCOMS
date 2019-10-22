
1. For dns related changes, edit below config map

kubectl edit cm coredns -n kube-system

rewrite name cfg.ccoms.com cfg.ccoms.svc.cluster.local
rewrite name dept.ccoms.com dept.ccoms.svc.cluster.local
rewrite name org.ccoms.com org.ccoms.svc.cluster.local
rewrite name emp.ccoms.com emp.ccoms.svc.cluster.local
rewrite name proxy.ccoms.com proxy.ccoms.svc.cluster.local
rewrite name db.mongo.com db.mongo.svc.cluster.local

2. delete the namespaces.

kubectl delete  namespaces mongo ccoms

3. get the information of application and db

 kubectl get po,svc,deploy -n mongo ccoms

4. Create deployment using below command

kubectl apply -f cfg-dep-svc.yaml -f emp-dep-svc.yaml -f dept-dep-svc.yaml -f org-dep-svc.yaml -f proxy-dep-svc.yaml 
