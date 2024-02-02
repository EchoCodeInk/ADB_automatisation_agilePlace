package model;

public class Lane {
    private int cardLimit;
    private String description;
    private String id;
    private String name;
    private boolean active;
    private boolean isConnectionDoneLane;
    private boolean isDefaultDropLane;
    private boolean isCollapsed;
    private int index;
    private int columns;
    private int wipLimit;
    private int cardCount;
    private int cardSize;
    private String laneClassType;
    private String laneType;
    private String orientation;
    private String parentLaneId;
    private String activityId;
    private String title;
    private String taskBoard;
    private String cardStatus;
    private String boardId;
    private String type;
    private String sortBy;
    private String creationDate;
    private int archiveCardCount;
    private String subscriptionId;


    // Getter and Setter
    public String getBoardId() {
        return boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isConnectionDoneLane() {
        return isConnectionDoneLane;
    }

    public void setConnectionDoneLane(boolean connectionDoneLane) {
        isConnectionDoneLane = connectionDoneLane;
    }

    public boolean isDefaultDropLane() {
        return isDefaultDropLane;
    }

    public void setDefaultDropLane(boolean defaultDropLane) {
        isDefaultDropLane = defaultDropLane;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int getCardSize() {
        return cardSize;
    }

    public void setCardSize(int cardSize) {
        this.cardSize = cardSize;
    }

    public String getParentLaneId() {
        return parentLaneId;
    }

    public void setParentLaneId(String parentLaneId) {
        this.parentLaneId = parentLaneId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getArchiveCardCount() {
        return archiveCardCount;
    }

    public void setArchiveCardCount(int archiveCardCount) {
        this.archiveCardCount = archiveCardCount;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
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

