package api;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvoiSMS {

    static String apiSMSAccount;
    static String apiSMSKey;
    static String twilioPhoneNumber;

    static {
        // Récupérer les valeurs depuis les variables d'environnement
        apiSMSAccount = System.getenv("SMS_ACCOUNT_SID");
        apiSMSKey = System.getenv("SMS_AUTH_TOKEN");
        twilioPhoneNumber = System.getenv("TWILIO_PHONE_NUMBER");

        // Si certaines variables d'environnement ne sont pas définies, charger depuis le fichier application.properties
        if (apiSMSAccount == null || apiSMSKey == null || twilioPhoneNumber == null) {
            loadProperties();
        }

        // Initialiser Twilio
        Twilio.init(apiSMSAccount, apiSMSKey);
    }

    private static void loadProperties() {
        try (InputStream input = EnvoiSMS.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("Sorry, unable to find application.properties");
                return;
            }

            prop.load(input);

            // Ne surcharge le chargement depuis le fichier que si les variables d'environnement ne sont pas définies
            if (apiSMSAccount == null) {
                apiSMSAccount = prop.getProperty("SMS_ACCOUNT_SID");
            }
            if (apiSMSKey == null) {
                apiSMSKey = prop.getProperty("SMS_AUTH_TOKEN");
            }
            if (twilioPhoneNumber == null) {
                twilioPhoneNumber = prop.getProperty("TWILIO_PHONE_NUMBER");
            }
        } catch (IOException e) {
            System.out.println("Une exception spécifique s'est produite dans loadProperties() " + e.getMessage());
        }
    }

    public static void envoyerSMS(String toPhoneNumber, String messageBody) {
        // Numéro de téléphone de destination
        PhoneNumber to = new PhoneNumber(toPhoneNumber);

        // Numéro Twilio (votre numéro Twilio)
        PhoneNumber from = new PhoneNumber(twilioPhoneNumber);

        // Envoi du SMS
        Message message = Message.creator(to, from, messageBody).create();

        // Affichage de l'ID du message (pour vérification)
        System.out.println("Message SID: " + message.getSid());
    }
}
