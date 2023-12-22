package controller;

import api.AgilePlaceClient;

public class AgilePlaceClientController {
    private AgilePlaceClient apiClient;

   AgilePlaceClientController() {
        this.apiClient = new AgilePlaceClient();
    }

    protected void moveChildsCardFromBoardEstimation() {
        apiClient.moveChildsCardFromBoardEstimation();
    }
    protected void createCard_boardUsineADB() {
        apiClient.createCard_boardUsineADB();
    }




}
