package Model;

public class AssignedUser {
    private String id;
    private String emailAddress;
    private String fullName;
    private String firstName;
    private String lastName;
    private boolean hasAccess;
    private String avatar;

    public AssignedUser(String id, String emailAddress, String fullName, String firstName, String lastName, boolean hasAccess, String avatar) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hasAccess = hasAccess;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
