package gigigo.com.orchextra.data.datasources.builders;

import java.util.Calendar;
import java.util.Date;

public class DateBuilder {

    public static Date getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}
