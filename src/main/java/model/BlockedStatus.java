package model;

public class BlockedStatus {
    private boolean isBlocked;
    private String reason;
    private String date;

    public BlockedStatus(boolean isBlocked, String reason, String date) {
        this.isBlocked = isBlocked;
        this.reason = reason;
        this.date = date;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
