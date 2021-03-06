package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    private String userId, userType;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String aadharNumber;
    private String email;
    private String businessCategory;
    private String cityName;
    private Boolean emailVerified;
    private ArrayList<String> haves, wants, clubMember, clubSecretary, clubAdmin, clubGuest;
    private Map<String, String> dob;

        public void setClubMember(ArrayList<String> clubMember) {
        this.clubMember = clubMember;
    }

    public void setClubAdmin(ArrayList<String> clubAdmin) {
        this.clubAdmin = clubAdmin;
    }

    public void setClubGuest(ArrayList<String> clubGuest) {
        this.clubGuest = clubGuest;
    }

    public void setClubSecretary(ArrayList<String> clubSecretary) {
        this.clubSecretary = clubSecretary;
    }

    public ArrayList<String> getClubAdmin() {
        return clubAdmin;
    }

    public ArrayList<String> getClubGuest() {
        return clubGuest;
    }

    public ArrayList<String> getClubMember() {
        return clubMember;
    }

    public ArrayList<String> getClubSecretary() {
        return clubSecretary;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


//    public static String USER_ID, USER_TYPE, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER;
//    private static SharedPreferences pref;

//    public static void saveDataLocally(Context context, Profile user) {
//        initPreferences(context);
//
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("userId", user.getUserId());
//        editor.putString("userType", user.getUserType());
//        editor.putString("firstName", user.getFirstName());
//        editor.putString("lastName", user.getLastName());
//        editor.putString("email", user.getEmail());
//        editor.putString("phoneNumber", user.getPhoneNumber());
//        editor.apply();
//    }
//
//    private static void initPreferences(Context context) {
//        pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
//    }
//
//    public String USER_ID() {
//        pref.
//    }


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

    public Boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
