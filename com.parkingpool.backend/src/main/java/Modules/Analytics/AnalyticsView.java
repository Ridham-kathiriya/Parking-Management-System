package Modules.Analytics;

import Modules.Analytics.controller.AnalyticsController;
import Modules.Analytics.model.AnalyticsData;
import Modules.User.model.USER_TYPE;
import Utils.Constants;
import Utils.GoogleMap;
import Utils.Scan;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnalyticsView {

    public AnalyticsView(){
        if(Constants.loggedInUser.role != USER_TYPE.ADMIN){
            Constants.printAndSpeak("You are not an admin. App exited forcefully!");
            System.exit(400);
        }
    }

    public void showAnalytics() throws SQLException {
        AnalyticsController ac = new AnalyticsController();

        ArrayList<AnalyticsData> l = ac.getAnalytics();

        for(AnalyticsData ad: l)
        {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Parking Slot ID: "+ ad.getParking_slot_id());
            System.out.println("Address: " + ad.getAddress());
            System.out.println("Location: "+ GoogleMap.generateUrl(ad.getAddress()));
            System.out.println("Revenue Generated: "+ ad.getRevenue_generated());
            System.out.println("Total Hours: "+ ad.getTotal_hours());
            System.out.println();
        }
    }

    public void createAnalyticsCSV() {
        AnalyticsController ac = new AnalyticsController();

        Constants.printAndSpeak("Please Enter folder Path: ");
        String path = Scan.nextLine();

        try {
            ac.createAnalyticsInCSVFormat(path);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
