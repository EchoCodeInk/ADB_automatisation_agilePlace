package controller;

import api.ExternalRequestListener;
import org.springframework.web.bind.annotation.GetMapping;

public class ExternalRequestController {
    private final ExternalRequestListener requestListener;

    public ExternalRequestController(ExternalRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    @GetMapping("https://adb2022.leankit.com/")
    public String startListening() {
        requestListener.listenToExternalURL();
        return "Écoute commencée sur l'URL externe.";
    }
}
