package model;

public class Type {
    private String id;
    private String title;
    private String cardColor;

    public Type(String id, String title, String cardColor) {
        this.id = id;
        this.title = title;
        this.cardColor = cardColor;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }
}
