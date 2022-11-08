package com.example.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String ,Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL (urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){

                    char current = (char) data ;
                    result += current ;

                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        protected  void onPostExecute(String result){

            // to process the data
            // to convert string to json data

            try {
                JSONObject jasonobjectsting = new JSONObject(result);
                // extract perticular parts of the string

                String weatherinfo = jasonobjectsting.getString("weather");

                // we have a huge dtring of jason objects in them and they need to be obtained seperately
                // we need to convert the string to the json array to get the elements of ehat was in it

                JSONArray jasonarray = new JSONArray(weatherinfo);

                // use for loop for going throght the string array
                //[{" id" = 521," main " :" description" : " prximity" , "icon": "09d" }, {.. same as previous }]
                // need to get each single part as seperate jason bject hence this

                for (int i = 0; i< jasonarray.length() ; i++){

                    JSONObject jasonpart1 =  jasonarray.getJSONObject(i);
                    // ust logging them for now

                    Log.i (" main", jasonpart1.getString("main"));
                    Log.i ("description", jasonpart1.getString("description"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("website content", result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        task.execute("https://api.openweather.org/data/2.5/weather?q=London,uk");
    }
}
