package model;

public class Lane {
    private int cardLimit;
    private String description;
    private String id;
    private int index;
    private String laneClassType;
    private String laneType;
    private String orientation;
    private String title;
    private String taskBoard;
    private String cardStatus;

    // Constructeur

    public Lane(int cardLimit, String description, String id, int index, String laneClassType, String laneType, String orientation, String title, String taskBoard, String cardStatus) {
        this.cardLimit = cardLimit;
        this.description = description;
        this.id = id;
        this.index = index;
        this.laneClassType = laneClassType;
        this.laneType = laneType;
        this.orientation = orientation;
        this.title = title;
        this.taskBoard = taskBoard;
        this.cardStatus = cardStatus;
    }


    // Getters et Setters pour chaque attribut

    public int getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(int cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLaneClassType() {
        return laneClassType;
    }

    public void setLaneClassType(String laneClassType) {
        this.laneClassType = laneClassType;
    }

    public String getLaneType() {
        return laneType;
    }

    public void setLaneType(String laneType) {
        this.laneType = laneType;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskBoard() {
        return taskBoard;
    }

    public void setTaskBoard(String taskBoard) {
        this.taskBoard = taskBoard;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}

