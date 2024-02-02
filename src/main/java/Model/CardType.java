package model;

public class CardType {
    private String id;
    private String name;
    private String colorHex;
    private boolean isCardType;
    private boolean isTaskType;
    private boolean isDefaultTaskType;

    public String getId() {
        return id;
    }

    public boolean isDefaultTaskType() {
        return isDefaultTaskType;
    }

    public void setDefaultTaskType(boolean defaultTaskType) {
        isDefaultTaskType = defaultTaskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public boolean isCardType() {
        return isCardType;
    }

    public void setCardType(boolean cardType) {
        isCardType = cardType;
    }

    public boolean isTaskType() {
        return isTaskType;
    }

    public void setTaskType(boolean taskType) {
        isTaskType = taskType;
    }
}
