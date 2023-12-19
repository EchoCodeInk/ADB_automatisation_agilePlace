package api;

import Model.Carte;
import com.google.gson.Gson;

import okhttp3.*;

import java.io.IOException;


public class APIClient {


    private OkHttpClient client;
    private APIConnector apiConnector;


    public APIClient() {
        this.apiConnector = new APIConnector();
        this.client = new OkHttpClient();
    }

    public String makeAPICall(String endpoint, String method, RequestBody requestBody) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(apiConnector.getAPIBaseUrl() + endpoint)
                .addHeader("Authorization", "Bearer " + apiConnector.getAPIJetonKey());

        // Selon la méthode, configurer la requête correctement
        switch (method.toUpperCase()) {
            case "GET":
                requestBuilder = requestBuilder.get();
                break;
            case "POST":
                requestBuilder = requestBuilder.post(requestBody);
                break;
            case "DELETE":
                requestBuilder = requestBuilder
                        .addHeader("X-HTTP-Method-Override", "DELETE")
                        .post(requestBody);
                break;
            case "PATCH":
                requestBuilder = requestBuilder
                        .addHeader("X-HTTP-Method-Override", "PATCH")
                        .post(requestBody);
                break;
            // Ajoute d'autres méthodes si nécessaire (PUT, PATCH, etc.)
            default:
                System.out.println("makeAPICall() >> Gérer le cas par défaut, la méthode n'est pas prise en charge.");
                return null;
        }

        Request request = requestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                String errorBody = response.body().string();
                System.out.println(errorBody);
                // Gérer les erreurs ici
                return null;
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public Carte getInfoCard(String id) {
        String reponseAPI  = makeAPICall("/card/"+id,"GET",null);

        if (reponseAPI != null) {
            System.out.println("getInfoCard() >> Réponse de l'API : " + reponseAPI);
        } else {
            System.out.println("getInfoCard() >> Erreur lors de l'appel à l'API."+ reponseAPI);
        }

        Gson gson = new Gson();
        return gson.fromJson(reponseAPI, Carte.class);
    }

    public String deleteCard(int id) {
        RequestBody requestBody = new FormBody.Builder().build();
        return makeAPICall("/card/" + id ,"DELETE", null);
    }

    //Déplacer une seule carte dans une autre voie(colonne)
    public String moveCard(String cardId, String destinationLaneId) {
            String json = "{\"cardIds\":[\"" + cardId + "\"],\"destination\":{\"laneId\":\"" + destinationLaneId + "\"}}";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(json, JSON);
            return makeAPICall("/card/move", "POST", requestBody);
    }

    //Obtenez une liste de tous les cartes pour un tableau spécifique
    public String getListOfCards(int boardId) {
        return makeAPICall( "/card?board=" + boardId , "GET", null);
    }

    // Mise a jour d'une carte specifique
    public String updateCard(String cardId) {
       Carte card = getInfoCard(cardId);

        //card.setWipOverrideComment("setWipOverrideComment que ce passe til ici");     //<<< A voir ou ce commentaire ce fait
        //card.setParents();                                                            //<<< Doit avoir ca propre fonction de mis a jour
        //card.setCustomIconId("2057018495");                                           //<<< Probleme quand je change ce champs
        //card.setTypeId("123456789");                                                  //<<< Probleme quand je change ce champs

        String json = "[";
        if (card.getVersion()!= null){
            json += " { \"op\": \"test\", \"path\": \"/version\", \"value\": \""+ card.getVersion() +"\" },";
        }
        if (card.getTitle()!= null){
            json += "{ \"op\":\"replace\",\"path\": \"/title\", \"value\":\"" + card.getTitle() +"\" },";
        }
        if (card.getTypeId()!= null){
            json += "{ \"op\": \"replace\", \"path\": \"/typeId\", \"value\": \"" + card.getTypeId() + "\" },";
        }
        if (card.getBlockReason()!= null){
            json += "{ \"op\": \"replace\", \"path\": \"/blockReason\", \"value\": \"" + card.getBlockReason() + "\" },";
        }
        if (card.getCustomIconId()!= null){
            json += "{ \"op\": \"replace\", \"path\": \"/customIconId\", \"value\": \"" + card.getCustomIconId() + "\" },";
        }
        if (card.getCustomId()!= null){
            json += "{ \"op\": \"replace\", \"path\": \"/customId\", \"value\": \"" + card.getCustomId().getValue() + "\" },";// Ceci est la section En-tete de la carte
        }
        if (card.getDescription()!= null){
            json += "{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"" + card.getDescription() + "\" },";
        }
        if (card.getIndex() != 0){
            json += "{ \"op\": \"replace\", \"path\": \"/index\", \"value\": " + card.getIndex() + " },";
        }
        if (card.isBlocked()) {
            json += "{ \"op\": \"replace\", \"path\": \"/isBlocked\", \"value\": true },";
        } else {
            json += "{ \"op\": \"replace\", \"path\": \"/isBlocked\", \"value\": false },";
        }
        if (card.getLaneId()!= null){
            json += "{ \"op\": \"replace\", \"path\": \"/laneId\", \"value\": \"" + card.getLaneId() + "\" },";// Ceci est le #Id de colonne
        }
        if (card.getSize()!= 0){
            json += "{ \"op\": \"replace\", \"path\": \"/size\", \"value\": " + card.getSize() + " },";
        }

//        Revenir a un autre momment pour regler ses attributs

//       if (card.getParentCards()!= null){
//           json += "{ \"op\": \"replace\", \"path\": \"/parentCardId\", \"value\": \"" + "2057179454" + "\" },";
//       }
//       if (planningIncrementIds != null) {
//           json += "{ \"op\": \"add\", \"path\": \"/planningIncrementIds/-\", \"value\": \"" + planningId + "\" },";
//       }
//       if (tags != null) {
//           json += "{ \"op\": \"add\", \"path\": \"/tags/-\", \"value\": \"" + tag + "\" },";
//       }
//       if (assignedUserIds != null) {
//           json += "{ \"op\": \"add\", \"path\": \"/assignedUserIds/-\", \"value\": \"" + userId + "\" },";
//       }
//       if (card.getWipOverrideComment()!= null){
//           json += "{ \"op\": \"replace\", \"path\": \"/wipOverrideComment\", \"value\": \"" + card.getWipOverrideComment() + "\" },";
//       }



        // Supprimez la virgule en trop à la fin
        json = json.substring(0, json.length() - 1);

        // Finalisez la chaîne JSON
        json += "]";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, JSON);


        System.out.println("updateCard() >> json" + json);
        return makeAPICall("/card/" + card.getId(), "PATCH", requestBody);
    }

//    public String updateCard(String cardId) {
//
//        Carte card = getInfoCard(cardId);
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = "[";
//             json += objectMapper.writeValueAsString(card);
//            json+="]";
//            System.out.println("updateCard() >> json" + json);
//
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//            RequestBody requestBody = RequestBody.create(json, JSON);
//
//            return makeAPICall("/card/" + card.getId(), "PATCH", requestBody);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }



    // Autres méthodes pour interagir avec l'API peuvent être ajoutées ici
}
