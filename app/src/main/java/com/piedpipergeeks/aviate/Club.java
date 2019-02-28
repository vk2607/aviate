package com.piedpipergeeks.aviate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Club {
    private String info, name, president, secretary, clubId;
    private ArrayList<String> admins, members, guests;

    public Club() {
        admins = new ArrayList<>();
        members = new ArrayList<>();
        guests = new ArrayList<>();
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

    public void addAdmin(String adminName) {
        admins.add(adminName);
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
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
        map.put("members", members);
        map.put("guests", guests);

        return map;
    }
}
