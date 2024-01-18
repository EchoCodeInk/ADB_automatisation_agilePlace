package api;

public class AgilePlaceConnector {
    String apiUrl = System.getenv("API_BASE_URL");
    String apiKey = System.getenv("API_JETON_KEY");
    protected String getAPIBaseUrl() {
        return apiUrl;
    }
    protected String getAPIJetonKey() {
        return apiKey;
    }

}
