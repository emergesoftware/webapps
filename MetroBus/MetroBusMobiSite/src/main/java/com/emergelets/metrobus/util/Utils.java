package com.emergelets.metrobus.util;



import java.util.Calendar;
import za.co.metrobus.hibernate.entity.BusServiceType;

public final class Utils {

    public static BusServiceType getServiceTypeByCurrentDayOfWeek() {
        int dayOfWeek = getDayOfWeek();

        // if it is a Sunday
        if (dayOfWeek == 1)
            return BusServiceType.SundayService;
        //if it is a Monday - Friday
        else if (dayOfWeek > 1 && dayOfWeek < 7)
            return BusServiceType.WeekdayService;
        // if it is a Saturday
        else
            return BusServiceType.SaturdayService;
    }

    /**
     * Gets the current day of the
     * week. The first day of the week
     * is preset as Sunday, which is
     * equal to 1.
     *
     * @return
     */
    public static int getDayOfWeek() {

        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);

    }

}
