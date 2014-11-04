package com.utoronto.timemng.calendar;
import java.util.Calendar;


/**
 * Contains several helper methods for the calendar.
 */
public class CalendarHelper {

    /**
     * Default constructor for this class.
     */
    public CalendarHelper() {
    }

    /**
     * There are two possibilities for the system calendar. Monday can be first,
     * or Sunday can be first.
     * @return  true iff Monday is the first day of the week.
     */
    public static boolean isFirstWeekDayMonday() {
        return Calendar.MONDAY == Calendar.getInstance().getFirstDayOfWeek();
    }

    /**
     * Gets the offset for the given month. Helps with calculating position of day of month in calendar.
     * @param month relevant month.
     * @return      offset for the month.
     */
    public static int getOffset(final Calendar month) {
        int shiftBy = month.get(Calendar.DAY_OF_WEEK);
        // Calendar.MONDAY == 2 and Calendar.SUNDAY == 1 always.
        // Need to perform different shifts depending on whether Monday is first day of week, or Sunday.
        if (CalendarHelper.isFirstWeekDayMonday()) {
            if (Calendar.SUNDAY == shiftBy) {
                shiftBy = 6;
            } else {
                shiftBy = shiftBy - 2;
            }
        } else { // It doesn't match our calendar format, need to recalculate.
            shiftBy = shiftBy - 1;
        }
        return shiftBy;
    }
}
