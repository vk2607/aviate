package com.piedpipergeeks.aviate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Club {
    private String info, name, president, secretary, clubId;
    private String presidentName, secretaryName;
    private ArrayList<String> admins, members, guests;
    private Map<String, Object> adminNames, memberNames, guestNames;

    public Club() {
        admins = new ArrayList<>();
        adminNames = new HashMap<>();

        members = new ArrayList<>();
        memberNames = new HashMap<>();

        guests = new ArrayList<>();
        guestNames = new HashMap<>();
    }

    public String getPresidentName() {
        return presidentName;
    }

    public String getSecretaryName() {
        return secretaryName;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<String> getAdmins() {
        return admins;
    }

    public void addAdmin(String adminId, String adminName) {
        admins.add(adminId);
        adminNames.put(adminId, adminName);
    }

    public Map<String, Object> getAdminNames() {
        return adminNames;
    }

    public Map<String, Object> getGuestNames() {
        return guestNames;
    }

    public Map<String, Object> getMemberNames() {
        return memberNames;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String presidentId, String presidentName) {
        this.president = presidentId;
        this.presidentName = presidentName;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretaryId, String secretaryName) {
        this.secretary = secretaryId;
        this.secretaryName = secretaryName;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void addMember(String memberName) {
        members.add(memberName);
    }

    public ArrayList<String> getGuests() {
        return guests;
    }

    public void addGuest(String guestName) {
        guests.add(guestName);
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("clubId", clubId);
        map.put("name", name);
        map.put("info", info);
        map.put("president", president);
        map.put("secretary", secretary);
        map.put("admins", admins);
        map.put("adminNames", adminNames);
        map.put("members", members);
        map.put("memberName", memberNames);
        map.put("guests", guests);
        map.put("guestNames", guestNames);

        return map;
    }
}
