package model;

public class CustomFieldOperation {
    private String op;
    private String path;
    private CustomFieldValue value;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public CustomFieldValue getValue() {
        return value;
    }

    public void setValue(CustomFieldValue value) {
        this.value = value;
    }
}
