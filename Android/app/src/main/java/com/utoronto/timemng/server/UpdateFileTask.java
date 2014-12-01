package com.utoronto.timemng.server;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * 01/12/2014 00:11.
 */
public class UpdateFileTask extends AsyncTask<ArrayList<NameValuePair>, Integer, Long> {
    protected Long doInBackground(ArrayList<NameValuePair>... vars) {
        ArrayList<NameValuePair> nameValuePairs = vars[0];
        //http post
        try {
            //Syste m.o ut.prin tln(1/0);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://54.85.221.124/updateEvent.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            //entity.consumeContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
            return null;
        }
        return null;
    }
}
