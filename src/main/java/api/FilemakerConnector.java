package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FilemakerConnector {

    String apiUrl;
    String database;

    String version;
    String username;
    String password;

    String oAuthRequestId;
    String oAuthIdentifier;



    public FilemakerConnector() {
        try (
                InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("Sorry, unable to find application.properties");
                return;
            }

            prop.load(input);

            // Ne surcharge le chargement depuis le fichier que si les variables d'environnement ne sont pas définies
            if (apiUrl == null) {
                apiUrl = prop.getProperty("FM_SERVER");
            }
            if (version == null) {
                version = prop.getProperty("FM_API_VERSION");
            }
            if (database == null) {
                database = prop.getProperty("FM_DATABASE");
            }
            if (username == null) {
                username = prop.getProperty("FM_USER_NAME");
            }
            if (password == null) {
                password = prop.getProperty("FM_PASSWORD");
            }
            if (oAuthRequestId == null) {
                oAuthRequestId = prop.getProperty("FM_OAUTH_REQUEST_ID");
            }
            if (oAuthIdentifier == null) {
                oAuthIdentifier = prop.getProperty("FM_OAUTH_IDENTIFIER");
            }
        } catch (
                IOException e) {
            System.out.println("Une exception spécifique s'est produite dans loadProperties() : " + e.getMessage());
        }


    }

    public String getApiUrl() {
        return apiUrl;
    }


    public String getDatabase() {
        return database;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getoAuthRequestId() {
        return oAuthRequestId;
    }

    public String getoAuthIdentifier() {
        return oAuthIdentifier;
    }
}


