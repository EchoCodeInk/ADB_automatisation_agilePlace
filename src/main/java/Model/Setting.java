package model;

import java.util.List;

public class Setting {
    private boolean useMondayForCalendarWeekViewStart;
    private String avatarBounds;
    private List<Integer> recentBoards;

    public boolean isUseMondayForCalendarWeekViewStart() {
        return useMondayForCalendarWeekViewStart;
    }

    public void setUseMondayForCalendarWeekViewStart(boolean useMondayForCalendarWeekViewStart) {
        this.useMondayForCalendarWeekViewStart = useMondayForCalendarWeekViewStart;
    }

    public String getAvatarBounds() {
        return avatarBounds;
    }

    public void setAvatarBounds(String avatarBounds) {
        this.avatarBounds = avatarBounds;
    }

    public List<Integer> getRecentBoards() {
        return recentBoards;
    }

    public void setRecentBoards(List<Integer> recentBoards) {
        this.recentBoards = recentBoards;
    }
}
