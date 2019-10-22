
1. For dns related changes, edit below config map

kubectl edit cm coredns -n kube-system

rewrite name cfg.ccoms.com cfg.ccoms.svc.cluster.local
rewrite name dept.ccoms.com dept.ccoms.svc.cluster.local
rewrite name org.ccoms.com org.ccoms.svc.cluster.local
rewrite name emp.ccoms.com emp.ccoms.svc.cluster.local
rewrite name proxy.ccoms.com proxy.ccoms.svc.cluster.local
rewrite name db.mongo.com db.mongo.svc.cluster.local
