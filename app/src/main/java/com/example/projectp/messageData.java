package com.example.projectp;
public class messageData {
    private String message;
    private Boolean isSend;
    private String time;
    private String name;

    public messageData(String message, Boolean isSend, String time, String name) {
        this.message = message;
        this.isSend = isSend;
        this.time = time;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

