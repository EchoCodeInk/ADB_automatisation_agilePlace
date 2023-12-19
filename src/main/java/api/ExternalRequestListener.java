package api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalRequestListener {

    private final RestTemplate restTemplate;

    public ExternalRequestListener(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void listenToExternalURL() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://adb2022.leankit.com/", String.class);
        // Traitez la réponse ici
        System.out.println("Requête reçue : " + response.getBody());
    }
}