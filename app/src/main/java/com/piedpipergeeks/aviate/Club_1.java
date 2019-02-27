package com.piedpipergeeks.aviate;

import java.util.HashMap;
import java.util.Map;

public class Club_1 {
    private String clubname;

    public void setClubname(String clubname){this.clubname=clubname;}

    public String getClubname(){return clubname;}

    public void getMap(){
        Map<String,Object> Club=new HashMap<>();
        Club.put("clubname",clubname);
    }
}
