package controller;

import model.Card;
import api.APIClient;
import model.Cards;

public class APIClientController {
    private APIClient apiClient;

    public APIClientController() {
        this.apiClient = new APIClient();
    }

    public void moveChildsCardFromBoardEstimation() {
        apiClient.moveChildsCardFromBoardEstimation();
    }
    public void createCard_boardUsineADB() {
        apiClient.createCard_boardUsineADB();
    }




}
