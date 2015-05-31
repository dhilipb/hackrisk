package com.mu.mothersunited;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dhilipb on 30/05/2015.
 */
public class MothersUnitedUtil
{

    /**
     * @param birthdate
     * @return Age from the current date
     */
    public static int getAge(final Date birthdate)
    {
        return getAge(Calendar.getInstance().getTime(), birthdate);
    }

    /**
     * Calculating age from a current date
     *
     * @param current
     * @param birthdate
     * @return Age from the current (arg) date
     */
    public static int getAge(final Date current, final Date birthdate)
    {

        if (birthdate == null)
        {
            return 0;
        }
        if (current == null)
        {
            return getAge(birthdate);
        }
        else
        {
            final Calendar calend = new GregorianCalendar();
            calend.set(Calendar.HOUR_OF_DAY, 0);
            calend.set(Calendar.MINUTE, 0);
            calend.set(Calendar.SECOND, 0);
            calend.set(Calendar.MILLISECOND, 0);

            calend.setTimeInMillis(current.getTime() - birthdate.getTime());

            float result = 0;
            result = calend.get(Calendar.YEAR) - 1970;
            result += (float) calend.get(Calendar.MONTH) / (float) 12;
            return (int) result;
        }
    }

    public static int mixTwoColors(int color1, int color2, float amount)
    {
        final byte ALPHA_CHANNEL = 24;
        final byte RED_CHANNEL = 16;
        final byte GREEN_CHANNEL = 8;
        final byte BLUE_CHANNEL = 0;

        final float inverseAmount = 1.0f - amount;

        int a = ((int) (((float) (color1 >> ALPHA_CHANNEL & 0xff) * amount) + ((float) (color2 >> ALPHA_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int r = ((int) (((float) (color1 >> RED_CHANNEL & 0xff) * amount) + ((float) (color2 >> RED_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int g = ((int) (((float) (color1 >> GREEN_CHANNEL & 0xff) * amount) + ((float) (color2 >> GREEN_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int b = ((int) (((float) (color1 & 0xff) * amount) + ((float) (color2 & 0xff) * inverseAmount))) & 0xff;

        return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
    }
}
