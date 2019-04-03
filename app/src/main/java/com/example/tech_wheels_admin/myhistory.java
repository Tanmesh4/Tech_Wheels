package com.example.tech_wheels_admin;

public class myhistory {

    private String timeslot;
    private String email;
    private String date;
    private String regno;
    private String vehicleModel;
    private String timestamp;
    private String status;

    public myhistory(String timeslot, String email, String date, String regno, String vehicleModel, String timestamp, String status) {
        this.timeslot = timeslot;
        this.email = email;
        this.date = date;
        this.regno = regno;
        this.vehicleModel = vehicleModel;
        this.timestamp = timestamp;
        this.status=status;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getRegno() {
        return regno;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus()
    {

        return status;
    }
}
