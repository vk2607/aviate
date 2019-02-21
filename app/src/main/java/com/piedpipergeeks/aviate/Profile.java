package com.piedpipergeeks.aviate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    private String userId;
    private String firstName, lastName, phoneNumber, aadharNumber, businessName, businessCategory, bio, businessDescription;
    private ArrayList<String> haves, wants, connections;
    private Map<String, String> dob;

    public Profile() {
        haves = new ArrayList<>();
        wants = new ArrayList<>();
        connections = new ArrayList<>();
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

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
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

    public ArrayList<String> getConnections() {
        return connections;
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

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public Map<String, String> getDob() {
        return dob;
    }

    public String getBio() {
        return bio;
    }

    public String getUserId() {
        return userId;
    }

    public String getBusinessDescription() {
        return businessDescription;
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
        profile.put("businessName", businessName);
        profile.put("businessCategory", businessCategory);
        profile.put("businessDescription", businessDescription);
        profile.put("bio", bio);
        profile.put("haves", haves);
        profile.put("wants", wants);
        profile.put("connections", connections);
    }

}
