package com.piedpipergeeks.aviate;

import java.util.ArrayList;

public class Club {
    private String info, name, president, secretary;
    private ArrayList<String> admins, members, guests;

    public Club () {
        admins=new ArrayList<>();
        members=new ArrayList<>();
        guests=new ArrayList<>();
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
    public void addMember(String memberName)
    {
        members.add(memberName);
    }

    public ArrayList<String> getGuests() {
        return guests;
    }
    public void addGuest (String guestName)
    {
        guests.add(guestName);
    }
}
