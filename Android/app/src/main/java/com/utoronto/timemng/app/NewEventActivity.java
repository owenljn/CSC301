package com.utoronto.timemng.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.utoronto.timemng.server.ServerHelper;

import java.util.Calendar;

/**
 * Add new event option.
 */
public class NewEventActivity extends Activity {
    private static final String TAG = "c2dm add event";
    private boolean isAllDay = false;
    private String eventTitle;
    private String startYearVar;
    private String startMonthVar;
    private String startDayVar;
    private String startTimeVar;
    private String endYearVar;
    private String endMonthVar;
    private String endDayVar;
    private String endTimeVar;
    private String location;
    private String invitees;
    private String note;
    private Calendar calendar = Calendar.getInstance();

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
        final TextView endDate = (TextView) this.findViewById(R.id.end_date);
        final DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                NewEventActivity.this.calendar.set(Calendar.YEAR, year);
                NewEventActivity.this.calendar.set(Calendar.MONTH, monthOfYear);
                NewEventActivity.this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                final String weekDay = DateFormat.format("EEEE", NewEventActivity.this.calendar).toString();
                final String monthStr = DateFormat.format("MMM", NewEventActivity.this.calendar).toString();
                final String dateStr = weekDay + ", " + dayOfMonth + " " + monthStr + " " + year;
                ((TextView) myView).setText(dateStr);
                endDate.setText(dateStr);

                NewEventActivity.this.startYearVar = String.valueOf(year);
                NewEventActivity.this.startMonthVar = String.valueOf(monthOfYear);
                NewEventActivity.this.startDayVar = String.valueOf(dayOfMonth);
                NewEventActivity.this.endYearVar = NewEventActivity.this.startYearVar;
                NewEventActivity.this.endMonthVar = NewEventActivity.this.startMonthVar;
                NewEventActivity.this.endDayVar = NewEventActivity.this.startDayVar;
            }
        };
        newFragment.show(getFragmentManager(), "startDatePicker");
    }

    /**
     * The start time is clicked.
     * @param myView  the view that was clicked.
     */
    public void onStartTimeClicked(final View myView) {
        final TextView endTime = (TextView) this.findViewById(R.id.event_end_time);
        final DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                NewEventActivity.this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                NewEventActivity.this.calendar.set(Calendar.MINUTE, minute);
                final String timeStrStart = DateFormat.format("HH:mm", NewEventActivity.this.calendar).toString();
                NewEventActivity.this.calendar.add(Calendar.HOUR_OF_DAY, 1);
                final String timeStrEnd = DateFormat.format("HH:mm", NewEventActivity.this.calendar).toString();
                ((TextView)myView).setText(timeStrStart);
                endTime.setText(timeStrEnd);
            }
        };
        newFragment.show(getFragmentManager(), "startTimePicker");
    }

    /**
     * The start date view is clicked.
     * @param myView  the view that was clicked.
     */
    public void onEndDateClicked(final View myView) {
        final DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                NewEventActivity.this.calendar.set(Calendar.YEAR, year);
                NewEventActivity.this.calendar.set(Calendar.MONTH, monthOfYear);
                NewEventActivity.this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                final String weekDay = DateFormat.format("EEEE", NewEventActivity.this.calendar).toString();
                final String monthStr = DateFormat.format("MMM", NewEventActivity.this.calendar).toString();
                final String dateStr = weekDay + ", " + dayOfMonth + " " + monthStr + " " + year;
                ((TextView) myView).setText(dateStr);
                NewEventActivity.this.endYearVar = String.valueOf(year);
                NewEventActivity.this.endMonthVar = String.valueOf(monthOfYear);
                NewEventActivity.this.endDayVar = String.valueOf(dayOfMonth);
            }
        };
        newFragment.show(getFragmentManager(), "endDatePicker");
    }

    /**
     * The start time is clicked.
     * @param myView  the view that was clicked.
     */
    public void onEndTimeClicked(final View myView) {
        final DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                NewEventActivity.this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                NewEventActivity.this.calendar.set(Calendar.MINUTE, minute);
                final String timeStrEnd = DateFormat.format("HH:mm", NewEventActivity.this.calendar).toString();
                NewEventActivity.this.calendar.add(Calendar.HOUR_OF_DAY, 1);
                ((TextView)myView).setText(timeStrEnd);
            }
        };
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
     * @param myView  the button clicked.
     */
    public void onCreateEventClicked(final View myView) {
        final EditText titleView = (EditText) this.findViewById(R.id.event_title_input);
        this.eventTitle = titleView.getText().toString();
        if (null != this.eventTitle) {
            final TextView startTimeView = (TextView) this.findViewById(R.id.event_start_time);
            final TextView endTimeView = (TextView) this.findViewById(R.id.event_end_time);
            final EditText locationView = (EditText) this.findViewById(R.id.event_location_input);
            final EditText inviteesView = (EditText) this.findViewById(R.id.event_add_ppl_input);
            final EditText noteView = (EditText) this.findViewById(R.id.event_note_input);
            this.startTimeVar = startTimeView.getText().toString();
            this.endTimeVar = endTimeView.getText().toString();
            this.location = locationView.getText().toString();
            this.invitees = inviteesView.getText().toString();
            this.note = noteView.getText().toString();
            Log.i(TAG, "is all day: " + Boolean.toString(this.isAllDay));
            Log.i(TAG, "title: " + this.eventTitle);
            Log.i(TAG, "start year: " + this.startYearVar);
            Log.i(TAG, "start month: " + this.startMonthVar);
            Log.i(TAG, "start day: " + this.startDayVar);
            Log.i(TAG, "start time: " + this.startTimeVar);
            Log.i(TAG, "finish year: " + this.endYearVar);
            Log.i(TAG, "finish month: " + this.endMonthVar);
            Log.i(TAG, "finish day: " + this.endDayVar);
            Log.i(TAG, "end time: " + this.endTimeVar);
            Log.i(TAG, "location: " + this.location);
            Log.i(TAG, "invitees: " + this.invitees);
            Log.i(TAG, "note: " + this.note);
            ServerHelper.sendToServer(this.eventTitle, this.note, this.location,
                    this.startYearVar, this.startMonthVar, this.startDayVar,
                    this.startTimeVar, this.endYearVar, this.endMonthVar,
                    this.endDayVar, this.endTimeVar, Boolean.toString(this.isAllDay));
        } else {
            // TODO: pop up saying there's an error.
        }
    }

    /**
     * Sets the default day and time for date and time picker.
     */
    private void setDefaultDateAndTime() {
        final Intent intent = getIntent();
        final int day = Integer.parseInt(intent.getStringExtra("day"));
        final int month = Integer.parseInt(intent.getStringExtra("month"));
        final int year = Integer.parseInt(intent.getStringExtra("year"));
        this.calendar.set(Calendar.DAY_OF_MONTH, day);
        this.calendar.set(Calendar.MONTH, month);
        this.calendar.set(Calendar.YEAR, year);
        this.startTimeVar = DateFormat.format("HH:" + "00", this.calendar).toString();
        this.calendar.add(Calendar.HOUR, 1);
        this.endTimeVar = DateFormat.format("HH:" + "00", this.calendar).toString();
        final String monthStr = DateFormat.format("MMM", this.calendar).toString();
        final String dayStr = DateFormat.format("dd", this.calendar).toString();
        final String weekDay = DateFormat.format("EEEE", this.calendar).toString();
        final String yearStr = DateFormat.format("yyyy", this.calendar).toString();
        final String dateString = String.format("%s, %s %s %s", weekDay, dayStr, monthStr, yearStr);
        final TextView startDate = (TextView) this.findViewById(R.id.start_date);
        final TextView startTime = (TextView) this.findViewById(R.id.event_start_time);
        final TextView endDate = (TextView) this.findViewById(R.id.end_date);
        final TextView endTime = (TextView) this.findViewById(R.id.event_end_time);
        startDate.setText(dateString);
        endDate.setText(dateString);
        startTime.setText(this.startTimeVar);
        endTime.setText(this.endTimeVar);
        this.startYearVar = String.valueOf(year);
        this.startMonthVar = String.valueOf(month);
        this.startDayVar = String.valueOf(day);
        this.endYearVar = this.startYearVar;
        this.endMonthVar = this.startMonthVar;
        this.endDayVar = this.startDayVar;
    }
}
