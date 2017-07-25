package com.ryanmord.livefrontproj.util;

import android.content.Context;

import com.ryanmord.livefrontproj.R;

import org.joda.time.DateTime;
import org.joda.time.Period;


/**
 * Utility class for date related functions
 */
public class DateUtils {

    /**
     * Takes a DateTime object, and returns the time since published
     * in the form of a String. String returned will be in the formatted
     * with the time unit of most significant non-zero denomination (minute, hour, day).
     *
     * @param c     Application context
     * @param date  Date to analyze and get time since
     *
     * @return  String representing the time since the date given.
     */
    public static String durationSinceDate(Context c, DateTime date) {

        Period p = new Period(date, new DateTime());

        if(p.getDays() > 0) {
            //return if more than a day has passed
            String s = c.getResources().getQuantityString(R.plurals.day_ago, p.getDays());

            return String.format(s, p.getDays());
        } else if(p.getHours() > 0) {
            //return if more than an hour has passed
            String s = c.getResources().getQuantityString(R.plurals.hour_ago, p.getHours());

            return String.format(s, p.getHours());
        } else if(p.getMinutes() > 0) {
            //return if more than a minute has passed
            String s = c.getResources().getQuantityString(R.plurals.minute_ago, p.getMinutes());

            return String.format(s, p.getMinutes());
        } else {
            //Else return 'Now' since time was less than a minute
            return "Now";
        }

    }

}
