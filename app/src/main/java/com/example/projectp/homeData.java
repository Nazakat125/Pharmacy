package com.example.projectp;

public class homeData {
    private String medicineName;
    private String quantity;

    public homeData(String medicineName, String quantity) {
        this.medicineName = medicineName;
        this.quantity = quantity;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getQuantity() {
        return quantity;
    }
}
