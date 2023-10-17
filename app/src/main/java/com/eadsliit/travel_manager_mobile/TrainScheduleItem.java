package com.eadsliit.travel_manager_mobile;

public class TrainScheduleItem {

    private String trainId;
    private String name;
    private String startStation;
    private String endStations;
    private String departureTime;
    private String arrivalTime;
    private String isActive;

    public TrainScheduleItem(String trainId, String name, String startStation, String endStations, String departureTime, String arrivalTime, String isActive) {
        this.trainId = trainId;
        this.name = name;
        this.startStation = startStation;
        this.endStations = endStations;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.isActive = isActive;
    }

    public TrainScheduleItem() {

    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStations() {
        return endStations;
    }

    public void setEndStations(String endStations) {
        this.endStations = endStations;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
