package model;

public class EventData {
    private Card card;
    private Lane fromLane;
    private Lane toLane;

    public EventData(Card card, Lane fromLane, Lane toLane) {
        this.card = card;
        this.fromLane = fromLane;
        this.toLane = toLane;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Lane getFromLane() {
        return fromLane;
    }

    public void setFromLane(Lane fromLane) {
        this.fromLane = fromLane;
    }

    public Lane getToLane() {
        return toLane;
    }

    public void setToLane(Lane toLane) {
        this.toLane = toLane;
    }
}
