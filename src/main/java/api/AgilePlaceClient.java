package api;

import model.*;
import com.google.gson.Gson;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;
import static model.Semestre.Mois.moisAPartirDe;
import static model.Semestre.obtenirMoisActuel;

public class AgilePlaceClient {
    private OkHttpClient client;
    private AgilePlaceConnector apiConnector;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int REQUEST_DELAY_MS = 500;

    // Attribut pour la gestion des estimations terminer de Sylvain
    private static final int BOARD_CRM_VERIfICATION_TERMINE = 2055641730;
    private static final int BOARD_ESTIMATION_VERIfICATION_SYLVAIN = 2063614479;
    private static final String BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_ANDRE = "2070706294";
    private static final String BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_JESSY = "2070706296";
    private static final String BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_MARIO = "2070706298";
    private static final String BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_GAETAN = "2074228815";
    private static final int BOARD_ESTIMATION_LANE_SUIVI_ESTIMATION_SYLVAIN = 2063637759;
    private static final int BOARD_CRM_LANE_SUIVI_ESTIMATION = 2075694230;
    private static final int BOARD_ESTIMATION_LANE_ARCHIVE = 1907499860;

    // Attribut pour le "CALENDRIER DES OBJECTIFS" du board "ESTIMATION"
    String currentMonth;
    private final int BOARD_ESTIMATION = 1954823342;
    private int CALENDRIER_LANE_MONTH_1 = 2072107008;
    private int CALENDRIER_LANE_MONTH_2 = 2072107009;
    private int CALENDRIER_LANE_MONTH_3 = 2072107010;
    private int CALENDRIER_LANE_MONTH_4 = 2072271540;
    private int CALENDRIER_LANE_MONTH_5 = 2072271541;
    private int CALENDRIER_LANE_MONTH_6 = 2072271542;
    private int CALENDRIER_TO_ARCHIVE_GAGNEES = 2058436638;

    //Attribut pour le calendrier "EN COURS D'ESTIMATION" du board "ESTIMATION"
    private int CAL_ANDRE_MAIN_LANE = 2043380283;
    private int CAL_JESSY_MAIN_LANE = 2043624709;
    private int CAL_MARIO_MAIN_LANE = 2043624714;
    private int CAL_GAETAN_MAIN_LANE = 2074240317;
    private final int CAL_ANDRE_SEM_1 = 2063557856;
    private final int CAL_JESSY_SEM_1 = 2063557858;
    private final int CAL_MARIO_SEM_1 = 2063557860;
    private final int CAL_GAETAN_SEM_1 = 2074240315;
    private final int CAL_ANDRE_SEM_2 = 2063557857;
    private final int CAL_JESSY_SEM_2 = 2063557859;
    private final int CAL_MARIO_SEM_2 = 2063557861;
    private final int CAL_GAETAN_SEM_2 = 2074240316;
    private final int CAL_ANDRE_SEM_3 = 2074260484;
    private final int CAL_JESSY_SEM_3 = 2074260485;
    private final int CAL_MARIO_SEM_3 = 2074260487;
    private final int CAL_GAETAN_SEM_3 = 2074260487;
    private final int CAL_ANDRE_SEM_4 = 2075643759;
    private final int CAL_JESSY_SEM_4 = 2075643760;
    private final int CAL_MARIO_SEM_4 = 2075643761;
    private final int CAL_GAETAN_SEM_4 = 2075551143;

    public AgilePlaceClient() {
        this.apiConnector = new AgilePlaceConnector();
        this.client = new OkHttpClient();
    }

    private String makeAPICall(String endpoint, String method, RequestBody requestBody) {
        try {
            client = new OkHttpClient.Builder()
                    .callTimeout(30, TimeUnit.SECONDS)
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    .url(apiConnector.getAPIBaseUrl() + endpoint)
                    .addHeader("Authorization", "Bearer " + apiConnector.getAPIJetonKey());

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
                default:
                    System.out.println("makeAPICall() >> Gérer le cas par défaut, la méthode n'est pas prise en charge.");
            }

            Request request = requestBuilder.build();

            try {
                Response response = client.newCall(request).execute();
                int statusCode = response.code();

                if (statusCode == TOO_MANY_REQUESTS) {
                    handleTooManyRequests(response, endpoint, method, requestBody);
                }

                Thread.sleep(REQUEST_DELAY_MS);

                if (isSuccessful(statusCode)) {
                    assert response.body() != null;
                    return response.body().string();
                } else {
                    handleErrorResponse(response);
                    return null;
                }
            } catch (IOException | InterruptedException e) {
                handleException(e);
                return null;
            }
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    private void handleTooManyRequests(Response response, String endpoint, String method, RequestBody requestBody) {
        String retryAfter = response.header("Retry-After");

        if (retryAfter != null) {
            try {
                // Convertir la date RFC 2822 en un objet Date
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                Date retryDate = format.parse(retryAfter);

                // Calculer le temps d'attente en millisecondes à partir de la date actuelle
                long currentTime = System.currentTimeMillis();
                long retryTime = retryDate.getTime();
                long timeoutMs = Math.max(0, retryTime - currentTime);

                System.out.println("Received a 429 response. Waiting for " + (timeoutMs / 1000) + " seconds before retrying...");
                Thread.sleep(timeoutMs);

                // Réessayer l'appel après le délai spécifié
                makeAPICall(endpoint, method, requestBody);
            } catch (ParseException | InterruptedException e) {
                handleException(e);
            }
        } else {
            System.out.println("Retry-After header not found. Cannot determine the wait time.");
        }
    }

    private boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    private void handleErrorResponse(Response response) {
        System.out.println("Une exception spécifique s'est produite dans makeAPICall() : " + response);
    }

    private void handleException(Exception e) {
        System.out.println("Une exception s'est produite" + e.getMessage());
    }

    private Card getInfoCard(String id) {
        String reponseAPI = makeAPICall("/card/" + id, "GET", null);
        Gson gson = new Gson();
        return gson.fromJson(reponseAPI, Card.class);
    }

    //Déplacer une seule carte dans une autre voie(colonne)
    private void moveCard(String cardId, String destinationLaneId) {
        try {
            String json = "{\"cardIds\":[\"" + cardId + "\"],\"destination\":{\"laneId\":\"" + destinationLaneId + "\"}}";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(json, JSON);
            makeAPICall("/card/move", "POST", requestBody);
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans moveCard() " + e.getMessage());
        }
    }

    private Cards getListOfCardsFromLane(int laneId) {
        String cardsString = makeAPICall("/card?lanes=" + laneId, "GET", null);
        Gson gson = new Gson();
        return gson.fromJson(cardsString, Cards.class);
    }

    //Deplacement des cartes enfant
    public void moveCardOfBoardEstimationOfTheLaneSylvain() {
        try {
            Cards cards = getListOfCardsFromLane(BOARD_ESTIMATION_VERIfICATION_SYLVAIN);
            Cards cards2 = getListOfCardsFromLane(BOARD_CRM_VERIfICATION_TERMINE);
            Cards verificationForDuplicatecards = getListOfCardsFromLane(2055700927);
            List<Card> cardList = cards.getCards();
            List<Card> cardList2 = cards2.getCards();


            if (!cardList.isEmpty()) {
                for (Card cardFromList : cardList) {
                    Card cardToMove = getInfoCard(cardFromList.getId());
                    if (!cardList2.isEmpty()) {
                        for (Card cardFromList2 : cardList2) {
                            Card cardCompare = getInfoCard(cardFromList2.getId());
                            if (cardCompare != null && cardCompare.getCustomId().getValue().equals(cardToMove.getCustomId().getValue())) {
                                for (AssignedUser assignedUser : cardToMove.getAssignedUsers()) {
                                    String fullName = assignedUser.getFullName();
                                    switch (fullName) {
                                        case "Jessy Therrien" ->
                                                moveCard(cardToMove.getId(), BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_JESSY);
                                        case "André Berthiaume" ->
                                                moveCard(cardToMove.getId(), BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_ANDRE);
                                        case "Mario Vivier" ->
                                                moveCard(cardToMove.getId(), BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_MARIO);
                                        case "Gaétan Dussault" ->
                                                moveCard(cardToMove.getId(), BOARD_ESTIMATION_LANE_COURRIEL_ET_RELANCE_GAETAN);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            findDuplicateCard(verificationForDuplicatecards.getCards(), BOARD_ESTIMATION_LANE_ARCHIVE);
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans moveCardOfBoardEstimationOfTheLaneSylvain() " + e.getMessage());
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
    private void updateALane(int boardId, int laneId, Map<String, Object> propertiesToUpdate) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(propertiesToUpdate);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(json, JSON);
            makeAPICall("/board/" + boardId + "/lane/" + laneId, "PATCH", requestBody);
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans updateALane() " + e.getMessage());
        }
    }

    private Map<String, Integer> createCalendrierDesObjectifs() {
        Map<String, Integer> createCalendrierDesObjectifs = new HashMap<>();
        createCalendrierDesObjectifs.put("boardId", BOARD_ESTIMATION);
        int BOARD_ESTIMATION_LANE_CALENDRIER = 2072271545;
        createCalendrierDesObjectifs.put("main_lane", BOARD_ESTIMATION_LANE_CALENDRIER);
        createCalendrierDesObjectifs.put("archive_lane", CALENDRIER_TO_ARCHIVE_GAGNEES);
        createCalendrierDesObjectifs.put("laneId_1", CALENDRIER_LANE_MONTH_1);
        createCalendrierDesObjectifs.put("laneId_2", CALENDRIER_LANE_MONTH_2);
        createCalendrierDesObjectifs.put("laneId_3", CALENDRIER_LANE_MONTH_3);
        createCalendrierDesObjectifs.put("laneId_4", CALENDRIER_LANE_MONTH_4);
        createCalendrierDesObjectifs.put("laneId_5", CALENDRIER_LANE_MONTH_5);
        createCalendrierDesObjectifs.put("laneId_6", CALENDRIER_LANE_MONTH_6);
        return createCalendrierDesObjectifs;
    }

    private List<Map<String, Integer>> createCalendrierEnCoursDEstimation() {
        List<Map<String, Integer>> groupSchedule = new ArrayList<>();

        Map<String, Integer> andreSchedule = new HashMap<>();
        andreSchedule.put("boardId", BOARD_ESTIMATION);
        andreSchedule.put("main_lane", CAL_ANDRE_MAIN_LANE);
        andreSchedule.put("lane_week_1", CAL_ANDRE_SEM_1);
        andreSchedule.put("lane_week_2", CAL_ANDRE_SEM_2);
        andreSchedule.put("lane_week_3", CAL_ANDRE_SEM_3);
        andreSchedule.put("lane_week_4", CAL_ANDRE_SEM_4);

        groupSchedule.add(andreSchedule);

        Map<String, Integer> jessySchedule = new HashMap<>();
        jessySchedule.put("boardId", BOARD_ESTIMATION);
        jessySchedule.put("main_lane", CAL_JESSY_MAIN_LANE);
        jessySchedule.put("lane_week_1", CAL_JESSY_SEM_1);
        jessySchedule.put("lane_week_2", CAL_JESSY_SEM_2);
        jessySchedule.put("lane_week_3", CAL_JESSY_SEM_3);
        jessySchedule.put("lane_week_4", CAL_JESSY_SEM_4);

        groupSchedule.add(jessySchedule);

        Map<String, Integer> marioSchedule = new HashMap<>();
        marioSchedule.put("boardId", BOARD_ESTIMATION);
        marioSchedule.put("main_lane", CAL_MARIO_MAIN_LANE);
        marioSchedule.put("lane_week_1", CAL_MARIO_SEM_1);
        marioSchedule.put("lane_week_2", CAL_MARIO_SEM_2);
        marioSchedule.put("lane_week_3", CAL_MARIO_SEM_3);
        marioSchedule.put("lane_week_4", CAL_MARIO_SEM_4);

        groupSchedule.add(marioSchedule);

        Map<String, Integer> gaetanSchedule = new HashMap<>();
        gaetanSchedule.put("boardId", BOARD_ESTIMATION);
        gaetanSchedule.put("main_lane", CAL_GAETAN_MAIN_LANE);
        gaetanSchedule.put("lane_week_1", CAL_GAETAN_SEM_1);
        gaetanSchedule.put("lane_week_2", CAL_GAETAN_SEM_2);
        gaetanSchedule.put("lane_week_3", CAL_GAETAN_SEM_3);
        gaetanSchedule.put("lane_week_4", CAL_GAETAN_SEM_4);

        groupSchedule.add(gaetanSchedule);
        return groupSchedule;
    }

    public void setBoardEstimationLaneCalendrierEnCoursDEstimation() {
        try {
            List<String> dimancheSemaines = new ArrayList<>();

            // Obtenez une instance du calendrier actuel
            Calendar calendrier = Calendar.getInstance();

            // Obtenez le jour actuel de la semaine (dimanche = 1, lundi = 2, ..., samedi = 7)
            int jourDeLaSemaine = calendrier.get(Calendar.DAY_OF_WEEK);

            // Ajustez la semaine pour débuter à partir de la semaine actuelle
            calendrier.add(Calendar.DAY_OF_MONTH, 1 - jourDeLaSemaine);

            // Répétez le processus pour les 5 semaines
            for (int i = 0; i < 5; i++) {
                // Imprimez la date du dimanche de la semaine i en majuscules
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());
                String dateEnMajuscules = dateFormat.format(calendrier.getTime()).toUpperCase();
                dimancheSemaines.add(dateEnMajuscules);
                // Déplacez-vous à la première journée de la semaine suivante
                calendrier.add(Calendar.DAY_OF_MONTH, 7);
            }

            List<Map<String, Integer>> createCalendrierEnCoursDEstimation = createCalendrierEnCoursDEstimation();
            Lane laneTitleCompare = getInfoOfLane(
                    createCalendrierEnCoursDEstimation.getFirst().get("boardId"),
                    createCalendrierEnCoursDEstimation.getFirst().get("lane_week_1"));

            if (!dimancheSemaines.getFirst().equals(laneTitleCompare.getTitle())) {

                for (Map<String, Integer> scedule : createCalendrierEnCoursDEstimation) {
                    updateTitleAndDescriptionOfCalendrierEnCoursDEstimation(
                            scedule.get("boardId"),
                            scedule.get("main_lane"),
                            scedule.get("lane_week_1"),
                            scedule.get("lane_week_2"),
                            dimancheSemaines);
                    updateTitleAndDescriptionOfCalendrierEnCoursDEstimation(
                            scedule.get("boardId"),
                            scedule.get("main_lane"),
                            scedule.get("lane_week_2"),
                            scedule.get("lane_week_3"),
                            dimancheSemaines);
                    updateTitleAndDescriptionOfCalendrierEnCoursDEstimation(
                            scedule.get("boardId"),
                            scedule.get("main_lane"),
                            scedule.get("lane_week_3"),
                            scedule.get("lane_week_4"),
                            dimancheSemaines);
                }

            }
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans setBoardEstimationLaneCalendrierEnCoursDEstimation() " + e.getMessage());
        }
    }

    private void updateTitleAndDescriptionOfCalendrierEnCoursDEstimation(int boardId, int initialLane, int laneMoveTo, int laneWeek, List<String> dimancheSemaines) {
        try {
            Cards cards = getListOfCardsFromLane(initialLane);
            if (cards != null) {
                List<Integer> laneSemaine1 = Arrays.asList(CAL_ANDRE_SEM_1, CAL_JESSY_SEM_1, CAL_MARIO_SEM_1, CAL_GAETAN_SEM_1);
                List<Integer> laneSemaine2 = Arrays.asList(CAL_ANDRE_SEM_2, CAL_JESSY_SEM_2, CAL_MARIO_SEM_2, CAL_GAETAN_SEM_2);
                List<Integer> laneSemaine3 = Arrays.asList(CAL_ANDRE_SEM_3, CAL_JESSY_SEM_3, CAL_MARIO_SEM_3, CAL_GAETAN_SEM_3);
                List<Integer> laneSemaine4 = Arrays.asList(CAL_ANDRE_SEM_4, CAL_JESSY_SEM_4, CAL_MARIO_SEM_4, CAL_GAETAN_SEM_4);

                if (laneSemaine1.contains(laneMoveTo)) {
                    //System.out.println("Premiere condition");
                    Map<String, Object> propertiesToUpdate = setTitleAndDescriptionOfLane(dimancheSemaines.getFirst(), null);
                    updateALane(boardId, laneMoveTo, propertiesToUpdate);
                }
                if (laneSemaine2.contains(laneWeek)) {
                    //System.out.println("Deuxieme condition");
                    Map<String, Object> propertiesToUpdate = setTitleAndDescriptionOfLane(dimancheSemaines.get(1), null);
                    updateALane(boardId, laneWeek, propertiesToUpdate);
                }

                if (laneSemaine3.contains(laneWeek)) {
                    //System.out.println("Troisieme condition");
                    Map<String, Object> propertiesToUpdate = setTitleAndDescriptionOfLane(dimancheSemaines.get(2), null);
                    updateALane(boardId, laneWeek, propertiesToUpdate);
                }
                if (laneSemaine4.contains(laneWeek)) {
                    //System.out.println("Quatrieme condition");
                    Map<String, Object> propertiesToUpdate = setTitleAndDescriptionOfLane(dimancheSemaines.get(3), null);
                    updateALane(boardId, laneWeek, propertiesToUpdate);
                }

                if (cards != null) {
                    for (Card card : cards.getCards()) {
                        if (card.getLane().getId().equals(valueOf(laneWeek))) {
                            //System.out.println("Accept Condition");
                            moveCard(card.getId(), String.valueOf(laneMoveTo));
                        }
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans updateTitleAndDescriptionOfCalendrierEnCoursDEstimation() " + e.getMessage());
        }
    }

    private Map<String, Object> setTitleAndDescriptionOfLane(String newTitle, String newDescription) {
        Map<String, Object> propertiesToSet = new HashMap<>();
        propertiesToSet.put("title", newTitle);
        propertiesToSet.put("description", newDescription);
        return propertiesToSet;
    }

    public void verifieBoardEstimationLaneCalendrierDesObjectifs() {
        try {
            Map<String, Integer> createCalendrierDesObjectifs = createCalendrierDesObjectifs();
            Lane laneTitleCompare = getInfoOfLane(createCalendrierDesObjectifs.get("boardId"), createCalendrierDesObjectifs.get("laneId_1"));
            List<Semestre.Mois> listMonthCompare = moisAPartirDe(obtenirMoisActuel());
            LocalDate dateActuelle = LocalDate.now();
            if (!dateActuelle.getMonth().name().equals(currentMonth) && !laneTitleCompare.getTitle().equals(listMonthCompare.getFirst().name())) {
                currentMonth = dateActuelle.getMonth().name();
                updateLaneTitleAndDescriptionOfCalendrierDesObjectifs(createCalendrierDesObjectifs);
            }
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans verifieBoardEstimationLaneCalendrierDesObjectifs() " + e.getMessage());
        }
    }

    //Calendrier automatisation
    private void updateLaneTitleAndDescriptionOfCalendrierDesObjectifs(Map<String, Integer> createCalendrier) {
        try {
            List<Semestre.Mois> listMonth = moisAPartirDe(obtenirMoisActuel());
            Cards cards = getListOfCardsFromLane(createCalendrier.get("main_lane"));
            for (int i = 0; i < createCalendrier.size() - 1; i++) {
                Map<String, Object> propertiesToUpdate = setTitleAndDescriptionOfLane(String.valueOf(listMonth.get(i)), null);
                updateALane(createCalendrier.get("boardId"), createCalendrier.get("laneId_" + (i + 1)), propertiesToUpdate);
                for (Card card : cards.getCards()) {
                    int moveTo = (i + 5) % 6;
                    if (moveTo == 5) {
                        if (card.getLane().getId().equals(valueOf(createCalendrier.get("laneId_" + (i + 1))))) {
                            moveCard(card.getId(), valueOf(createCalendrier.get("archive_lane")));
                        }
                    }
                    if (moveTo != 5) {
                        if (card.getLane().getId().equals(valueOf(createCalendrier.get("laneId_" + (i + 1))))) {
                            moveCard(card.getId(), valueOf(createCalendrier.get("laneId_" + (moveTo + 1))));
                        }
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans updateLaneTitleAndDescriptionOfCalendrierDesObjectifs() " + e.getMessage());
        }
    }

    private AttachmentInfo getAListOfAttachementsForACard(int cardId) {
        Gson gson = new Gson();
        String stringAttachement = makeAPICall("/card/" + cardId + "/attachment/", "GET", null);
        return gson.fromJson(stringAttachement, AttachmentInfo.class);
    }

    private AttachmentInfo getAnAttachementsContentsForACard(int cardId, int attachmentId) {
        Gson gson = new Gson();
        String stringAttachement = makeAPICall("/card/" + cardId + "/attachment/" + attachmentId + "/content", "GET", null);
        return gson.fromJson(stringAttachement, AttachmentInfo.class);
    }

    private void createAnAttachment(int cardId, File file, String description) {
        try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("description", description)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .build();
            makeAPICall("/card/" + cardId + "/attachment", "POST", requestBody);
        } catch (Exception e) {
            System.out.println("Une exception spécifique s'est produite dans createAnAttachment() " + e.getMessage());
        }
    }

    private void deleteAnAttachment(int cardId, int attachmentId) {
        try {
            RequestBody requestBody = RequestBody.create(null, new byte[0]);
            makeAPICall("/card/" + cardId + "/attachment/" + attachmentId, "DELETE", requestBody);
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans deleteAnAttachment() " + e.getMessage());
        }
    }

    private void findDuplicateCard(List<Card> array, int laneArchive) {
        try {
            Set<String> uniqueElements = new HashSet<>();
            for (Card card : array) {
                if (!uniqueElements.add(card.getCustomId().getValue())) {
                    moveCard(card.getId(), String.valueOf(laneArchive));
                }
            }
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans findDuplicateCard() " + e.getMessage());
        }
    }

    public void findDuplicateCardInLanes() {
        try {
            int USINE_ADB_GERANCE_DE_PROJET_A_ATTRIBUER_LANE = 1897212305;
            int USINE_ADB_ARCHIVES_LANE = 1823767913;
            Cards laneCards = getListOfCardsFromLane(USINE_ADB_GERANCE_DE_PROJET_A_ATTRIBUER_LANE);
            findDuplicateCard(laneCards.getCards(), USINE_ADB_ARCHIVES_LANE);
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans findDuplicateCardInLanes() " + e.getMessage());
        }


    }

    public void updateAttachmentForMagasinCheckList() {
        System.out.println("updateAttachmentForMagasinCheckList");
        //envoyerSMS("5147788120","test salut");
    }

    public void reflectionOfBoardEstimationLainSuiviEstimationSylvain() {
        try {

            Cards suiviEstimationSylvainCards = getListOfCardsFromLane(BOARD_ESTIMATION_LANE_SUIVI_ESTIMATION_SYLVAIN);
            Cards crmSuiviSylvainCards = getListOfCardsFromLane(BOARD_CRM_LANE_SUIVI_ESTIMATION);
            List<String> costumIdJobs = new ArrayList<>();
            for (Card card : suiviEstimationSylvainCards.getCards()) {
                costumIdJobs.add(card.getCustomId().getValue());
            }
            for (Card card : crmSuiviSylvainCards.getCards()) {
                if (!costumIdJobs.contains(card.getCustomId().getValue())) {
                    moveCard(card.getId(), "1907499860");
                }
            }
        } catch (Exception e) {
            handleException(e);
            System.out.println("Une exception spécifique s'est produite dans reflectionOfBoardEstimationLainSuiviEstimationSylvain() " + e.getMessage());
        }
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
