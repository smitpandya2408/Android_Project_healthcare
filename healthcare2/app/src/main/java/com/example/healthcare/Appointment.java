package com.example.healthcare;

public class Appointment {

    private String fullName;
    private String address;
    private String contactNumber;
    private String fees;
    private String date;
    private String time;

    // Empty constructor required for Firestore
    public Appointment() {
    }

    public Appointment(String fullName, String address, String contactNumber, String fees, String date, String time) {
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.fees = fees;
        this.date = date;
        this.time = time;
    }

    // Getters and setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
