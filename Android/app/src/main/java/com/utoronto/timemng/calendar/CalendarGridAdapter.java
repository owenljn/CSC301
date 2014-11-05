package com.utoronto.timemng.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.utoronto.timemng.app.R;

import java.util.Calendar;

/**
 * Used for constructing a calendar grid.
 */
public class CalendarGridAdapter extends BaseAdapter {

    private static final String TAG = "c2dm cal grid";
    private final Activity activity; // Application context.
    private Calendar pickedMonth; // Calendar month requested by user.
    private Calendar curMonth; // Current date.
    private DayCell[] dayCells; // An array of DayCell classes. Stores date info.

    /**
     * Default constructor for this class.
     * @param activity      application context.
     * @param pickedMonth   month requested.
     *
     */
    public CalendarGridAdapter(final Activity activity, final Calendar pickedMonth) {
        if (null != pickedMonth) { // Cannot select a null month.
            this.activity = activity;
            this.curMonth = Calendar.getInstance(); // Gets an instance of calendar for current date.
            this.pickedMonth = (Calendar) pickedMonth.clone(); // Month selected by user.
            this.pickedMonth.set(Calendar.DAY_OF_MONTH, 1); // Look at first day of month picked by user.
            // Makes an array of days of month so that they correspond to correct days of the week.
            makeCalendarArray();
        } else {
            Log.i(TAG, "month was set to null in constructor");
            throw new IllegalArgumentException("pickedMonth cannot be null");
        }
    }

    /**
     * Sets the month value to a different month. Calling the method that will make
     * a new calendar array is the job of the caller class (CalendarMonthConstructor).
     * @param month the month that the calendar should display.
     */
    public final void setMonth(final Calendar month) {
        if (null != month) { // Selected month cannot be null.
            if (!this.pickedMonth.equals(month)) {
                this.pickedMonth = month;
            }
        } else {
            Log.i(TAG, "Month set to null in setter");
            throw new IllegalArgumentException("Month cannot be null");
        }
    }

    /**
     * Gets the month that is currently being viewed.
     * @return  calendar object for the month currently being viewed.
     */
    public final Calendar getPickedMonth() {
        return this.pickedMonth;
    }

    /**
     * Creates an array of correctly ordered days for the calendar.
     */
    public void makeCalendarArray() {
        // Clone calendar for selected month as it will be modified.
        final Calendar useMonth = (Calendar) this.pickedMonth.clone();
        useMonth.set(Calendar.DAY_OF_MONTH, 1); // Set to first day of the month.
        // To determine what day of the week the first day of the selected month falls under, need to
        // perform a shift. They days of the week preceding that will then be filled in with preceding
        // month's dates. The day's of the week following the last day of the selected month will be
        // filled in with dates from the following month.
        final int shiftBy = CalendarHelper.getOffset(useMonth);
        // Shift the dates so that the calendar can be filled in starting from Monday.
        useMonth.add(Calendar.DATE, -shiftBy);
        this.dayCells = new DayCell[42]; // the calendar grid is 6 rows by 7 columns.
        for (int i = 0; i < this.dayCells.length; i++) { // Iterate through every cell of calendar.
            // Store day of month and calendar.
            this.dayCells[i] = new DayCell((Calendar) useMonth.clone(), useMonth.get(Calendar.DAY_OF_MONTH));
            useMonth.add(Calendar.DATE, 1); // Go to next day.
        }
    }

    /**
     * Gets the days array.
     * @return  array of Day objects.
     */
    private DayCell[] getDayCells() {
        return this.dayCells;
    }

    /**
     * The number of cells that will be created in GridView.
     * @return  number of items in the data set; will be 42.
     */
    @Override
    public int getCount() {
        return this.dayCells.length;
    }

    /**
     * Gets the item given its position in the data set.
     * @param position  position of item in array that we want to get.
     * @return          the item in question.
     */
    @Override
    public Object getItem(final int position) {
        return this.dayCells[position];
    }

    /**
     * Given the item's position in the array, get its id. Not implemented as we're not
     * associating any unique id with each cell.
     * @param position  position of the item in the array.
     * @return          the id of the item in question.
     */
    @Override
    public long getItemId(final int position) {
        return 0;
    }

    /**
     * Get a view that displays the data at the specified position in the data set.
     * The view is inflated from an XML layout file. When the View is inflated, the
     * parent View (GridView) will apply default layout parameters.
     * This method will be called repeatedly on every cell in a grid view.
     * @param position      the position of the item within the adapter's data set.
     * @param convertView   the old view to reuse.
     * @param parent        parent that this view wil eventually be attached to.
     * @return              a View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final TextView textView; // text view that will be applied to the cell.
        final DayCell dayCell = this.dayCells[position]; // the appropriate day cell.
        // Integer value for the month. Note: as per the Calendar API, it appears that month indexing
        // starts from 0, so January is 0, February is 1, etc.
        final int dayInt = dayCell.getDateInt();

        // If this is the first cell of GridView, the grid item layout hasn't yet been inflated.
        if (null == row) {
            // Inflate using context obtained from core activity.
            row = LayoutInflater.from(this.activity.getApplicationContext()).inflate(R.layout.grid_item, parent, false);
        }
        // Find the text view by id in the inflated layout.
        textView = (TextView) row.findViewById(R.id.grid_item);
        // If we're looking at current month's dates, then make the day of month text black.
        if (dayCell.getDayDate().get(Calendar.MONTH) == this.pickedMonth.get(Calendar.MONTH)) {
            textView.setTextColor(Color.BLACK);
            row.setTag(dayInt);
        // If we're looking at days of month from either preceding or following months, make text gray.
        // Also  make those cells un-clickable.
        } else {
            textView.setTextColor(Color.GRAY);
            row.setFocusable(true); // This is weirdly counter-intuitive,
            row.setClickable(true); // but the combination of these two makes the view un-clickable.
        }
        // Mark today in red.
        if (this.curMonth.get(Calendar.YEAR) == dayCell.getDayDate().get(Calendar.YEAR) &&
                this.curMonth.get(Calendar.MONTH) == dayCell.getDayDate().get(Calendar.MONTH) &&
                this.curMonth.get(Calendar.DAY_OF_MONTH) == dayCell.getDayDate().get(Calendar.DAY_OF_MONTH)) {
            textView.setTextColor(Color.RED);
        }
        // Write correct day of month in this cell.
        textView.setText(Integer.toString(dayInt));
        return row; // Return the layout for cell so that it can be reused for other cells.
    }
}
