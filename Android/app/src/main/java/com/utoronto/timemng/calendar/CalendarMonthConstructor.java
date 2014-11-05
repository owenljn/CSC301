package com.utoronto.timemng.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.daytasks.EventListConstructor;
import com.utoronto.timemng.event.DayDto;
import com.utoronto.timemng.event.EventDto;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Constructs the calendar for the month. Creates the calendar for the current month by default.
 * A different month can be selected via setMonth method.
 */
public class CalendarMonthConstructor {
    private static CalendarMonthConstructor calendarMonthConstructor;
    private static final String TAG = "c2dm calendar";
    private Calendar selMonth;
    private final CalendarGridAdapter adapter;
    private final Activity activity;
    private final GridView gridView;
    private final Map<Integer, List<EventDto>> eventMap;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Default constructor for this class.
     * @param activity   activity.
     */
    private CalendarMonthConstructor(final Activity activity) {
        super();
        this.activity = activity;
        this.eventMap = new HashMap<Integer, List<EventDto>>();
        this.selMonth = Calendar.getInstance(); // Get this month calendar.
        this.adapter = new CalendarGridAdapter(activity, this.selMonth); // Create an adapter.
        this.gridView = (GridView) activity.findViewById(R.id.calendar_grid); // My calendar grid.

        whichMonthString();
        gridView.setAdapter(this.adapter);
        // TODO: return a map of day of month to position.
        // TODO: request event list for the month from server.
        // TODO: mark days of month that contain an event.
        final LinearLayout eventBox = (LinearLayout) activity.findViewById(R.id.dayevents_box);
        eventBox.setVisibility(View.VISIBLE);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                int dayOfMonth = Integer.parseInt(((TextView) view).getText().toString());
                final EventListConstructor eventListConstructor =
                        new EventListConstructor(CalendarMonthConstructor.this.activity,
                                CalendarMonthConstructor.this.eventMap.get(dayOfMonth));

//                Toast.makeText(CalendarMonthConstructor.this.activity.getApplicationContext(),
//                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Get the singleton object.
     * @param activity  activity.
     * @return          the singleton object.
     */
    public static CalendarMonthConstructor getInstance(final Activity activity) {
        if (null == calendarMonthConstructor) {
            calendarMonthConstructor = new CalendarMonthConstructor(activity);
        }
        return calendarMonthConstructor;
    }

    /**
     * Get the already instantiated singleton object.
     * @return  singleton object.
     */
    public static CalendarMonthConstructor getInstance() {
        if (null != calendarMonthConstructor) {
            return calendarMonthConstructor;
        } else {
            throw new IllegalArgumentException("Class not yet instantiated.");
        }
    }

    /**
     * Returns string representation of selected month and year.
     */
    private void whichMonthString() {
        final TextView monthName = (TextView) this.activity.findViewById(R.id.month_text);
        final CharSequence month = DateFormat.format("MMMM", this.selMonth).toString(); // month
        final CharSequence year = DateFormat.format("yyyy", this.selMonth).toString(); // year
        monthName.setText(String.format("%s %s", month, year));

//        try {
//            testJSON();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Sets a different month. This requires the calendar to be redrawn.
     * @param month selected month.
     */
    public final void setMonth(final Calendar month) {
        // Don't want to redrawn unnecessarily.
        if (this.selMonth.get(Calendar.YEAR) != month.get(Calendar.YEAR) &&
                this.selMonth.get(Calendar.MONTH) != month.get(Calendar.MONTH)) {
            this.selMonth = month;
            // TODO: return a map of day of month to position.
            // TODO: request updated event list for new month from server.
            // TODO: mark days of month that contain an event.
            refactorCalendar(); // Redraw the calendar.
            whichMonthString(); // Get the correct month name.
        }
    }

    /**
     * Gets the calendar object for the currently displayed month.
     * @return  calendar object for the currently displayed month.
     */
    public final Calendar getMonth() {
        return this.selMonth;
    }

    /**
     * Redraws the calendar based on the changes made.
     */
    private void refactorCalendar() {
        this.adapter.makeCalendarArray();
    }

//    private void testJSON() throws IOException {
//
//        final EventDto event1 = new EventDto(1, "Project", 2014, 10, 8, 7, "12:30:00", "13:00:00", null, null, null, false, null, false);
//        final EventDto event2 = new EventDto(2, "Test", 2014, 10, 8, 7, "13:00:00", "14:00:00", null, null, null, false, null, false);
//        final EventDto event3 = new EventDto(3, "Gym", 2014, 10, 9, 7, "17:00:00", "18:00:00", null, null, null, false, null, false);
//        final EventDto event4 = new EventDto(4, "Project", 2014, 10, 9, 7, "12:30:00", "13:00:00", null, null, null, false, null, false);
//        final EventDto event5 = new EventDto(5, "Appointment", 2014, 10, 9, 7, "16:00:00", "16:15:00", null, null, null, false, null, false);
//        final List<EventDto> eventList1 = Arrays.asList(event1, event2, event3);
//        final List<EventDto> eventList2 = Arrays.asList(event4, event5);
//        final DayDto day1 = new DayDto(2014, 10, 8, eventList1);
//        final DayDto day2 = new DayDto(2014, 10, 9, eventList2);
//        final List<DayDto> dayList = Arrays.asList(day1, day2);
//        final PayloadContainerDto containerDto = new PayloadContainerDto(dayList);
//                                                      String aaa = "{\"days\":[{\"year\":2014,\"month\":10,\"day\":8,\"events\":[{\"eventId\":1,\"eventTitle\":\"Project\",\"year\":2014,\"month\":10,\"dayOfMonth\":8,\"dayOfWeek\":7,\"startTime\":\"12:30:00\",\"endTime\":\"13:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":2,\"eventTitle\":\"Test\",\"year\":2014,\"month\":10,\"dayOfMonth\":8,\"dayOfWeek\":7,\"startTime\":\"13:00:00\",\"endTime\":\"14:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":3,\"eventTitle\":\"Gym\",\"year\":2014,\"month\":10,\"dayOfMonth\":9,\"dayOfWeek\":7,\"startTime\":\"17:00:00\",\"endTime\":\"18:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false}]},{\"year\":2014,\"month\":10,\"day\":9,\"events\":[{\"eventId\":4,\"eventTitle\":\"Project\",\"year\":2014,\"month\":10,\"dayOfMonth\":9,\"dayOfWeek\":7,\"startTime\":\"12:30:00\",\"endTime\":\"13:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":5,\"eventTitle\":\"Appointment\",\"year\":2014,\"month\":10,\"dayOfMonth\":9,\"dayOfWeek\":7,\"startTime\":\"16:00:00\",\"endTime\":\"16:15:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false}]}]}";
//        final String json = OBJECT_MAPPER.writeValueAsString(containerDto);
//
//        PayloadContainerDto dto = OBJECT_MAPPER.readValue(json, PayloadContainerDto.class);
//    }

    /**
     * Populates the map of days to events with events, and marks days on calendar that contain at least
     * one event.
     * @param days   one calendar day.
     */
    public void populateWithEvents(final List<DayDto> days) {
        final int year = this.selMonth.get(Calendar.YEAR);
        final int month = this.selMonth.get(Calendar.MONTH);
        for (final DayDto day : days) {
            if (year == day.getYear() && month == day.getMonth()) {
                final int dayNum = day.getDay();
                final View view = this.gridView.findViewWithTag(dayNum);
                this.eventMap.put(dayNum, day.getEvents());
                if (day.getEvents().isEmpty()) {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#C2DFFF"));
                }
            }
        }
    }
}
