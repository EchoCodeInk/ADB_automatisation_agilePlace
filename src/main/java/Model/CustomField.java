package Model;

public class CustomField {
    private String fieldId;
    private String value;

    public CustomField(String fieldId, String value) {
        this.fieldId = fieldId;
        this.value = value;
    }

    // Getters et setters
    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
