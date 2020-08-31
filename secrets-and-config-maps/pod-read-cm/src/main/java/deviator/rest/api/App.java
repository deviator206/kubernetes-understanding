package deviator.rest.api;

import java.util.Map;
import java.util.Random;
import java.util.Iterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
public class App {

	static KubernetesClient k8Client = new DefaultKubernetesClient();
			
    @RequestMapping("/home")
    public String home() {
        return "Hello Docker World";
    }

    @RequestMapping("/home2")
    public String home2() {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            System.out.println(" total Pods " + client.pods().inNamespace("default").list().getItems().size());
            ConfigMap cfgData = client.configMaps().withName("game-config-env-file").get();
            Map<String, String> hmInfo = cfgData.getData();
            System.out.println(" CONTENT :: "+hmInfo.get("enemies"));
       
        } catch (KubernetesClientException ex) {
            ex.printStackTrace();
        }
        return "Hello Docker World 2222";
    }

    private String concatString(Map<String, String> textInfo) {
        String finalKeys= "";
        Iterator<Map.Entry<String, String>> iterator = textInfo.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
            finalKeys += entry.getKey();
        }
        return finalKeys;
    }

    @RequestMapping("/getcm")
    public String getcm() {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            System.out.println(" reading CFG Maps" );
            ConfigMap swaggerCfgData = client.configMaps().withName("my-some-cm-2").get();
            System.out.println(" read [my-some-cm-2]" );
            ConfigMap textCfgData = client.configMaps().withName("my-some-cm").get();
            System.out.println(" read [my-some-cm]" );
            Map<String, String> swaggerInfo = swaggerCfgData.getData();
            Map<String, String> textInfo = textCfgData.getData();
            System.out.println("  trying to iterate over it..." );
            String value1 = this.concatString(textInfo);
            String value2 = this.concatString(swaggerInfo);
            return value1 +" |||| "+ value2;
        } catch (KubernetesClientException ex) {
            // Handle exception
            ex.printStackTrace();
            return "Error";
        }
        
    }
    
    
    @RequestMapping("/createcm")
    public String createCM() {
    	
    	Random random = new Random();
    	int keySuffix = random.nextInt(898);
    	ConfigMap configMap = k8Client.configMaps().inNamespace("default").createOrReplaceWithNew()
    		      .withNewMetadata().withName("dynamic-created-01").endMetadata()
    		      .addToData("1_"+String.valueOf(keySuffix), "one___"+random.nextInt(100))
    		      .addToData("2_"+String.valueOf(keySuffix), "two__"+random.nextInt(200))
    		      .addToData("3_"+String.valueOf(keySuffix), "three__"+random.nextInt(300))
    		      .done();
    	return configMap.getData().toString();
    }
    
    
    @RequestMapping("/updatecm")
    public String updateCM() {
    	Random random = new Random();
    	int keySuffix = random.nextInt(898);
    	ConfigMap configMap = k8Client.configMaps().inNamespace("default").withName("my-some-cm").edit()
    		      .addToData("1_"+String.valueOf(keySuffix), "one___"+random.nextInt(100))
    		      .done();
    	return configMap.getData().toString();
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);

        System.out.println( "App Initialized!!! Re" );
        
        k8Client.configMaps().inNamespace("default").watch(new Watcher<ConfigMap>() {
        	  @Override
        	  public void eventReceived(Action action, ConfigMap resource) {
        	    // Do something depending upon action type  
        		  System.out.println("***************************");
        		  System.out.println(" ACTION :: "+action.toString());
        		  System.out.println(" resource :: "+resource.getData().toString());
        		  System.out.println("***************************");
        	  }

        	  @Override
        	  public void onClose(KubernetesClientException cause) {
        		  System.out.println(" cause :: "+ cause.getLocalizedMessage());
        	  }
        	});
        
    }
}
