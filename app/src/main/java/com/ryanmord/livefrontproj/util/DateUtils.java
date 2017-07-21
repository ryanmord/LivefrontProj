package com.ryanmord.livefrontproj.util;

import android.content.Context;

import com.ryanmord.livefrontproj.R;

import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by ryanmord on 7/21/17.
 */

public class DateUtils {

    public static String durationSinceDate(Context c, DateTime date) {

        Period p = new Period(date, new DateTime());

        if(p.getDays() > 0) {
            //return days since
            String s = c.getResources().getQuantityString(R.plurals.day_ago, p.getDays());

            return String.format(s, p.getDays());
        } else if(p.getHours() > 0) {
            //return hours
            String s = c.getResources().getQuantityString(R.plurals.hour_ago, p.getHours());

            return String.format(s, p.getHours());
        } else {
            String s = c.getResources().getQuantityString(R.plurals.minute_ago, p.getMinutes());

            return String.format(s, p.getMinutes());
        }

    }

}
