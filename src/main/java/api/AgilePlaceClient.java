package api;

import model.*;
import com.google.gson.Gson;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static model.Semestre.Mois.moisAPartirDe;
import static model.Semestre.obtenirMoisActuel;


public class AgilePlaceClient {
    private OkHttpClient client;
    private AgilePlaceConnector apiConnector;
    private int callCount;
    String currentMonth = "JANUARY";
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int REQUEST_DELAY_MS = 500;
    private int BOARD_CRM_VERIfICATION_TERMINE = 2055641730;
    private int BOARD_ESTIMATION_VERIfICATION_SYLVAIN = 2063614479;

    private int CALENDRIER_BOARD = 2057018447;
    private int CALENDRIER_LANE_1 = 2057018491;
    private int CALENDRIER_LANE_2 = 2070897052;
    private int CALENDRIER_LANE_3 = 2070897053;
    private int CALENDRIER_LANE_4 = 2070897054;
    private int CALENDRIER_LANE_5 = 2070897055;
    private int CALENDRIER_LANE_6 = 2070897056;
    private String BOARD_ANDRE = "2063586246";
    private String BOARD_JESSY = "2063637757";
    private String BOARD_MARIO = "2063637758";


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

    private Cards getListOfCardsFromLane(int laneId) {
        String cardsString = makeAPICall("/card?lanes=" + laneId, "GET", null);
        Gson gson = new Gson();
        Cards cards = gson.fromJson(cardsString, Cards.class);
        return cards;
    }

    //Deplacement des cartes enfant
    public void moveCardOfBoardEstimationOfTheLaneSylvain() {

        Cards cards = getListOfCardsFromLane(BOARD_ESTIMATION_VERIfICATION_SYLVAIN);
        Cards cards2 = getListOfCardsFromLane(BOARD_CRM_VERIfICATION_TERMINE);

        List<Card> cardList = cards.getCards();
        List<Card> cardList2 = cards2.getCards();
        try {
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
        } catch (Exception e) {
            System.out.println("Une exception spécifique s'est produite dans moveCardOfBoardEstimationOfTheLaneSylvain() : " + e.getMessage());

        }


    }

    private Lane getInfoOfLane(int boardId, int laneId) {
        Gson gson = new Gson();
        String json = "";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, JSON);
        String reponseAPI = makeAPICall("/board/" + boardId + "/lane/" + laneId, "PATCH", requestBody);
        return gson.fromJson(reponseAPI, Lane.class);
    }

    //Mettre ajour une Voie(Lane)
    private Lane updateALane(int boardId, int laneId, Map<String, Object> propertiesToUpdate) {
        Gson gson = new Gson();
        String json = gson.toJson(propertiesToUpdate);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, JSON);
        String reponseAPI = makeAPICall("/board/" + boardId + "/lane/" + laneId, "PATCH", requestBody);
        return gson.fromJson(reponseAPI, Lane.class);
    }

    // Exemple d'utilisation pour mettre à jour le titre et la description de la voie
    private void updateLaneTitleAndDescription() {
        Cards cards = getListOfCardsFromLane(2057018482);
        Lane laneTitleCompare = getInfoOfLane(CALENDRIER_BOARD,CALENDRIER_LANE_1);
        List<Semestre.Mois> listMonthCompare = moisAPartirDe(obtenirMoisActuel());
        LocalDate dateActuelle = LocalDate.now();
        try {
            if (!dateActuelle.getMonth().name().equals(currentMonth) && !laneTitleCompare.getTitle().equals(listMonthCompare.getFirst().name())) {
                currentMonth = dateActuelle.getMonth().name();
                List<Semestre.Mois> listMonth = moisAPartirDe(obtenirMoisActuel());
                Map<String, Integer> calendrierBoardAndLaneMap = new HashMap<>();
                calendrierBoardAndLaneMap.put("boardId", CALENDRIER_BOARD);
                calendrierBoardAndLaneMap.put("laneId_1", CALENDRIER_LANE_1);
                calendrierBoardAndLaneMap.put("laneId_2", CALENDRIER_LANE_2);
                calendrierBoardAndLaneMap.put("laneId_3", CALENDRIER_LANE_3);
                calendrierBoardAndLaneMap.put("laneId_4", CALENDRIER_LANE_4);
                calendrierBoardAndLaneMap.put("laneId_5", CALENDRIER_LANE_5);
                calendrierBoardAndLaneMap.put("laneId_6", CALENDRIER_LANE_6);


                for (int i = 0; i < calendrierBoardAndLaneMap.size() - 1; i++) {
                    Map<String, Object> propertiesToUpdate = new HashMap<>();
                    propertiesToUpdate.put("title", listMonth.get(i));
                    propertiesToUpdate.put("description", "");
                    System.out.println("propertiesToUpdate : " + propertiesToUpdate.get("title"));
                    updateALane(calendrierBoardAndLaneMap.get("boardId"), calendrierBoardAndLaneMap.get("laneId_" + (i + 1)), propertiesToUpdate);

                    for (Card card : cards.getCards()) {
                        int moveTo = (i + 5) % 6;
                        if (moveTo != 5) {
                            System.out.println("moveTo" + moveTo);
                            System.out.println("card.getLane().getId()" + card.getLane().getId());
                            System.out.println("calendrierBoardAndLaneMap.get(\"laneId_\" + i)" + calendrierBoardAndLaneMap.get("laneId_" + (i + 1)));
                            System.out.println("calendrierBoardAndLaneMap.get(\"laneId_\" + moveTo)" + calendrierBoardAndLaneMap.get("laneId_" + (moveTo + 1)));

                            if (card.getLane().getId().equals(String.valueOf(calendrierBoardAndLaneMap.get("laneId_" + (i + 1))))) {
                                moveCard(card.getId(), String.valueOf(calendrierBoardAndLaneMap.get("laneId_" + (moveTo + 1))));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Une exception spécifique s'est produite dans updateLaneTitleAndDescription() : " + e.getMessage());
        }
    }

    //Calendrier automatisation
    public void calenderAutomate() {
        updateLaneTitleAndDescription();
        System.out.println("calenderAutomate() FINISH");
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
