package com.emergelets.metrobus.test;

import java.util.Calendar;

public class Test {

    public static void main(String[] args) {

        int startFrom = 7265;
        
        int hoursLeft = (int)(startFrom / 3600);
        int minutesLeft = (int)((startFrom % 3600) / 60);
        int secondsLeft = startFrom % 60;
        
        System.out.println(hoursLeft + " hr " + minutesLeft + " min " + secondsLeft + " sec");

    }

}
