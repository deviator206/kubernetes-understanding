## Step#1 Simple REST in spring-boot & Simple Docker file

<pre>
 <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
  </dependency>
</pre>

## Step#2 Simple REST in spring-boot
Expose it to local docker 
<pre>
 docker build -t <MY_IMAG_NAME> DIRECTORY_WHERE_DOCKERFILE_EXIST
</pre>

But since we are not publishing the image to server /registry 
So to use an image without uploading it, you can follow these steps:
1. Set the environment variables with eval $(minikube docker-env)
2. Build the image with the Docker daemon of Minikube (eg docker build -t my-image .)
3. Set the image in the pod spec like the build tag (eg my-image)
4. Set the imagePullPolicy to Never, otherwise Kubernetes will try to download the image.
Important note: You have to run eval $(minikube docker-env) on each terminal you want to

## Step#3 - Create POD in kubernetes  (minikube in my case)
<pre>
 kubectl apply -f pod-creation.yaml
</pre>


## Step#4 - get Pod details
<pre>
 kubectl get pods -o wide
</pre>


## Step#5 - Access Pod from within the cluster (in my case minikube)
<pre>
 > minikube ssh

 // HIT THE ENDPOINT

 > curl http:[HOST]:[PORT]//home
 // get the response as hello.. which indicates all is well

 > curl http:[HOST]:[PORT]//home2
 // this reads the configmaps 

 
</pre>

*** Make sure Role & Role Binding is assigned...