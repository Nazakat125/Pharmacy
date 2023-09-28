package com.example.projectp;

public class AgentData {
    private String name;
    private String subject;
    private String message;

    public AgentData(String name, String subject, String message) {
        this.name = name;
        this.subject = subject;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}

