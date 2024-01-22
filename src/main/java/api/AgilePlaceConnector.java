package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AgilePlaceConnector {
    private String apiUrl;
    private String apiKey;

    public AgilePlaceConnector() {
        // Récupérer les valeurs depuis les variables d'environnement
        apiUrl = System.getenv("API_BASE_URL");
        apiKey = System.getenv("API_JETON_KEY");

        // Si certaines variables d'environnement ne sont pas définies, charger depuis le fichier application.properties
        if (apiUrl == null || apiKey == null) {
            loadProperties();
        }
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("Sorry, unable to find application.properties");
                return;
            }

            prop.load(input);

            // Ne surcharge le chargement depuis le fichier que si les variables d'environnement ne sont pas définies
            if (apiUrl == null) {
                apiUrl = prop.getProperty("API_BASE_URL");
            }
            if (apiKey == null) {
                apiKey = prop.getProperty("API_JETON_KEY");
            }
        } catch (IOException e) {
            System.out.println("Une exception spécifique s'est produite dans loadProperties() : " + e.getMessage());
        }
    }

    protected String getAPIBaseUrl() {
        return apiUrl;
    }

    protected String getAPIJetonKey() {
        return apiKey;
    }
}
