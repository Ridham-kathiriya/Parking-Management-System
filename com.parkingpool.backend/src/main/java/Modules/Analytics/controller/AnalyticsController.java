package Modules.Analytics.controller;

import Modules.Analytics.model.AnalyticsData;
import Utils.Constants;
import Utils.GoogleMap;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AnalyticsController {

    public void createAnalyticsInCSVFormat(String FolderPath) throws SQLException {

        ArrayList<AnalyticsData> l = getAnalytics();

        String filePath = FolderPath+"\\Analytics.csv";
        File file = new File(filePath);
        try {

            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "Parking_slot_id", "Address", "Location_link","Revenue_generated","Total_Hours_of_use" };
            writer.writeNext(header);

            for(AnalyticsData ad: l)
            {
                String[] data1 = {String.valueOf(ad.getLongitude()), ad.getAddress(), GoogleMap.generateUrl(ad.getAddress()), String.valueOf(ad.getRevenue_generated()), String.valueOf(ad.getTotal_hours())};
                writer.writeNext(data1);
            }

            writer.close();
            outputfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String getLocationString(double longitude,double latitude) {
        return "https://www.google.com/maps/@" + longitude + "," + latitude + ",15z";
    }


    public ArrayList<AnalyticsData> getAnalytics() throws SQLException {
        ArrayList<AnalyticsData>  a_list = new ArrayList<AnalyticsData>();

        String query = "SELECT * FROM ParkingSlot;";

        Statement st = Constants.stmt;

        ResultSet rs1 = st.executeQuery(query);


        while(rs1.next())
        {
            AnalyticsData d = new AnalyticsData();

            d.setParking_slot_id(rs1.getInt("id"));
            d.setLatitude(rs1.getDouble("latitude"));
            d.setLongitude(rs1.getDouble("longitude"));
            d.setAddress(rs1.getString("address"));


            double totalHours = getTotalHours(rs1);

            d.setTotal_hours(totalHours);
            d.setRevenue_generated(totalHours*rs1.getDouble("hourly_rate"));

            a_list.add(d);
        }
        return a_list;
    }

    private double getTotalHours(ResultSet rs1) throws SQLException {
        double totalHours = 0;
        String q = "SELECT * FROM booking where parking_id="+ rs1.getInt("id")+";";
        Connection conn = Constants.conn;
        Statement st2 = conn.createStatement();
        ResultSet rs2 = st2.executeQuery(q);
        while(rs2.next())
        {
            totalHours += getTimeLength(rs2.getTime("start_time"),rs2.getTime("end_time"));
        }
        return totalHours;
    }


    private double getTimeLength(Time start_time, Time end_time)
    {
        double Hour_difference = (double) (end_time.getHours() - start_time.getHours());
        double Minutes_difference = (end_time.getMinutes() - start_time.getMinutes());
        double second_difference = (end_time.getSeconds() - start_time.getSeconds());
        return Hour_difference +(Minutes_difference /60)+(second_difference /3600);
    }

}
