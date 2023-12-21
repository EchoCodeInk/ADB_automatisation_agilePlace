package model;

import java.lang.reflect.Array;
import java.util.List;

public class Card {

    private String id;
    private Board board;
    private String title;
    private  String version;
    private Type type;
    private  List<AssignedUser> assignedUsers;
    private String description;
    private int size;
    private Lane lane;
    private List<ParentCard> parentCards;
    private String mirrorSourceCardId;
    private String copiedFromCardId;
    private String blockReason;
    private boolean isBlocked;
    private String priority;
    private String customIconId;
    private CustomId customId;
    private ExternalLink externalLink;
    private int index;
    private String plannedStart;
    private String plannedFinish;
    private List<String> tags;
    private String wipOverrideComment;
    private List<CustomField> customFields;
    private List<String> planningIncrementIds;


    // Constructeur minimal
    public Card(Board board , String title) {
        this.board = board;
        this.title = title;

    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

    public void setId(String id) {
        this.id = id;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public  List<AssignedUser> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers( List<AssignedUser> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public List<ParentCard> getParentCards() {
        return parentCards;
    }

    public void setParentCards(List<ParentCard> parentCards) {
        this.parentCards = parentCards;
    }

    public String getMirrorSourceCardId() {
        return mirrorSourceCardId;
    }

    public void setMirrorSourceCardId(String mirrorSourceCardId) {
        this.mirrorSourceCardId = mirrorSourceCardId;
    }

    public String getCopiedFromCardId() {
        return copiedFromCardId;
    }

    public void setCopiedFromCardId(String copiedFromCardId) {
        this.copiedFromCardId = copiedFromCardId;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCustomIconId() {
        return customIconId;
    }

    public void setCustomIconId(String customIconId) {
        this.customIconId = customIconId;
    }

    public CustomId getCustomId() {
        return customId;
    }

    public void setCustomId(CustomId customId) {
        this.customId = customId;
    }

    public ExternalLink getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(ExternalLink externalLink) {
        this.externalLink = externalLink;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(String plannedStart) {
        this.plannedStart = plannedStart;
    }

    public String getPlannedFinish() {
        return plannedFinish;
    }

    public void setPlannedFinish(String plannedFinish) {
        this.plannedFinish = plannedFinish;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getWipOverrideComment() {
        return wipOverrideComment;
    }

    public void setWipOverrideComment(String wipOverrideComment) {
        this.wipOverrideComment = wipOverrideComment;
    }


    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public List<String> getPlanningIncrementIds() {
        return planningIncrementIds;
    }

    public void setPlanningIncrementIds(List<String> planningIncrementIds) {
        this.planningIncrementIds = planningIncrementIds;
    }
}
