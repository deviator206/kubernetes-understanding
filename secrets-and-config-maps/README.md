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


 # Pod reading Predefined configMap
 This is simple REST endpoint which on invocation will read content from ConfigMap
 ## Reading configMap
 Below endpoint has the code for reading from CM
 <code>/getcm </code> 
 > First need the k8ClientInstance 
 <pre> 
  try (KubernetesClient client = new DefaultKubernetesClient()) { 
    ....
  } catch (KubernetesClientException ex) {
    ...
  }
 </pre>
 > Get  from the env
 <pre>
 ConfigMap swaggerCfgData = client.configMaps();
 </pre>
 >Get Specific CM
 <pre>
  ConfigMap swaggerCfgData = client.configMaps().withName("my-some-cm-2").get();
 </pre>
 >Iterate over the data 
 <pre>
  Map<String, String> swaggerInfo = swaggerCfgData.getData();
  Iterator<Map.Entry<String, String>> iterator = textInfo.entrySet().iterator();
  while (iterator.hasNext()) {
     Map.Entry<String, String> entry = iterator.next();
   System.out.println(entry.getKey() + ":" + entry.getValue());
  }
 </pre>

 >Watch the changes over CMs - Action & ConfigMap:
 Action:  [  ADDED, MODIFIED, DELETED, ERROR]
 ConfigMap: CM which is being impacted
 <pre>
 static KubernetesClient k8Client = new DefaultKubernetesClient();
 
 k8Client.configMaps().watch(new Watcher<ConfigMap>() {
  @Override
  public void eventReceived(Action action, ConfigMap resource)
  {...}

  @Override
  public void onClose(KubernetesClientException cause) 
  {...}
 }
 </pre>

> Deploy the changes via image

> Post which after the POD is up make changes in ConfigMap, casue of which below event would be triggered

> Sample of the action, resource - onEventReceived

<pre>
 ACTION :: MODIFIED
 resource :: {NEW_CHANGE=dummy, app=also if secret is }
</pre>
 