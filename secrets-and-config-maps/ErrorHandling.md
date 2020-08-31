## Basic Errors & its Solutions

### Authorization error- while updating config map
<pre>
  https://10.96.0.1/api/v1/namespaces/default/configmaps.
  
  Message: Forbidden!Configured service account doesn't have access. 
  
  Service account may have been revoked. configmaps is forbidden: User "system:serviceaccount:default:default" cannot create resource "configmaps" in API group "" in the namespace "default".] with root cause
  </pre>


  > Lets look at the roles
  <pre>
  apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: pod-reader
  namespace: default
rules:
- apiGroups: [ "" ]
  resources: [ "pods", "services","configmaps"]
  verbs: [ "get", "list", "watch"]
- apiGroups: [ "extensions" ]
  resources: [ "deployments" ]
  verbs: [ "get", "list", "watch"]
---
  </pre>
We do not have update /patch / create authority

[Refer Authorization in K8S](https://kubernetes.io/docs/reference/access-authn-authz/authorization/)

