package com.utoronto.timemng.calendar;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import com.utoronto.timemng.app.R;

import java.util.Calendar;

/**
 * 02/11/2014 14:52.
 */
public class CalendarMonthConstructor extends LinearLayout {
    // TODO: Make receiver inner class for this.
    private static final String TAG = "c2dm_CalendarMonthConstructor";
    private Calendar selMonth;
    private CalendarGridAdapter adapter;
    private Activity activity;

    /**
     * Default constructor for this class.
     * @param activity   Application context.
     */
    public CalendarMonthConstructor(final Activity activity) {
        super(activity, null); // Call to default constructor for LinearLayout
        this.activity = activity;
        this.selMonth = Calendar.getInstance(); // Get this month calendar.
        this.adapter = new CalendarGridAdapter(activity, this.selMonth); // Create an adapter.
        final GridView gridView = (GridView) activity.findViewById(R.id.calendar_grid); // My calendar grid.

        whichMonthString();
        gridView.setAdapter(this.adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CalendarMonthConstructor.this.activity.getApplicationContext(),
//                        "" + position, Toast.LENGTH_SHORT).show();

                Toast.makeText(CalendarMonthConstructor.this.activity.getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void whichMonthString() {
        final TextView monthName = (TextView) this.activity.findViewById(R.id.month_text);
        final CharSequence month = DateFormat.format("MMMM", this.selMonth);
        monthName.setText(String.format("%s", month.toString()));
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
}
