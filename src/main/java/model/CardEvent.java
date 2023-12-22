package model;

import java.util.List;

public class CardEvent {
    private List<Event> events;

    public CardEvent(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
