/*
* On opening the app, a rest query is made to get 40 Dog photos. DownloadTask creates an Async Task
* to open the HTTP connection and fetch JSON in background thread. Meanwhile, a progress bar is
* shown while the data is being fetched. JSON is parsed and data is added to feedslist array.
* */

package com.example.shiprajain.doosk;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<FeedItem> mfeedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        new DownloadTask().execute();
    }

    public class DownloadTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20flickr.photos.search(40)%20where%20text%3D%22Dog%22%20%20and%20api_key%3D%22d5c7df3552b89d13fe311eb42715b510%22&format=json&diagnostics=true&callback=";
            return QueryFlickr(url);
        }

        @Override
        protected void onPostExecute(Integer result) {
            mProgressBar.setVisibility(View.GONE);

            if (result == 1) {
                mAdapter = new MyRecyclerViewAdapter(MainActivity.this, mfeedsList);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONObject json_query = response.getJSONObject("query");
            JSONObject json_results = json_query.getJSONObject("results");
            JSONArray photos = json_results.getJSONArray("photo");
            mfeedsList = new ArrayList<>();

            for (int i = 0; i < photos.length(); i++) {
                JSONObject photo = photos.getJSONObject(i);
                FeedItem f = new FeedItem(photo.optString("farm"),
                        photo.optString("server"),
                        photo.optString("id"),
                        photo.optString("secret"));
                mfeedsList.add(f);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Integer QueryFlickr(String urlstring){
        String qResult;
        Integer result = 0;
        HttpURLConnection urlConnection;
        try {
            URL url = new URL(urlstring);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String stringReadLine = null;
                while ((stringReadLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(stringReadLine + "\n");
                }

                qResult = stringBuilder.toString();
                parseResult(qResult);
                result = 1;
            }else {
                result = 0; //"Failed to fetch data!";
            }
        } catch (Exception e) {
            Log.d("Network Error", e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
