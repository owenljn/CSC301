package com.utoronto.timemng.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import com.utoronto.timemng.calendar.CalendarMonthConstructor;
import com.utoronto.timemng.daytasks.EventListConstructor;

/**
 * An activity containing the list of events for the selected day.
 */
public class TaskListActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        // Keep orientation portrait for now.
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Intent intent = getIntent();
        // Get the day that this activity is constructed for.
        final int dayOfMonth = Integer.parseInt(intent.getStringExtra("day"));
        final CalendarMonthConstructor calendarMonthConstructor = CalendarMonthConstructor.getInstance();
        // Construct the task list.
        new EventListConstructor(this, calendarMonthConstructor.getEventMap().get(dayOfMonth));
    }

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
