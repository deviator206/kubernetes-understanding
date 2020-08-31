# Secrets

## create simple secret
data required to plugin is suppsed to be base64 encoded.

so i would go ahead and create
> echo -n "admin" | base64
> YWRtaW4=

Below is the sample YAML
<pre>
apiVersion: v1
kind: Secret
metadata:
  name: my-cred-secret
data:
  uname: YWRtaW4=

</pre>

## Execute on terminal for a particular pod
> kubectl exec -it 2-pod -- sh

### Create Pod which reads Secrets from volumes
> First add volumes, mention the type of volume
<pre>
spec:
  volumes:
    - name: my-cred-secret
      secret:
          secretName: my-cred-secret
</pre>

> Specify the volumeMounts inside container
<pre>
volumeMounts:
      - name: my-cred-secret
        mountPath: /deviator/my-data
</pre>

> create this pod
> execute inside the terminal
  <code> "kubectl exec -it 2-pod -- sh"</code>
> now inside the pod/container check the filesystem
 <code> ls /deviator/my-data </code>
> One would see differnt files each with key Name & value is the content of the file
 <code> cat /deviator/my-data/userName </code>
 value is <b> admin </b>

 > Also if secret is modified, just relogging into the pod terminal you would see the latest values


 # ConfigMaps
 Similar to secrets, only change in ConfigMap
 