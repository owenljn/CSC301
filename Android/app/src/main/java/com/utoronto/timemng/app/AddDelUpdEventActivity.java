package com.utoronto.timemng.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.utoronto.timemng.app.constants.Constants;
import com.utoronto.timemng.server.ServerHelper;

import java.util.Calendar;

/**
 * Add new event option.
 */
public class AddDelUpdEventActivity extends Activity {
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

    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        final String type = intent.getStringExtra("type");
        if ("add".equals(type)) {
            setContentView(R.layout.add_event);
            // Keep orientation portrait for now.
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setDefaultDateAndTime();
        } else {
            setContentView(R.layout.edit_event);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setAllFields();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Sets all fields for the fragment.
     */
    private void setAllFields() {
        final TextView startDateBox = (TextView) this.findViewById(R.id.start_date);
        final TextView endDateBox = (TextView) this.findViewById(R.id.end_date);
        final EditText titleBox = (EditText) this.findViewById(R.id.event_title_input);
        final CheckBox allDayBox = (CheckBox) this.findViewById(R.id.checkbox_all_day);
        final TextView startTimeBox = (TextView) this.findViewById(R.id.event_start_time);
        final TextView endTimeBox = (TextView) this.findViewById(R.id.event_end_time);
        final EditText locationBox = (EditText) this.findViewById(R.id.event_location_input);
        final EditText noteBox = (EditText) this.findViewById(R.id.event_note_input);
        final Intent intent = getIntent();
        this.isAllDay = Boolean.parseBoolean(intent.getStringExtra("isAllDay"));
        this.eventTitle = intent.getStringExtra("eventName");
        this.startYearVar = intent.getStringExtra("startYear");
        this.startMonthVar = intent.getStringExtra("startMonth");
        this.startDayVar = intent.getStringExtra("startDay");
        this.startTimeVar = intent.getStringExtra("startTime");
        this.endYearVar = intent.getStringExtra("endYear");
        this.endMonthVar = intent.getStringExtra("endMonth");
        this.endDayVar = intent.getStringExtra("endDay");
        this.endTimeVar = intent.getStringExtra("endTime");
        this.note = intent.getStringExtra("description");
        this.location = intent.getStringExtra("location");
        setCalendarDate(Integer.parseInt(this.startYearVar), Integer.parseInt(this.startMonthVar),
                Integer.parseInt(this.startDayVar));
        String weekday = DateFormat.format("EEEE", this.calendar).toString();
        String month = DateFormat.format("MMM", this.calendar).toString();
        String dateStr = generateDateString(weekday, this.startDayVar, month, this.startYearVar);
        allDayBox.setChecked(this.isAllDay);
        titleBox.setEnabled(false);
        titleBox.setText(this.eventTitle);
        startDateBox.setText(dateStr);
        setCalendarDate(Integer.parseInt(this.endYearVar), Integer.parseInt(this.endMonthVar),
                Integer.parseInt(this.endDayVar));
        weekday = DateFormat.format("EEEE", this.calendar).toString();
        month = DateFormat.format("MMM", this.calendar).toString();
        dateStr = generateDateString(weekday, this.endDayVar, month, this.endYearVar);
        endDateBox.setText(dateStr);
        startTimeBox.setText(this.startTimeVar);
        endTimeBox.setText(this.endTimeVar);
        locationBox.setText(this.location, TextView.BufferType.EDITABLE);
        noteBox.setText(this.note, TextView.BufferType.EDITABLE);
    }

    public void onDeleteEventClicked(final View view) {
        ServerHelper.deleteEvent(this.eventTitle);
        this.finish();
    }

    /**
     * Describes action when the "all day" checkbox was checked.
     * @param view  the view that was clicked.
     */
    public void onCheckBoxClicked(final View view) {
        // Is this view now checked?
        final boolean checked = ((CheckBox) view).isChecked();
        // Get the text views for the start time and the end time.
        // If checked, then want to set the isAllDay variable to true, and make start
        // time and end time invisible.
        final TextView startTimeBox = (TextView) this.findViewById(R.id.event_start_time);
        final TextView endTimeBox = (TextView) this.findViewById(R.id.event_end_time);
        if (checked) {
            this.isAllDay = true;
            startTimeBox.setVisibility(View.INVISIBLE);
            endTimeBox.setVisibility(View.INVISIBLE);
        } else { // Need to set visible again in case someone changes their mind and unchecks box.
            this.isAllDay = false;
            startTimeBox.setVisibility(View.VISIBLE);
            endTimeBox.setVisibility(View.VISIBLE);
        }
    }

    /**
     * The start date view is clicked. Initialise date picker pop up.
     * @param myView  the view that was clicked.
     */
    public void onStartDateClicked(final View myView) {
        final TextView endDateBox = (TextView) this.findViewById(R.id.end_date);
        final DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                setCalendarDate(year, monthOfYear, dayOfMonth);
                final String weekDay = DateFormat.format("EEEE", AddDelUpdEventActivity.this.calendar).toString();
                final String monthStr = DateFormat.format("MMM", AddDelUpdEventActivity.this.calendar).toString();
                final String dateStr = generateDateString(weekDay, String.valueOf(dayOfMonth), monthStr,
                        String.valueOf(year));
                ((TextView) myView).setText(dateStr);
                endDateBox.setText(dateStr);

                setBothDateVars(String.valueOf(year), String.valueOf(monthOfYear), String.valueOf(dayOfMonth));
            }
        };
        newFragment.show(getFragmentManager(), "startDatePicker");
    }

    /**
     * The start time is clicked.
     * @param myView  the view that was clicked.
     */
    public void onStartTimeClicked(final View myView) {
        final TextView endTimeBox = (TextView) this.findViewById(R.id.event_end_time);
        final DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                setCalendarTime(hourOfDay, minute);
                final String timeStrStart = DateFormat.format("HH:mm", AddDelUpdEventActivity.this.calendar).toString();
                AddDelUpdEventActivity.this.calendar.add(Calendar.HOUR_OF_DAY, 1);
                final String timeStrEnd = DateFormat.format("HH:mm", AddDelUpdEventActivity.this.calendar).toString();
                ((TextView)myView).setText(timeStrStart);
                endTimeBox.setText(timeStrEnd);
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
                setCalendarDate(year, monthOfYear, dayOfMonth);
                final String weekDay = DateFormat.format("EEEE", AddDelUpdEventActivity.this.calendar).toString();
                final String monthStr = DateFormat.format("MMM", AddDelUpdEventActivity.this.calendar).toString();
                final String dateStr = generateDateString(weekDay, String.valueOf(dayOfMonth), monthStr,
                        String.valueOf(year));
                ((TextView) myView).setText(dateStr);
                setEndDateVars(String.valueOf(year), String.valueOf(monthOfYear), String.valueOf(dayOfMonth));
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
                setCalendarTime(hourOfDay, minute);
                final String timeStrEnd = DateFormat.format("HH:mm", AddDelUpdEventActivity.this.calendar).toString();
                AddDelUpdEventActivity.this.calendar.add(Calendar.HOUR_OF_DAY, 1);
                ((TextView)myView).setText(timeStrEnd);
            }
        };
        newFragment.show(getFragmentManager(), "endTimePicker");
    }

    /**
     * Create event by filling out all the relevant fields and sending request
     * to server.
     * @param myView  the button clicked.
     */
    public void onCreateEventClicked(final View myView) {
        final TextView startTimeBox = (TextView) this.findViewById(R.id.event_start_time);
        final TextView endTimeBox = (TextView) this.findViewById(R.id.event_end_time);
        final EditText titleBox = (EditText) this.findViewById(R.id.event_title_input);
        final EditText locationBox = (EditText) this.findViewById(R.id.event_location_input);
        final EditText noteBox = (EditText) this.findViewById(R.id.event_note_input);
        final EditText inviteesBox = (EditText) this.findViewById(R.id.event_add_ppl_input);
        this.eventTitle = titleBox.getText().toString(); // Obtain event title.
        if (null != this.eventTitle && !this.eventTitle.trim().isEmpty()) { // Title must not be empty.
            // Obtain start time, end time, location, invitees, and note strings from respective fields.
            setFieldVars(startTimeBox.getText().toString(), endTimeBox.getText().toString(),
                    locationBox.getText().toString(), inviteesBox.getText().toString(),
                    noteBox.getText().toString());
            ServerHelper.createEvent(this.eventTitle, this.note, this.location,
                    this.startYearVar, this.startMonthVar, this.startDayVar,
                    this.startTimeVar, this.endYearVar, this.endMonthVar,
                    this.endDayVar, this.endTimeVar, Boolean.toString(this.isAllDay));
            /** Delete **/
            Log.d(TAG, "event title: " + this.eventTitle);
            Log.d(TAG, "is all day? " + Boolean.toString(this.isAllDay));
            Log.d(TAG, "start year: " + this.startYearVar);
            Log.d(TAG, "start month: " + this.startMonthVar);
            Log.d(TAG, "start day: " + this.startDayVar);
            Log.d(TAG, "start time: " + this.startTimeVar);
            Log.d(TAG, "end year: " + this.endYearVar);
            Log.d(TAG, "end month: " + this.endMonthVar);
            Log.d(TAG, "end day: " + this.endDayVar);
            Log.d(TAG, "end time: " + this.endTimeVar);
            Log.d(TAG, "location: " + this.location);
            Log.d(TAG, "invitees: " + this.invitees);
            Log.d(TAG, "note: " + this.note);
            /** **/
            toastEventCreated(this.eventTitle); // Lets user know event was created.
            this.finish(); // Terminate activity.
        } else {
            promptEnterEventName();
        }
    }

    /**
     * Sets the various field variables.
     * @param startTime     chosen event start time.
     * @param endTime       chosen event end time.
     * @param location      chosen event location.
     * @param invitees      chosen event invitees.
     * @param note          note for event.
     */
    private void setFieldVars(final String startTime, final String endTime, final String location,
                              final String invitees, final String note) {
        this.startTimeVar = startTime;
        this.endTimeVar = endTime;
        this.location = location;
        this.invitees = invitees;
        this.note = note;
    }

    /**
     * Create a toast to let the user know the event was created.
     * @param title         title of the created event.
     */
    private void toastEventCreated(final String title) {
        Toast.makeText(this.getApplicationContext(), title + " event created.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Prompts the user to enter the name of the event.
     */
    private void promptEnterEventName() {
        final InputNameDialogFragment dialogFragment = new InputNameDialogFragment();
        dialogFragment.show(getFragmentManager(), "inputEventName");
    }

    /**
     * Sets the default day and time for date and time picker.
     */
    private void setDefaultDateAndTime() {
        final TextView endTimeBox = (TextView) this.findViewById(R.id.event_end_time);
        final TextView startTimeBox = (TextView) this.findViewById(R.id.event_start_time);
        final TextView startDateBox = (TextView) this.findViewById(R.id.start_date);
        final TextView endDateBox = (TextView) this.findViewById(R.id.end_date);
        final Intent intent = getIntent();
        // Fill out the date and time parameters on start of activity based on day selected and current time.
        final int day = Integer.parseInt(intent.getStringExtra("day"));
        final int month = Integer.parseInt(intent.getStringExtra("month"));
        final int year = Integer.parseInt(intent.getStringExtra("year"));
        setBothDateVars(String.valueOf(year), String.valueOf(month), String.valueOf(day));
        setCalendarDate(year, month, day); // Update the calendar object to reflect current information.
        this.calendar.add(Calendar.HOUR_OF_DAY, 1); // Move forward one hour.
        this.startTimeVar = DateFormat.format("HH:" + "00", this.calendar).toString(); // Set default start time.
        this.calendar.add(Calendar.HOUR_OF_DAY, 1); // Move forward one hour.
        this.endTimeVar = DateFormat.format("HH:" + "00", this.calendar).toString(); // Set default end time.
        final String monthStr = DateFormat.format("MMM", this.calendar).toString();
        final String dayStr = DateFormat.format("dd", this.calendar).toString();
        final String weekDay = DateFormat.format("EEEE", this.calendar).toString();
        final String yearStr = DateFormat.format("yyyy", this.calendar).toString();
        // Generate the date information string.
        final String dateString = generateDateString(weekDay, dayStr, monthStr, yearStr);
        // Place date information string in both the star and end date fields.
        startDateBox.setText(dateString);
        endDateBox.setText(dateString);
        startTimeBox.setText(this.startTimeVar); // Set the default start time for field.
        endTimeBox.setText(this.endTimeVar); // Set the default end time for field.
    }

    /**
     * Sets the date variables.
     * @param startYear     start year of event.
     * @param startMonth    end year of event.
     * @param startDay      start day of event.
     */
    private void setBothDateVars(final String startYear, final String startMonth, final String startDay) {
        this.startYearVar = startYear;
        this.startMonthVar = startMonth;
        this.startDayVar = startDay;
        setEndDateVars(startYear, startMonth, startDay);
    }

    /**
     * Sets the end date variables.
     * @param endYear       end year of event.
     * @param endMonth      end month of event.
     * @param endDay        end day of event.
     */
    private void setEndDateVars(final String endYear, final String endMonth, final String endDay) {
        this.endYearVar = endYear;
        this.endMonthVar = endMonth;
        this.endDayVar = endDay;
    }

    /**
     * Generates a date string of format: Weekday, XX Month XXXX.
     * @param weekDay       string representation of weekday.
     * @param dayOfMonth    string representation of day of month.
     * @param month         string representation of month.
     * @param year          string representation of year.
     * @return              a date string.
     */
    private String generateDateString(final String weekDay, final String dayOfMonth, final String month,
                                      final String year) {
        return weekDay + ", " + dayOfMonth + " " + month + " " + year;
    }

    /**
     * Sets the date for the calendar object.
     * @param year          year for the calendar.
     * @param month         month for the calendar.
     * @param dayOfMonth    day of month for the calendar.
     */
    private void setCalendarDate(final int year, final int month, final int dayOfMonth) {
        this.calendar.set(Calendar.YEAR, year);
        this.calendar.set(Calendar.MONTH, month);
        this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    /**
     * Sets the time for the calendar object.
     * @param hour      the hour of event.
     * @param minute    the minute of event.
     */
    private void setCalendarTime(final int hour, final int minute) {
        this.calendar.set(Calendar.HOUR_OF_DAY, hour);
        this.calendar.set(Calendar.MINUTE, minute);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.setResult(Constants.REQUEST_EXIT);
    }

    /**
     * Dialogue fragment class for creating a pop up window.
     */
    public static class InputNameDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.request_event_name)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int id) {
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
