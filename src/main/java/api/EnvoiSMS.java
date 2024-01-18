package api;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class EnvoiSMS {

    static String apiSMSAccount = System.getenv("SMS_ACCOUNT_SID");
    static String apiSMSKey = System.getenv("SMS_AUTH_TOKEN");
    static String twilioPhoneNumber = System.getenv("TWILIO_PHONE_NUMBER");


    static {
        Twilio.init(apiSMSAccount,apiSMSKey);
    }

    public static void envoyerSMS(String toPhoneNumber, String messageBody) {
        // Numéro de téléphone de destination
        PhoneNumber to = new PhoneNumber(toPhoneNumber);

        // Numéro Twilio (votre numéro Twilio)
        PhoneNumber from = new PhoneNumber("twilioPhoneNumber");

        // Envoi du SMS
        Message message = Message.creator(to, from, messageBody).create();

        // Affichage de l'ID du message (pour vérification)
        System.out.println("Message SID: " + message.getSid());
    }
}
