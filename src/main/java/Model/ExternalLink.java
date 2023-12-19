package Model;

public class ExternalLink {
    private String label;
    private String url;

    public ExternalLink(String label, String url) {
        this.label = label;
        this.url = url;
    }

    // Getters et setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
