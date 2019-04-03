package com.example.techwheelsservicecentre;

class UserInfor {
    private String vehicleModel,regNo,Date,Time;
    public UserInfor(String vehiclemodel, String regno, String date, String time) {
        vehicleModel=vehiclemodel;
        regNo=regno;
        Date=date;
        Time=time;

    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }
}
