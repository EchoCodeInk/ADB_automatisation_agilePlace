package model;

import java.util.List;

public class BoardDetails {

    private List<User> users;
    private List<ClassOfService> classesOfServices;
    private List<String> tags;
    private List<CustomField> customFields;
    private String id;
    private String title;
    private String description;
    private String creationDate;
    private boolean classOfServiceEnabled;
    private String customIconFieldLabel;
    private String organizationId;
    private String version;
    private int cardColorField;
    private boolean isCardIdEnabled;
    private boolean isHeaderEnabled;
    private boolean isHyperlinkEnabled;
    private boolean isPrefixEnabled;
    private String prefix;
    private String format;
    private boolean isPrefixIncludedInHyperlink;
    private boolean baseWipOnCardSize;
    private boolean excludeCompletedAndArchiveViolations;
    private boolean isDuplicateCardIdAllowed;
    private boolean isAutoIncrementCardIdEnabled;
    private String currentExternalCardId;
    private boolean isWelcome;
    private boolean isShared;
    private boolean isArchived;
    private String sharedBoardRole;
    private String customBoardMoniker;
    private boolean isPermalinkEnabled;
    private boolean isExternalUrlEnabled;
    private boolean allowUsersToDeleteCards;
    private boolean allowPlanviewIntegration;
    private String subscriptionId;
    private String boardRole;
    private String effectiveBoardRole;
    private List<CardType> cardTypes;
    private List<LaneType> laneClassTypes;
    private List<Lane> lanes;
    private List<LaneType> laneTypes;
    private Object userSettings;
    private List<Priority> priorities;
    private List<PlanningSerie> planningSeries;
    private String layoutChecksum;
    private String defaultCardTypeId;
    private String defaultTaskTypeId;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<ClassOfService> getClassesOfServices() {
        return classesOfServices;
    }

    public void setClassesOfServices(List<ClassOfService> classesOfServices) {
        this.classesOfServices = classesOfServices;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public String getId() {
        return id;
    }

    public Object getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(Object userSettings) {
        this.userSettings = userSettings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isClassOfServiceEnabled() {
        return classOfServiceEnabled;
    }

    public void setClassOfServiceEnabled(boolean classOfServiceEnabled) {
        this.classOfServiceEnabled = classOfServiceEnabled;
    }

    public String getCustomIconFieldLabel() {
        return customIconFieldLabel;
    }

    public void setCustomIconFieldLabel(String customIconFieldLabel) {
        this.customIconFieldLabel = customIconFieldLabel;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCardColorField() {
        return cardColorField;
    }

    public void setCardColorField(int cardColorField) {
        this.cardColorField = cardColorField;
    }

    public boolean isCardIdEnabled() {
        return isCardIdEnabled;
    }

    public void setCardIdEnabled(boolean cardIdEnabled) {
        isCardIdEnabled = cardIdEnabled;
    }

    public boolean isHeaderEnabled() {
        return isHeaderEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        isHeaderEnabled = headerEnabled;
    }

    public boolean isHyperlinkEnabled() {
        return isHyperlinkEnabled;
    }

    public void setHyperlinkEnabled(boolean hyperlinkEnabled) {
        isHyperlinkEnabled = hyperlinkEnabled;
    }

    public boolean isPrefixEnabled() {
        return isPrefixEnabled;
    }

    public void setPrefixEnabled(boolean prefixEnabled) {
        isPrefixEnabled = prefixEnabled;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isPrefixIncludedInHyperlink() {
        return isPrefixIncludedInHyperlink;
    }

    public void setPrefixIncludedInHyperlink(boolean prefixIncludedInHyperlink) {
        isPrefixIncludedInHyperlink = prefixIncludedInHyperlink;
    }

    public boolean isBaseWipOnCardSize() {
        return baseWipOnCardSize;
    }

    public void setBaseWipOnCardSize(boolean baseWipOnCardSize) {
        this.baseWipOnCardSize = baseWipOnCardSize;
    }

    public boolean isExcludeCompletedAndArchiveViolations() {
        return excludeCompletedAndArchiveViolations;
    }

    public void setExcludeCompletedAndArchiveViolations(boolean excludeCompletedAndArchiveViolations) {
        this.excludeCompletedAndArchiveViolations = excludeCompletedAndArchiveViolations;
    }

    public boolean isDuplicateCardIdAllowed() {
        return isDuplicateCardIdAllowed;
    }

    public void setDuplicateCardIdAllowed(boolean duplicateCardIdAllowed) {
        isDuplicateCardIdAllowed = duplicateCardIdAllowed;
    }

    public boolean isAutoIncrementCardIdEnabled() {
        return isAutoIncrementCardIdEnabled;
    }

    public void setAutoIncrementCardIdEnabled(boolean autoIncrementCardIdEnabled) {
        isAutoIncrementCardIdEnabled = autoIncrementCardIdEnabled;
    }

    public String getCurrentExternalCardId() {
        return currentExternalCardId;
    }

    public void setCurrentExternalCardId(String currentExternalCardId) {
        this.currentExternalCardId = currentExternalCardId;
    }

    public boolean isWelcome() {
        return isWelcome;
    }

    public void setWelcome(boolean welcome) {
        isWelcome = welcome;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getSharedBoardRole() {
        return sharedBoardRole;
    }

    public void setSharedBoardRole(String sharedBoardRole) {
        this.sharedBoardRole = sharedBoardRole;
    }

    public String getCustomBoardMoniker() {
        return customBoardMoniker;
    }

    public void setCustomBoardMoniker(String customBoardMoniker) {
        this.customBoardMoniker = customBoardMoniker;
    }

    public boolean isPermalinkEnabled() {
        return isPermalinkEnabled;
    }

    public void setPermalinkEnabled(boolean permalinkEnabled) {
        isPermalinkEnabled = permalinkEnabled;
    }

    public boolean isExternalUrlEnabled() {
        return isExternalUrlEnabled;
    }

    public void setExternalUrlEnabled(boolean externalUrlEnabled) {
        isExternalUrlEnabled = externalUrlEnabled;
    }

    public boolean isAllowUsersToDeleteCards() {
        return allowUsersToDeleteCards;
    }

    public void setAllowUsersToDeleteCards(boolean allowUsersToDeleteCards) {
        this.allowUsersToDeleteCards = allowUsersToDeleteCards;
    }

    public boolean isAllowPlanviewIntegration() {
        return allowPlanviewIntegration;
    }

    public void setAllowPlanviewIntegration(boolean allowPlanviewIntegration) {
        this.allowPlanviewIntegration = allowPlanviewIntegration;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getBoardRole() {
        return boardRole;
    }

    public void setBoardRole(String boardRole) {
        this.boardRole = boardRole;
    }

    public String getEffectiveBoardRole() {
        return effectiveBoardRole;
    }

    public void setEffectiveBoardRole(String effectiveBoardRole) {
        this.effectiveBoardRole = effectiveBoardRole;
    }

    public List<CardType> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(List<CardType> cardTypes) {
        this.cardTypes = cardTypes;
    }

    public List<LaneType> getLaneClassTypes() {
        return laneClassTypes;
    }

    public void setLaneClassTypes(List<LaneType> laneClassTypes) {
        this.laneClassTypes = laneClassTypes;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public List<LaneType> getLaneTypes() {
        return laneTypes;
    }

    public void setLaneTypes(List<LaneType> laneTypes) {
        this.laneTypes = laneTypes;
    }




    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public List<PlanningSerie> getPlanningSeries() {
        return planningSeries;
    }

    public void setPlanningSeries(List<PlanningSerie> planningSeries) {
        this.planningSeries = planningSeries;
    }

    public String getLayoutChecksum() {
        return layoutChecksum;
    }

    public void setLayoutChecksum(String layoutChecksum) {
        this.layoutChecksum = layoutChecksum;
    }

    public String getDefaultCardTypeId() {
        return defaultCardTypeId;
    }

    public void setDefaultCardTypeId(String defaultCardTypeId) {
        this.defaultCardTypeId = defaultCardTypeId;
    }

    public String getDefaultTaskTypeId() {
        return defaultTaskTypeId;
    }

    public void setDefaultTaskTypeId(String defaultTaskTypeId) {
        this.defaultTaskTypeId = defaultTaskTypeId;
    }
}
