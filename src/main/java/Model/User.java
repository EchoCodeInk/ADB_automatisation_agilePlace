package model;


import java.util.List;

public class User {
        private String id;
        private String username;
        private String firstName;
        private String lastName;
        private String fullName;
        private String emailAddress;
        private String lastAccess;
        private String dateFormat;
        private boolean administrator;
        private boolean enabled;
        private boolean deleted;
        private String organizationId;
        private boolean boardCreator;
        private String timeZone;
        private String licenseType;
        private String externalUserName;
        private String avatar;
        private Setting settings;
        private List<BoardRole> boardRoles;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getLastAccess() {
            return lastAccess;
        }

        public void setLastAccess(String lastAccess) {
            this.lastAccess = lastAccess;
        }

        public String getDateFormat() {
            return dateFormat;
        }

        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public boolean isAdministrator() {
            return administrator;
        }

        public void setAdministrator(boolean administrator) {
            this.administrator = administrator;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public boolean isBoardCreator() {
            return boardCreator;
        }

        public void setBoardCreator(boolean boardCreator) {
            this.boardCreator = boardCreator;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getLicenseType() {
            return licenseType;
        }

        public void setLicenseType(String licenseType) {
            this.licenseType = licenseType;
        }

        public String getExternalUserName() {
            return externalUserName;
        }

        public void setExternalUserName(String externalUserName) {
            this.externalUserName = externalUserName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Setting getSettings() {
            return settings;
        }

        public void setSettings(Setting settings) {
            this.settings = settings;
        }

        public List<BoardRole> getBoardRoles() {
            return boardRoles;
        }

        public void setBoardRoles(List<BoardRole> boardRoles) {
            this.boardRoles = boardRoles;
        }
    }

