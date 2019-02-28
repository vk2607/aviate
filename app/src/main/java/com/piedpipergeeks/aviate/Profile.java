package com.piedpipergeeks.aviate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    private String userId;
    private String firstName, lastName, phoneNumber, aadharNumber, email;
    private ArrayList<String> haves, wants;
    private Map<String, String> dob;

    public Profile() {
        haves = new ArrayList<>();
        wants = new ArrayList<>();
    }

    public void setDob(String date, String month, String year) {
        Map<String, String> dob = new HashMap<>();
        dob.put("date", date);
        dob.put("month", month);
        dob.put("year", year);
        this.dob = dob;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String firstName) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addHaves(String have) {
        haves.add(have);
    }

    public void addWants(String want) {
        wants.add(want);
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getHaves() {
        return haves;
    }

    public ArrayList<String> getWants() {
        return wants;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<String, String> getDob() {
        return dob;
    }

    public String getUserId() {
        return userId;
    }


    public void saveDataLocally() {
        //TO DO USING SHARED PREFERENCES
    }

    public void getMap() {
        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", userId);
        profile.put("firstName", firstName);
        profile.put("lastName", lastName);
        profile.put("phoneNumber", phoneNumber);
        profile.put("aadharNumber", aadharNumber);
        profile.put("haves", haves);
        profile.put("wants", wants);
    }

}
