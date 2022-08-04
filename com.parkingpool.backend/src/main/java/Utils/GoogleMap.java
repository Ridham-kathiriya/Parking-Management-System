package Utils;

import java.util.*;

public class GoogleMap {
    public static Map<String, String> parseUrl(String googleMapUrl){
        Map<String, String> parsedData = new HashMap<String, String>();
        String address = "";
        String[] googleMapUrlSplit = googleMapUrl.split("/");
        int splitIndex = 0;
        if(googleMapUrlSplit[4].equals("place")){
            address = String.join(" ", googleMapUrlSplit[5].split("\\+"));
            splitIndex = 6;
        } else if(googleMapUrlSplit[4].charAt(0) == '@'){
            address = "";
            splitIndex = 4;
        } else {
            Constants.printAndSpeak("Sorry, I couldn't find the address.");
            return null;
        }
        String[] rawCoordinates = googleMapUrlSplit[splitIndex].split(",");
        parsedData.put("address", address);
        parsedData.put("longitude", rawCoordinates[0].substring(1));
        parsedData.put("latitude", rawCoordinates[1]);
        return parsedData;
    }

    public static String generateUrl(String address){
        String parsedAddress = String.join("+", address.split(" "));
        String googleMapUrl = "https://www.google.com/maps/search/?api=1&query=" + parsedAddress;
        return googleMapUrl;
    }
}
