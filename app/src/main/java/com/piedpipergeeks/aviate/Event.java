package com.piedpipergeeks.aviate;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Event {
    private Timestamp timestamp;
    private String agenda, momLink, clubName;
    private ArrayList<String> needUsers;
    private Map<String, Object> needs;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ArrayList<String> getNeedUsers() {
        return needUsers;
    }

    public Map<String, Object> getNeeds() {
        return needs;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getMomLink() {
        return momLink;
    }

    public String getClubName() {
        return clubName;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public void setMomLink(String momLink) {
        this.momLink = momLink;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void addNeedUsers(String userId) {
        this.needUsers.add(userId);
    }

    public void addNeed(String userId, String text) {
        addNeedUsers(userId);
        Map<String, Object> need = new HashMap<>();
        need.put("text", text);
        needs.put(userId, need);
    }

    public void addReplyToNeed(String needUserId, String repliedUserId, String text) {
        Map<String, Object> need = (HashMap<String, Object>) needs.get(needUserId);
        if (need.containsKey("replies")) {

            if (!((ArrayList<String>) need.get("repliedUsers")).contains(repliedUserId)) {
                ((ArrayList<String>) need.get("repliedUsers")).add(repliedUserId);
            }
            ((HashMap<String, Object>) need.get("replies")).put(repliedUserId, text);

        }
    }

    public Map<String, Object> getMap() {
        Map<String, Object> event = new HashMap<>();
        event.put("timestamp", timestamp);
        event.put("agenda", agenda);
        event.put("momLink", momLink);
        event.put("clubName", clubName);
        event.put("needUsers", needUsers);
        event.put("needs", needs);
        return event;
    }

}
