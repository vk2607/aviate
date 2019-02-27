package com.piedpipergeeks.aviate;

import java.util.ArrayList;

public class Groups {
    private String admins, president, secretary;
    private ArrayList<Profile> guests, members;
    private String name;

    public Groups() {
        guests = new ArrayList<>();
        members = new ArrayList<>();
    }

    public String getAdmins() {
        return admins;
    }

    public void setAdmins(String admins) {
        this.admins = admins;
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

    public ArrayList<Profile> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<Profile> guests) {
        this.guests = guests;
    }

    public ArrayList<Profile> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Profile> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
