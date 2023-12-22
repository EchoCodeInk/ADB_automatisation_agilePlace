package api;

import com.google.gson.Gson;
import model.TokenForMail;
import okhttp3.*;

public class EmailConnector {

    // Informations d'authentification
    private String CLIENT_ID = "fc2fbe2c-2455-49c9-a20e-b9ef9ebc13b4";
    private String CLIENT_SECRETS = "6g18Q~WVEacKuGV8h1M.U-BRB3xM4KxZHe0lZb~P";
    private String LOCATAIRE_ID = "amidubois2022.onmicrosoft.com";

    private TokenForMail token;

    protected TokenForMail apiCallMailToken() {

        // URL pour obtenir le jeton d'accès
        String tokenUrl = String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", LOCATAIRE_ID);

        // Paramètres pour la requête du jeton d'accès
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRETS)
                .add("scope", "https://graph.microsoft.com/.default")
                .build();

        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(formBody)
                .build();

        // Exécution de la requête pour obtenir le jeton d'accès
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                // Traitement de la réponse JSON pour extraire le jeton d'accès
                // Utilisez une bibliothèque comme Gson pour gérer la réponse JSON
                // Voici un exemple simple pour extraire le jeton d'accès


                String jsonResponse = response.body().string();
                Gson gson = new Gson();
                TokenForMail tokenResponse = gson.fromJson(jsonResponse, TokenForMail.class);
                if (tokenResponse != null) {
                    token = tokenResponse;
                }


            } else {
                System.out.println("Erreur : " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }


}
