package com.utoronto.timemng.event;

/**
 * Contains types for each day of the week.
 */
public enum WeekDay {
    SUNDAY(1) {
        @Override
        public String getWeekDayName() {
            return "Sunday";
        }
    },
    MONDAY(2) {
        @Override
        public String getWeekDayName() {
            return "Monday";
        }
    },
    TUESDAY(3) {
        @Override
        public String getWeekDayName() {
            return "Tuesday";
        }
    },
    WEDNESDAY(4) {
        @Override
        public String getWeekDayName() {
            return "Wednesday";
        }
    },
    THURSDAY(5) {
        @Override
        public String getWeekDayName() {
            return "Thursday";
        }
    },
    FRIDAY(6) {
        @Override
        public String getWeekDayName() {
            return "Friday";
        }
    },
    SATURDAY(7) {
        @Override
        public String getWeekDayName() {
            return "Saturday";
        }
    };

    private final int dayNum;

    /**
     * The default constructor for the class.
     * @param dayNum    day number.
     */
    WeekDay(final int dayNum) {
        this.dayNum = dayNum;
    }

    /**
     *  Gets the name of the weekday.
     * @return  name of weekday as string.
     */
    public abstract String getWeekDayName();

    /**
     * Gets the day number.
     * @return  the day number.
     */
    public int getDayNum() {
        return this.dayNum;
    }

    /**
     * Gets the type for day of the week, given day number.
     * @param numDay   day number.
     * @return         type for day of the week.
     */
    public WeekDay lookup(final int numDay) {
        for (final WeekDay weekDay : values()) {
            if (numDay == getDayNum()) {
                return weekDay;
            }
        }
        return null;
    }
}

