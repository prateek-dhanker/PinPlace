package com.example.pinplace;

public class Location {
    private double latitude, longitude;
    private String name, description;

    Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    Location(double latitude, double longitude,String name){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }
    Location(double latitude, double longitude,String name, String description){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
