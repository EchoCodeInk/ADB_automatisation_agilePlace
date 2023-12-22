package model;

public class Event {
    private String id;
    private String type;
    private String timestamp;
    private AssignedUser user;
    private EventData data;

    public Event(String id, String type, String timestamp, AssignedUser user, EventData data) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.user = user;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public AssignedUser getUser() {
        return user;
    }

    public void setUser(AssignedUser user) {
        this.user = user;
    }

    public EventData getData() {
        return data;
    }

    public void setData(EventData data) {
        this.data = data;
    }
}
