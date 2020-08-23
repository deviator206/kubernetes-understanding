package deviator.rest.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
public class App {

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
            // client.configMaps().withName("game-config-env-file").createOrReplace()
        
        } catch (KubernetesClientException ex) {
            // Handle exception
            ex.printStackTrace();
        }
        return "Hello Docker World 2222";
    }
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        System.out.println( "App Initialized!!! Re" );
        
    }
}
