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
    private Calendar curMonth; // Current month calendar.
    private DayCell[] dayCells; // Collection of days.

    /**
     * Default constructor for this class.
     * @param activity       application context.
     * @param pickedMonth   month requested.
     *
     */
    public CalendarGridAdapter(final Activity activity, final Calendar pickedMonth) {
        if (null != pickedMonth) {
            this.activity = activity;
            this.curMonth = Calendar.getInstance();
            this.pickedMonth = (Calendar) pickedMonth.clone();
            this.pickedMonth.set(Calendar.DAY_OF_MONTH, 1); // Look at first day of month.
            makeCalendarArray();
        } else {
            Log.i(TAG, "month was set to null in constructor");
            throw new IllegalArgumentException("pickedMonth cannot be null");
        }
    }

    /**
     * Sets the month value to a different month.
     * @param month the month that the calendar should display.
     */
    public final void setMonth(final Calendar month) {
        if (null != month) {
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
        final Calendar useMonth = (Calendar) this.pickedMonth.clone();
        useMonth.set(Calendar.DAY_OF_MONTH, 1);
        final int shiftBy = CalendarHelper.getOffset(useMonth);
        // Shifting to accommodate for the fact that Monday is day number two.
        useMonth.add(Calendar.DATE, -shiftBy);
        this.dayCells = new DayCell[42]; // the calendar grid is 6 rows by 7 columns.
        for (int i = 0; i < this.dayCells.length; i++) {
            this.dayCells[i] = new DayCell((Calendar) useMonth.clone(), useMonth.get(Calendar.DAY_OF_MONTH));
            useMonth.add(Calendar.DATE, 1); // Go to next day in calendar.
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
     * The number of items in the data set represented by the Adapter.
     * @return  number of items in the data set.
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
     * Given the item's position in the array, get its id.
     * @param position  position of the item in the array.
     * @return          the id of the item in question.
     */
    // TODO: Implement in future release.
    @Override
    public long getItemId(final int position) {
        return 0;
    }

    /**
     * Get a view that displays the data at the specified position in the data set.
     * The view is inflated from an XML layout file. When the View is inflated, the
     * parent View (GridView) will apply default layout parameters.
     * @param position      the position of the item within the adapter's data set.
     * @param convertView   the old view to reuse.
     * @param parent        parent that this view wil eventually be attached to.
     * @return              a View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final TextView textView;
        final DayCell dayCell = this.dayCells[position];
        final int dayInt = dayCell.getDateInt();

        if (null == row) {
            row = LayoutInflater.from(this.activity.getApplicationContext()).inflate(R.layout.grid_item, parent, false);
        }

        textView = (TextView) row.findViewById(R.id.grid_item);
        if (dayCell.getDayDate().get(Calendar.MONTH) == this.pickedMonth.get(Calendar.MONTH)) {
            textView.setTextColor(Color.BLACK);
            row.setTag(dayInt);
        } else {
            textView.setTextColor(Color.GRAY);
            row.setFocusable(true);
            row.setClickable(true);
        }

        if (this.curMonth.get(Calendar.YEAR) == dayCell.getDayDate().get(Calendar.YEAR) &&
                this.curMonth.get(Calendar.MONTH) == dayCell.getDayDate().get(Calendar.MONTH) &&
                this.curMonth.get(Calendar.DAY_OF_MONTH) == dayCell.getDayDate().get(Calendar.DAY_OF_MONTH)) {
            textView.setTextColor(Color.RED);
        }

        textView.setText(Integer.toString(dayInt));
        return row;
    }
}