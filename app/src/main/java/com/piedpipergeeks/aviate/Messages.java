package com.piedpipergeeks.aviate;

import java.sql.Timestamp;
import java.util.Date;

public class Messages {
    private String text;
    private Profile sender;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return text;
    }

    public void setMessage(String message) {
        this.text = message;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }


}
