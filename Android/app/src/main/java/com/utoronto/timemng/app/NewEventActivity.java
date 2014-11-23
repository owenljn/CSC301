package com.utoronto.timemng.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import com.utoronto.timemng.event.EventDto;

import java.util.Calendar;

/**
 * Add new event option.
 */
public class NewEventActivity extends Activity {
    private EventDto eventDto;
    private boolean isAllDay = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        // Keep orientation portrait for now.
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setDefaultDateAndTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Describes action when the "all day" checkbox was checked.
     * @param view  the view that was clicked.
     */
    public void onCheckBoxClicked(final View view) {
        // Is this view now checked?
        final boolean checked = ((CheckBox) view).isChecked();
        // Get the text views for the start time and the end time.
        final TextView startTime = (TextView) this.findViewById(R.id.event_start_time);
        final TextView endTime = (TextView) this.findViewById(R.id.event_end_time);
        // If checked, then want to set the isAllDay variable to true, and make start
        // time and end time invisible.
        if (checked) {
            this.isAllDay = true;
            startTime.setVisibility(View.INVISIBLE);
            endTime.setVisibility(View.INVISIBLE);
        } else {
            this.isAllDay = false;
            startTime.setVisibility(View.VISIBLE);
            endTime.setVisibility(View.VISIBLE);
        }
    }

    /**
     * The start date view is clicked.
     * @param myView  the view that was clicked.
     */
    public void onStartDateClicked(final View myView) {
        final Calendar calendar = Calendar.getInstance();
        final TextView endDate = (TextView) this.findViewById(R.id.end_date);
        final DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                final String weekDay = DateFormat.format("EEEE", calendar).toString();
                final String monthStr = DateFormat.format("MMM", calendar).toString();
                final String dateStr = weekDay + ", " + dayOfMonth + " " + monthStr + " " + year;
                ((TextView) myView).setText(dateStr);
                endDate.setText(dateStr);
            }
        };
        newFragment.show(getFragmentManager(), "startDatePicker");
    }

    /**
     * The start time is clicked.
     * @param view  the view that was clicked.
     */
    public void onStartTimeClicked(final View view) {
        final DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "startTimePicker");
    }

    /**
     * The start date view is clicked.
     * @param view  the view that was clicked.
     */
    public void onEndDateClicked(final View view) {
        final DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "endDatePicker");
    }

    /**
     * The start time is clicked.
     * @param view  the view that was clicked.
     */
    public void onEndTimeClicked(final View view) {
        final DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "endTimePicker");
    }

    /**
     * Close activity.
     * @param view  the button clicked.
     */
    public void onCancelEventClicked(final View view) {
        onBackPressed();
    }

    /**
     * Create event by filling out all the relevant fields and sending request
     * to server.
     * @param view  the button clicked.
     */
    public void onCreateEventClicked(final View view) {
    }

    /**
     * Sets the default day and time for date and time picker.
     */
    private void setDefaultDateAndTime() {
        final Intent intent = getIntent();
        final Calendar calendar = Calendar.getInstance();
        final int day = Integer.parseInt(intent.getStringExtra("day"));
        final int month = Integer.parseInt(intent.getStringExtra("month"));
        final int year = Integer.parseInt(intent.getStringExtra("year"));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        final CharSequence beginTime = DateFormat.format("HH:" + "00", calendar).toString();
        calendar.add(Calendar.HOUR, 1);
        final CharSequence finishTime = DateFormat.format("HH:" + "00", calendar).toString();
        final CharSequence monthStr = DateFormat.format("MMM", calendar).toString();
        final CharSequence dayStr = DateFormat.format("dd", calendar).toString();
        final CharSequence weekDay = DateFormat.format("EEEE", calendar).toString();
        final CharSequence yearStr = DateFormat.format("yyyy", calendar).toString();
        final String dateString = String.format("%s, %s %s %s", weekDay, dayStr, monthStr, yearStr);
        final TextView startDate = (TextView) this.findViewById(R.id.start_date);
        final TextView startTime = (TextView) this.findViewById(R.id.event_start_time);
        final TextView endDate = (TextView) this.findViewById(R.id.end_date);
        final TextView endTime = (TextView) this.findViewById(R.id.event_end_time);
        startDate.setText(dateString);
        endDate.setText(dateString);
        startTime.setText(beginTime);
        endTime.setText(finishTime);
    }
}
