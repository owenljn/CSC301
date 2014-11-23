package com.utoronto.timemng.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.utoronto.timemng.calendar.CalendarMonthConstructor;
import com.utoronto.timemng.daytasks.EventListConstructor;

import java.util.Calendar;

/**
 * An activity containing the list of events for the selected day.
 */
public class TaskListActivity extends Activity {
    private EventListConstructor taskList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        // Keep orientation portrait for now.
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Intent intent = getIntent();
        // Get the day that this activity is constructed for.
        final int dayOfMonth = Integer.parseInt(intent.getStringExtra("day"));
        final int year = Integer.parseInt(intent.getStringExtra("year"));
        final int month = Integer.parseInt(intent.getStringExtra("month"));
        final CalendarMonthConstructor calendarMonthConstructor = CalendarMonthConstructor.getInstance();
        // Construct the task list.
        this.taskList = new EventListConstructor(this, calendarMonthConstructor.getEventMap()
                .get(dayOfMonth), year, month, dayOfMonth);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.taskmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_new:
                return true;
            case R.id.action_prev:
                return true;
            case R.id.action_next:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//
//    private Calendar createCalendarObject() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, ())
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
