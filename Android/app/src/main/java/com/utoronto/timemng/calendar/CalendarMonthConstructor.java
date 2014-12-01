package com.utoronto.timemng.calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.app.TaskListActivity;
import com.utoronto.timemng.event.DayDto;
import com.utoronto.timemng.event.EventDto;

import java.util.*;

/**
 * Constructs the calendar for the month. Creates the calendar for the current month by default.
 * A different month can be selected via setMonth method.
 * Note: Singleton design pattern.
 */
public class CalendarMonthConstructor {

    /**
     * Tag for logging purposes.
     */
    private static final String TAG = "c2dm calendar";

    /**
     * Carried over from CoreActivity.
     */
    private static Activity activity;

    /**
     * Month selected by user. When class is first instantiated, it will be set to current month.
     */
    private Calendar selMonth;

    /**
     * The adapter for the calendar (the calendar is a GridView item).
     */
    private final CalendarGridAdapter adapter;

    /**
     * A GridView item used to draw the calendar.
     */
    private final GridView gridView;

    /**
     * A map with day of the month as key, and a list of events for that day as value. Each key is unique
     * as only the days for the current month are used when constructing this map.
     */
    private final Map<Integer, List<EventDto>> eventMap;

    private final Map<Integer, List<EventDto>> eventMap2;

    /**
     * Default constructor for this class.
     */
    private CalendarMonthConstructor() {
        super();
        if (null == activity) {
            Log.i(TAG, "Tried to create the very first instance of this class without setting activity first");
            throw new IllegalArgumentException("need to set the activity before trying to create new instance");
        }
        this.eventMap = new HashMap<Integer, List<EventDto>>();
        this.eventMap2 = new HashMap<Integer, List<EventDto>>();
        this.selMonth = Calendar.getInstance(); // Get this month calendar.
        this.adapter = new CalendarGridAdapter(activity, this.selMonth); // Create an adapter.
        this.gridView = (GridView) activity.findViewById(R.id.calendar_grid); // Get the GridView item.

        whichMonthString(); // Label the calendar with correct month and year.
        // Set the adapter for the GridView, so that correct calendar days are displayed.
        this.gridView.setAdapter(this.adapter);
        // TODO: request event list for the month from server.

        // Action for when a day is clicked.
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final Calendar clonedCalendar = (Calendar) CalendarMonthConstructor.this.selMonth.clone();
                final String day = ((TextView) view).getText().toString();
                clonedCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
                // Construct list of events.
                final Intent intent = new Intent(activity, TaskListActivity.class);
                intent.putExtra("day", day);
                intent.putExtra("month", String.valueOf(clonedCalendar.get(Calendar.MONTH)));
                intent.putExtra("year", String.valueOf(clonedCalendar.get(Calendar.YEAR)));
                intent.putExtra("weekday", String.valueOf(clonedCalendar.get(Calendar.DAY_OF_WEEK)));
                activity.startActivity(intent);
            }
        });
    }

    /**
     * This method is used by CoreActivity when creating an instance of this
     * class for the first time on start of the application. Has to be used
     * prior to making new instance in order to set the activity.
     * @param coreActivity  core activity.
     */
    public static void defineActivity(final Activity coreActivity) {
        CalendarMonthConstructor.activity = coreActivity;
    }

    /**
     * Constructor holder. Ensures that only one instance of the class is ever created.
     */
    private static final class CalendarMonthConstructorHolder {
        private static final CalendarMonthConstructor CALENDAR_MONTH_CONSTRUCTOR = new CalendarMonthConstructor();

    }
    /**
     * Get the singleton instance of this class.
     * @return          the singleton instance of this class.
     */
    public static CalendarMonthConstructor getInstance() {
        return CalendarMonthConstructorHolder.CALENDAR_MONTH_CONSTRUCTOR;
    }

    /**
     * Sets the month and the year based on the month being viewed.
     */
    private void whichMonthString() {
        // The month and year label on the calendar.
        final TextView monthName = (TextView) activity.findViewById(R.id.month_text);
        final CharSequence month = DateFormat.format("MMMM", this.selMonth).toString(); // month
        final CharSequence year = DateFormat.format("yyyy", this.selMonth).toString(); // year
        monthName.setText(String.format("%s %s", month, year));
    }

    /**
     * Sets the month to the next month.
     */
    public final void setNextMonth() {
        // TODO: request updated event list for new month from server.
        this.selMonth.add(Calendar.MONTH, 1);
        this.eventMap.clear();
        refactorCalendar();
        whichMonthString();
    }

    /**
     * Sets the month to the previous month.
     */
    public final void setPrevMonth() {
        // TODO: request updated list for new month from server.
        this.selMonth.add(Calendar.MONTH, -1);
        this.eventMap.clear();
        refactorCalendar();
        whichMonthString();
    }

    /**
     * Sets the month to this month.
     */
    public void setToday() {
        // TODO: request updated list for new month from server.
        this.selMonth = Calendar.getInstance();
        this.eventMap.putAll(this.eventMap2);
        refactorCalendar();
        whichMonthString();
    }

    /**
     * Gets the calendar object for the currently displayed month.
     * @return  calendar object for the currently displayed month.
     */
    public final Calendar getMonth() {
        return this.selMonth;
    }

    /**
     * Redraws the calendar based on the changes made. Called every time a different
     * month is selected.
     */
    public void refactorCalendar() {
        this.adapter.setMonth(this.selMonth);
        this.adapter.makeCalendarArray();
        this.adapter.notifyDataSetChanged();
        this.gridView.invalidateViews();
        this.gridView.setAdapter(this.adapter);
    }



    /**
     * Populates the map with day of month as key and events as value with events.
     * Marks days on calendar that contain at least one event with a different colour.
     * @param days   one calendar day.
     */
    public void populateWithEvents(final List<DayDto> days) {
        // Get currently viewed calendar stats.
        final int year = this.selMonth.get(Calendar.YEAR);
        final int month = this.selMonth.get(Calendar.MONTH);
        // Iterate through the updated days.
        for (final DayDto day : days) {
            // Only care about days belonging to the same month and year we're currently viewing.
            if (year == day.getYear() && month == day.getMonth()) {
                final int dayNum = day.getDay(); // The day of month.
                // Get the GridView cell that's associated with this day.
                final View view = this.gridView.findViewWithTag(dayNum);
                // Put all events for this day into the hash map that stores them.
                this.eventMap.put(dayNum, day.getEvents());
                // If this day has some events make it blue, else make it white.
                if (day.getEvents().isEmpty()) {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#C2DFFF"));
                }
            }
        }
        this.eventMap2.putAll(this.eventMap);
    }

    /**
     * Gets the map of events for the month.
     * @return  map of events for the month
     */
    public Map<Integer, List<EventDto>> getEventMap() {
        return this.eventMap;
    }
}
