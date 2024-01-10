package api;

import model.*;
import com.google.gson.Gson;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AgilePlaceClient {

    private OkHttpClient client;
    private AgilePlaceConnector apiConnector;
    private int callCount;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int REQUEST_DELAY_MS = 500;
    private int BOARD_CRM_VERIfICATION_TERMINE = 2055641730;
    private int BOARD_ESTIMATION_VERIfICATION_SYLVAIN = 2063614479;
    private String BOARD_ANDRE = "2063586246";
    private String BOARD_JESSY = "2063637757";
    private String BOARD_MARIO = "2063637758";

    public enum Mois {JANVIER, FEVRIER, MARS, AVRIL, MAI, JUIN, JUILLET, AOUT, SEPTEMBRE, OCTOBRE, NOVEMBRE, DECEMBRE}


    public AgilePlaceClient() {
        this.callCount = 0;
        this.apiConnector = new AgilePlaceConnector();
        this.client = new OkHttpClient();
    }

    private String makeAPICall(String endpoint, String method, RequestBody requestBody) {
        callCount++;
        Request.Builder requestBuilder = new Request.Builder()
                .url(apiConnector.getAPIBaseUrl() + endpoint)
                .addHeader("Authorization", "Bearer " + apiConnector.getAPIJetonKey());
        System.out.println("makeAPICall() Count >> " + callCount);
        // Selon la méthode, configurer la requête correctement
        switch (method.toUpperCase()) {
            case "GET":
                requestBuilder = requestBuilder.get();
                break;
            case "POST":
                requestBuilder = requestBuilder.post(requestBody);
                break;
            case "DELETE":
                requestBuilder = requestBuilder.addHeader("X-HTTP-Method-Override", "DELETE")
                        .post(requestBody);
                break;
            case "PATCH":
                requestBuilder = requestBuilder.addHeader("X-HTTP-Method-Override", "PATCH")
                        .post(requestBody);
                break;
            // Ajoute d'autres méthodes si nécessaire (PUT, PATCH, etc.)
            default:
                System.out.println("makeAPICall() >> Gérer le cas par défaut, la méthode n'est pas prise en charge.");
        }

        Request request = requestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            int statusCode = response.code();

            if (statusCode == TOO_MANY_REQUESTS) {
                // Gérer la réponse 429 Too Many Requests
                String retryAfter = response.header("Retry-After");

                if (retryAfter != null) {
                    try {
                        // Convertir la date RFC 2822 en un objet Date
                        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                        Date retryDate = format.parse(retryAfter);

                        // Calculer le temps d'attente en millisecondes à partir de la date actuelle
                        long currentTime = System.currentTimeMillis();
                        long retryTime = retryDate.getTime();
                        long timeoutMs = Math.max(0, retryTime - currentTime); // Assurez-vous que le délai n'est pas négatif

                        System.out.println("Received a 429 response. Waiting for " + (timeoutMs / 1000) + " seconds before retrying...");
                        Thread.sleep(timeoutMs);

                        // Réessayer l'appel après le délai spécifié
                        return makeAPICall(endpoint, method, requestBody);
                    } catch (ParseException | InterruptedException e) {
                        e.printStackTrace();
                        // Gérer les exceptions ici
                        return null;
                    }
                } else {
                    System.out.println("Retry-After header not found. Cannot determine the wait time.");
                    // Gérer en cas de Retry-After non défini dans l'en-tête
                    return null;
                }
            }

            // Ajouter le délai entre les appels pour respecter les limites de débit
            Thread.sleep(REQUEST_DELAY_MS);

            // Gérer d'autres cas de statut de réponse ici
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                // Gérer les autres codes d'erreur ici
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Gérer les exceptions ici
            return null;
        }
    }


    private Card getInfoCard(String id) {
        String reponseAPI = makeAPICall("/card/" + id, "GET", null);
        Gson gson = new Gson();
        return gson.fromJson(reponseAPI, Card.class);
    }

    //Déplacer une seule carte dans une autre voie(colonne)
    private void moveCard(String cardId, String destinationLaneId) {
        String json = "{\"cardIds\":[\"" + cardId + "\"],\"destination\":{\"laneId\":\"" + destinationLaneId + "\"}}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, JSON);
        makeAPICall("/card/move", "POST", requestBody);
    }

    private Cards getListOfCardsFromLineBoard(int lineId) {
        String cardsString = makeAPICall("/card?lanes=" + lineId, "GET", null);
        Gson gson = new Gson();
        Cards cards = gson.fromJson(cardsString, Cards.class);
        return cards;
    }

    //Deplacement des cartes enfant
    public void moveCardOfBoardEstimationOfTheLaneSylvain() {

        Cards cards = getListOfCardsFromLineBoard(BOARD_ESTIMATION_VERIfICATION_SYLVAIN);
        Cards cards2 = getListOfCardsFromLineBoard(BOARD_CRM_VERIfICATION_TERMINE);

        List<Card> cardList = cards.getCards();
        List<Card> cardList2 = cards2.getCards();

        if (cardList != null && !cardList.isEmpty()) {
            for (Card cardFromList : cardList) {
                Card cardToMove = getInfoCard(cardFromList.getId());

                if (cardList2 != null) {
                    for (Card cardFromList2 : cardList2) {

                        Card cardCompare = getInfoCard(cardFromList2.getId());

                        if (cardCompare != null && cardCompare.getCustomId().getValue().equals(cardToMove.getCustomId().getValue())) {
                            for (AssignedUser assignedUser : cardToMove.getAssignedUsers()) {
                                String fullName = assignedUser.getFullName();
                                if (fullName.equals("Jessy Therrien")) {
                                    moveCard(cardToMove.getId(), BOARD_JESSY);
                                } else if (fullName.equals("André Berthiaume")) {
                                    moveCard(cardToMove.getId(), BOARD_ANDRE);
                                } else if (fullName.equals("Mario Vivier")) {
                                    moveCard(cardToMove.getId(), BOARD_MARIO);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Mettre ajour une Voie(Lane)
    private Lane updateALane(int boardId, int laneId, Map<String, Object> propertiesToUpdate) {
        Gson gson = new Gson();
        String json = gson.toJson(propertiesToUpdate);
        System.out.println("updateALane>>> json: " + json);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, JSON);
        System.out.println("updateALane>>> requestBody: " + requestBody);
        // Effectuer la requête PATCH pour mettre à jour la voie (Lane)
        String reponseAPI = makeAPICall("/board/" + boardId + "/lane/" + laneId, "PATCH", requestBody);
        // Traitement de la réponse de l'API
        System.out.println("updateALane>>> reponseAPI: " + reponseAPI);
        return gson.fromJson(reponseAPI, Lane.class);
    }

    // Exemple d'utilisation pour mettre à jour le titre et la description de la voie
    private void updateLaneTitleAndDescription() {
        int boardId = 2057018447;
        int laneId = 2057018491;

        Map<String, Object> propertiesToUpdate = new HashMap<>();
        propertiesToUpdate.put("title", "new lane title");
        propertiesToUpdate.put("description", "the new lane policy");

        Lane updatedLane = updateALane(boardId, laneId, propertiesToUpdate);
        System.out.println("updateLaneTitleAndDescription >>> Updated Lane: " + updatedLane);
    }

    //Calendrier automatisation
    public void calenderAutomate() {
        updateLaneTitleAndDescription();

        System.out.println("testing");
    }


    private CardEvent getActivityFromCard(String cardId) {
        String activityString = makeAPICall("/card/" + cardId + "/activity?limit=5&direction=newer", "GET", null);
        Gson gson = new Gson();
        return gson.fromJson(activityString, CardEvent.class);
    }

    //Obtenez une liste de tous les cartes pour un tableau spécifique
    private String getListOfCardsFromBoard(int boardId) {
        return makeAPICall("/card?board=" + boardId, "GET", null);
    }

    // Mise a jour d'une carte specifique
    public void updateCard(@NotNull Card card) {
        //card.setWipOverrideComment("setWipOverrideComment que ce passe til ici");     //<<< A voir ou ce commentaire ce fait
        //card.setParents();                                                            //<<< Doit avoir ca propre fonction de mis a jour
        //card.setCustomIconId("2057018495");                                           //<<< Probleme quand je change ce champs
        //card.setTypeId("123456789");                                                  //<<< Probleme quand je change ce champs

        String json = "[";
        if (card.getVersion() != null) {
            json += " { \"op\": \"test\", \"path\": \"/version\", \"value\": \"" + card.getVersion() + "\" },";
        }
        if (card.getTitle() != null) {
            json += "{ \"op\":\"replace\",\"path\": \"/title\", \"value\":\"" + card.getTitle() + "\" },";
        }
        if (card.getType().getId() != null) {
            json += "{ \"op\": \"replace\", \"path\": \"/typeId\", \"value\": \"" + card.getType().getId() + "\" },";
        }
        if (card.getBlockReason() != null) {
            json += "{ \"op\": \"replace\", \"path\": \"/blockReason\", \"value\": \"" + card.getBlockReason() + "\" },";
        }
        if (card.getCustomIconId() != null) {
            json += "{ \"op\": \"replace\", \"path\": \"/customIconId\", \"value\": \"" + card.getCustomIconId() + "\" },";
        }
        if (card.getCustomId() != null) {
            json += "{ \"op\": \"replace\", \"path\": \"/customId\", \"value\": \"" + card.getCustomId().getValue() + "\" },";// Ceci est la section En-tete de la carte
        }
        if (card.getDescription() != null) {
            json += "{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"" + card.getDescription() + "\" },";
        }
        if (card.getIndex() != 0) {
            json += "{ \"op\": \"replace\", \"path\": \"/index\", \"value\": " + card.getIndex() + " },";
        }
        if (card.isBlocked()) {
            json += "{ \"op\": \"replace\", \"path\": \"/isBlocked\", \"value\": true },";
        } else {
            json += "{ \"op\": \"replace\", \"path\": \"/isBlocked\", \"value\": false },";
        }
        if (card.getLane().getId() != null) {
            json += "{ \"op\": \"replace\", \"path\": \"/laneId\", \"value\": \"" + card.getLane().getId() + "\" },";// Ceci est le #Id de colonne
        }
        if (card.getSize() != 0) {
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


        //System.out.println("updateCard() >> json" + json);
        makeAPICall("/card/" + card.getId(), "PATCH", requestBody);
    }

}
