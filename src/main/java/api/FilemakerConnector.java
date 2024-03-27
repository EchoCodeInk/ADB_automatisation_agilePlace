package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FilemakerConnector {
    String apiUrl;
    String databaseName;

    String login;
    String pass;


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
                apiUrl = prop.getProperty("FM_API_URL");
            }
            if (databaseName == null) {
                databaseName = prop.getProperty("FM_DATABASE_NAME");
            }
            if (login == null) {
                login = prop.getProperty("FM_LOGIN");
            }
            if (pass == null) {
                pass = prop.getProperty("FM_PASS");
            }
        } catch (
                IOException e) {
            System.out.println("Une exception spécifique s'est produite dans loadProperties() : " + e.getMessage());
        }


    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}


