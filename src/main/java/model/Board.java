package model;

public class Board {
    private String id;
    private String title;
    private String version;
    private boolean isArchived;

    public Board(String id, String title, String version, boolean isArchived) {
        this.id = id;
        this.title = title;
        this.version = version;
        this.isArchived = isArchived;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
