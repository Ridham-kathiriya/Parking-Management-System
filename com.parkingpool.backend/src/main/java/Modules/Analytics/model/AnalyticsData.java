package Modules.Analytics.model;

public class AnalyticsData {
    private int parking_slot_id;
    private double longitude;
    private double latitude;
    private double revenue_generated;
    private double total_hours;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public int getParking_slot_id() {
        return parking_slot_id;
    }

    public void setParking_slot_id(int parking_slot_id) {
        this.parking_slot_id = parking_slot_id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRevenue_generated() {
        return revenue_generated;
    }

    public void setRevenue_generated(double revenue_generated) {
        this.revenue_generated = revenue_generated;
    }

    public double getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(double total_hours) {
        this.total_hours = total_hours;
    }

//    public AnalyticsData(int parking_slot_id, double longitude, double latitude, double revenue_generated, double total_hours) {
//        this.parking_slot_id = parking_slot_id;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.revenue_generated = revenue_generated;
//        this.total_hours = total_hours;
//    }
}
