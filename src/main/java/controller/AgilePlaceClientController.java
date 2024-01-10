package controller;

import api.AgilePlaceClient;

import java.util.concurrent.ScheduledExecutorService;

public class AgilePlaceClientController {
    private AgilePlaceClient apiClient;
    private ScheduledExecutorService scheduler;

   AgilePlaceClientController() {
        this.apiClient = new AgilePlaceClient();
    }

    protected void moveCardOfBoardEstimationOfTheLaneSylvain() {
        apiClient.moveCardOfBoardEstimationOfTheLaneSylvain();
    }

    protected void calenderAutomate() {
        apiClient.calenderAutomate();
    }

}
