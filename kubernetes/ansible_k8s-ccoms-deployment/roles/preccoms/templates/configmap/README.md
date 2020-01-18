CoreDNS
=======
- Updating existing core-dns file

* Create manifest file using existing configmap
``` kubectl get -n kube-system cm/coredns --export -o yaml > coredns-kube-system.yml  ```

* Add below content on above this line:: kubernetes cluster.local in-addr.arpa ip6.arpa { </br>
```
        rewrite name cfg.ccoms.com cfg.ccoms.svc.cluster.local
        rewrite name dept.ccoms.com dept.ccoms.svc.cluster.local
        rewrite name org.ccoms.com org.ccoms.svc.cluster.local
        rewrite name emp.ccoms.com emp.ccoms.svc.cluster.local
        rewrite name proxy.ccoms.com proxy.ccoms.svc.cluster.local
        rewrite name db.mongo.com db.mongo.svc.cluster.local
```

* Forcing CoreDNS to reload the ConfigMap
``` kubectl delete pod --namespace kube-system -l k8s-app=kube-dns ```
The kubectl delete pod command isn't destructive and doesn't cause down time. The kube-dns pods are deleted, and the Kubernetes Scheduler then recreates them. These new pods contain the change in TTL value.
 
* Replace existing coredns configmap through newly created manifest file

``` kubectl replace -n kube-system -f coredns-kube-system.yml ```

Note:: Here, we are using replace since we dont know how this ConfigMap is created. if you try to use apply it wont work.

- Adding Custom core-dns file
