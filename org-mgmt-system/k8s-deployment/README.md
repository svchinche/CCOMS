# For dns related changes, edit below system config map

kubectl edit cm coredns -n kube-system

```
rewrite name cfg.ccoms.com cfg.ccoms.svc.cluster.local
rewrite name dept.ccoms.com dept.ccoms.svc.cluster.local
rewrite name org.ccoms.com org.ccoms.svc.cluster.local
rewrite name emp.ccoms.com emp.ccoms.svc.cluster.local
rewrite name proxy.ccoms.com proxy.ccoms.svc.cluster.local
rewrite name db.mongo.com db.mongo.svc.cluster.local
```


# Cleanup Steps :: Just delete the namespaces. 

 kubectl delete  namespaces mongo ccoms

```
[root@k8s-master k8s-deployment]# kubectl delete namespaces ccoms
namespace "ccoms" deleted
[root@k8s-master k8s-deployment]# kubectl delete -f ccoms-namespace.yaml
Error from server (NotFound): error when deleting "ccoms-namespace.yaml": namespaces "ccoms" not found

```

# Get information using get command

  kubectl get po,svc,deploy -n mongo -n ccoms -o wide
  
```
[root@k8s-master k8s-files]# kubectl get po,svc,deploy -n mongo -n ccoms -o wide
NAME                            READY   STATUS    RESTARTS   AGE    IP            NODE             NOMINATED NODE
pod/cfg-ms-76f88dd788-rtxjv     1/1     Running   0          33m    10.244.1.40   k8s-workernode   <none>
pod/dept-ms-648d97c769-mgxjw    1/1     Running   1          33m    10.244.1.41   k8s-workernode   <none>
pod/emp-ms-74f8745784-5szxs     1/1     Running   1          33m    10.244.1.42   k8s-workernode   <none>
pod/org-ms-5d58c7dc5-h5p4n      1/1     Running   1          33m    10.244.1.43   k8s-workernode   <none>
pod/proxy-ms-746b697b6f-pft55   1/1     Running   0          108s   10.244.1.45   k8s-workernode   <none>

NAME            TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE    SELECTOR
service/cfg     ClusterIP   10.106.233.113   <none>        8888/TCP   33m    app=cfg-ms
service/dept    ClusterIP   10.101.59.149    <none>        8081/TCP   33m    app=dept-ms
service/emp     ClusterIP   10.109.184.6     <none>        8080/TCP   33m    app=emp-ms
service/org     ClusterIP   10.110.65.182    <none>        8082/TCP   33m    app=org-ms
service/proxy   ClusterIP   10.106.10.8      <none>        8111/TCP   109s   app=proxy-ms

NAME                             DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE    CONTAINERS   IMAGES                                                     SELECTOR
deployment.extensions/cfg-ms     1         1         1            1           33m    cfg-ms       compucomm/organization-mgmt-system:config-server-service   app=cfg-ms
deployment.extensions/dept-ms    1         1         1            1           33m    dept-ms      compucomm/organization-mgmt-system:department-service      app=dept-ms
deployment.extensions/emp-ms     1         1         1            1           33m    emp-ms       compucomm/organization-mgmt-system:employee-service        app=emp-ms
deployment.extensions/org-ms     1         1         1            1           33m    org-ms       compucomm/organization-mgmt-system:organization-service    app=org-ms
deployment.extensions/proxy-ms   1         1         1            1           109s   proxy-ms     compucomm/organization-mgmt-system:proxy-server-service    app=proxy-ms
```
# Create Service, Namespace and Deployments using create/apply command

kubectl apply -f ccoms-namespace.yaml -f ccoms-svc.yaml -f ccoms-deployment.yaml

```
[root@k8s-master k8s-deployment]# kubectl apply -f ccoms-namespace.yaml -f ccoms-svc.yaml -f ccoms-deployment.yaml
namespace/ccoms created
service/cfg created
service/emp created
service/dept created
service/org created
service/proxy created
deployment.apps/cfg-ms created
deployment.apps/emp-ms created
deployment.apps/dept-ms created
deployment.apps/org-ms created
deployment.apps/proxy-ms created
``` 

