package model;

public class ParentCard {
    private String cardId;
    private String boardId;

    public ParentCard(String cardId, String boardId) {
        this.cardId = cardId;
        this.boardId = boardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
