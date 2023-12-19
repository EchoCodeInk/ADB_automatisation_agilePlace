package Model;

public class CustomId {
    private String value;
    private String prefix;
    private String url;


    public CustomId(String value, String prefix, String url) {
        this.value = value;
        this.prefix = prefix;
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
