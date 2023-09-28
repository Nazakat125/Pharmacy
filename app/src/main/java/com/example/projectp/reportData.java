package com.example.projectp;

public class reportData {
    private String reportSubject;
    private String reportMessage;

    public reportData(String reportSubject, String reportMessage) {
        this.reportSubject = reportSubject;
        this.reportMessage = reportMessage;
    }

    public String getReportSubject() {
        return reportSubject;
    }

    public String getReportMessage() {
        return reportMessage;
    }
}
