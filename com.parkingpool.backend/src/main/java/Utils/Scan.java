package Utils;

import java.util.Scanner;

public class Scan {
    static Scanner sc = Constants.sc;
    public static String nextLine(){
        String input = sc.nextLine();
        if(input.equals("") || input.equals("\n")){
            Constants.printAndSpeak("Cannot be empty. Please try again!");
            return nextLine();
        } else {
            return input;
        }
    }
}
