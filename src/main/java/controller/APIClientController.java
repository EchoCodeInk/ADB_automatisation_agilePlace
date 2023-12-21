package controller;

import model.Card;
import api.APIClient;
import model.Cards;

public class APIClientController {
    private APIClient apiClient;

    public APIClientController() {
        this.apiClient = new APIClient();
    }

    public Card getInfoCardFromApiClient(String cardId) {
        return apiClient.getInfoCard(cardId);
    }

    public void moveCardFromApiClient(String cardId, String newLaneLocationId) {
        apiClient.moveCard(cardId, newLaneLocationId);
    }
    public void updateCardFromApiClient(Card card) {
        apiClient.updateCard(card);
    }
//    public String getListOfCardsFromApiClient(String boardId) {
//        int parsedBoardId = Integer.parseInt(boardId);
//        return apiClient.getListOfCards(parsedBoardId);
//    }
    public void moveChildsCardFromBoardEstimation() {
        apiClient.moveChildsCardFromBoardEstimation();
    }
    public Cards getListOfCardsFromLineBoard(int lineIds) {
        return  apiClient.getListOfCardsFromLineBoard(lineIds);
    }



}
