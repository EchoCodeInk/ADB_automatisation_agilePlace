package model;

public class BoardRole {
    private String boardId;
    private Integer WIP;
    private Role role;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Integer getWIP() {
        return WIP;
    }

    public void setWIP(Integer WIP) {
        this.WIP = WIP;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
