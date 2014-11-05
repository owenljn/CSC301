package com.utoronto.timemng.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
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
 * Note: Singleton design pattern.
 */
public class CalendarMonthConstructor {

    /**
     * Carried over from CoreActivity.
     */
    private static Activity activity;

    /**
     * Tag for logging purposes.
     */
    private static final String TAG = "c2dm calendar";

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
        this.selMonth = Calendar.getInstance(); // Get this month calendar.
        this.adapter = new CalendarGridAdapter(activity, this.selMonth); // Create an adapter.
        this.gridView = (GridView) activity.findViewById(R.id.calendar_grid); // Get the GridView item.

        whichMonthString(); // Label the calendar with correct month and year.
        // Set the adapter for the GridView, so that correct calendar days are displayed.
        this.gridView.setAdapter(this.adapter);
        // TODO: return a map of day of month to position.
        // TODO: request event list for the month from server.
        // TODO: mark days of month that contain an event.
        // Get the box where events will be displayed.
        final LinearLayout eventBox = (LinearLayout) activity.findViewById(R.id.dayevents_box);

        // Action for when a day is clicked.
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                // Make the box where events will be displayed visible.
                eventBox.setVisibility(View.VISIBLE);
                // Convert the day of month label on the clicked cell to an integer.
                final int dayOfMonth = Integer.parseInt(((TextView) view).getText().toString());
                // Construct list of events.
                // TODO: Start this as a separate activity following demo.
                new EventListConstructor(CalendarMonthConstructor.this.activity,
                                CalendarMonthConstructor.this.eventMap.get(dayOfMonth));
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
     * Sets a different month. This requires the calendar to be redrawn.
     * @param month selected month.
     */
    public final void setMonth(final Calendar month) {
        // Don't want to redrawn unnecessarily, so check if trying to set the same month and year.
        if (this.selMonth.get(Calendar.YEAR) != month.get(Calendar.YEAR) &&
                this.selMonth.get(Calendar.MONTH) != month.get(Calendar.MONTH)) {
            this.selMonth = month;
            // TODO: return a map of day of month to position.
            // TODO: request updated event list for new month from server.
            // TODO: mark days of month that contain an event.
            refactorCalendar(); // Redraw the calendar.
            whichMonthString(); // Set the correct month and year on label.
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
     * Redraws the calendar based on the changes made. Called every time a different
     * month is selected.
     */
    private void refactorCalendar() {
        this.adapter.makeCalendarArray();
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
    }
}
