package model;

import java.util.List;

public class CardList {
    private List<Card> cards;

    public CardList(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
