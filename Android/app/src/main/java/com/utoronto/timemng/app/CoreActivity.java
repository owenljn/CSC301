package com.utoronto.timemng.app;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.utoronto.timemng.descriptor.Event;

import java.io.IOException;


public class CoreActivity extends Activity {
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    GoogleCloudMessaging gcm;
    String regid;
    Context context;
    int i = 0;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "156524885885";

    private static final String TAG = "c2dm_app";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_core);

        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            Log.d(TAG, "Google Play has been found!");

            context = getApplicationContext();

            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            Log.d(TAG, "Registration Id: "+ regid);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        }
        // Show current tasks, pulled from server's DB
        // (receive payload of all events, show on phone. is easy to implement, but no server has been implemented yet)
        
        // TODO: could add some layout actions here
        addStuff(new Event("Event1", 1, "Date")); 
        // How are we going to pass event information?
        // How is it sent here?
        // GCM sends message, which is parsed
        // 
    }

    
    private void addStuff(final Event event) {
    	//I copied all of this from http://androiddesk.wordpress.com/2012/08/05/creating-dynamic-views-in-android/
        final ScrollView scrl=new ScrollView(this);
        final LinearLayout ll=new LinearLayout(this);
        final LinearLayout popLayout = new LinearLayout(this);
        
        TextView popText = new TextView(this);
        popText.setText("Enter your task");
        popLayout.setOrientation(LinearLayout.VERTICAL);
        popLayout.addView(popText);
        
        ll.setOrientation(LinearLayout.VERTICAL);
        
        scrl.addView(ll);
        
        //Alertbox stuff

        // create button
        final Button add_btn=new Button(this);
        add_btn.setText("Create Task");
        ll.addView(add_btn);
        add_btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		addPopUp(popLayout,scrl,ll);
        	}});
        this.setContentView(scrl);
	}
    
    public void addPopUp(final LinearLayout popLayout, final ScrollView scrl,final LinearLayout ll){
    	
    	//Create popup
        final PopupWindow pw = new PopupWindow(popLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //Set params
        pw.setContentView(popLayout);
        pw.setFocusable(true);
        
        // Parameters 
        
        View contentView = pw.getContentView();

        final EditText name=new EditText(getApplicationContext());
        name.setHint("Name");
        popLayout.addView(name);
        
        final EditText date=new EditText(getApplicationContext());
        date.setHint("date");
        popLayout.addView(date);

        // create add stuff here button
        final Button showStuff=new Button(getApplicationContext());
        showStuff.setText("Add");
        popLayout.addView(showStuff);
        pw.showAtLocation(scrl,Gravity.TOP,0, 200);
        showStuff.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				addButton(popLayout,scrl,ll,name,date);
				pw.dismiss();
				popLayout.removeAllViews();
			}

        });
        
        // create close button
        final Button closeButton=new Button(getApplicationContext());
        closeButton.setText("Close");
        popLayout.addView(closeButton);
        closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pw.dismiss();
				popLayout.removeAllViews();
			}
        });
    }
    
    private void addButton(final LinearLayout popLayout, final ScrollView scrl,final LinearLayout ll,EditText name, EditText date){
		// Dynamically add stuff here
		final LinearLayout eventView = new LinearLayout(getApplicationContext());
		eventView.setMinimumWidth(ll.getWidth());
		eventView.setOrientation(LinearLayout.HORIZONTAL);
		
        final TextView nametv=new TextView(getApplicationContext());
        nametv.setText("Event: "+ name.getText());
        nametv.setTextColor(Color.BLUE);
        eventView.addView(nametv);
        
		
        final TextView datetv=new TextView(getApplicationContext());
        datetv.setText(" At Date: " + date.getText());
        datetv.setTextColor(Color.RED);
        eventView.addView(datetv);
        
        final Button deleteStuff=new Button(getApplicationContext());
        deleteStuff.setText("Delete");
        deleteStuff.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		eventView.removeAllViews();
        	}
        	
        });
        final Button updateStuff=new Button(getApplicationContext());
        updateStuff.setText("Update");
        updateStuff.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		
        		editPopUp(popLayout,scrl,ll,eventView, nametv, datetv);
        	}
        	
        });
        eventView.addView(updateStuff);
        eventView.addView(deleteStuff);
        //Add info to server's DB here 
        ll.addView(eventView);
    }

    /*
     *  I think the only critique that I could make is that there should be
     *  more comments to make this easier to understand. The code itself though,
     *  is very well done. I'm a bit rusty on dynamic creation of views/buttons, etc.
     *  so this cleared up a lot of the questions that I had about the process. For example,
     *  I had no idea how to specify where on a view a button should be displayed.
     */
	private void editPopUp(final LinearLayout popLayout, final ScrollView scrl,
			final LinearLayout ll, final LinearLayout targEvent, final TextView nametv, final TextView datetv) {
		
		//Create popup
        final PopupWindow pw = new PopupWindow(popLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //Set params
        pw.setContentView(popLayout);
        pw.setFocusable(true);
        
        // Parameters 
        
        View contentView = pw.getContentView();
        String tvname = ((String) nametv.getText()).substring(7);
        String tvdate = ((String) datetv.getText()).substring(10);
        
        
        final EditText name=new EditText(getApplicationContext());
        name.setText(tvname);
        popLayout.addView(name);
        
        final EditText date=new EditText(getApplicationContext());
        date.setText(tvdate);
        popLayout.addView(date);

        // create add stuff here button
        final Button showStuff=new Button(getApplicationContext());
        showStuff.setText("Update");
        popLayout.addView(showStuff);
        pw.showAtLocation(scrl,Gravity.CENTER,0, 0);
        showStuff.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				//addButton(popLayout,scrl,ll,name,date);
				datetv.setText(" At Date: " + date.getText());
				nametv.setText("Event: " + name.getText());
				pw.dismiss();
				popLayout.removeAllViews();
			}

        });
        
        // create close button
        final Button closeButton=new Button(getApplicationContext());
        closeButton.setText("Close");
        popLayout.addView(closeButton);
        closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pw.dismiss();
				popLayout.removeAllViews();
			}
        });
	}



	private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(CoreActivity.class.getSimpleName(),  Context.MODE_PRIVATE);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    @SuppressWarnings("ConstantConditions")
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    @SuppressWarnings("unchecked")
    private void registerInBackground() {
        new AsyncTask() {
            @Override
            protected String doInBackground(final Object[] params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    Log.d(TAG, msg);

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

        }.execute(null, null, null);
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coremenu, menu);

        Log.d(TAG, "Started...");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
