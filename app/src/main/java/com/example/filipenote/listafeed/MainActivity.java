package com.example.filipenote.listafeed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    List lstFeed;
    ListView lvFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy ( policy );

        lstFeed = new ArrayList<FeedDados>();
        lvFeed = (ListView) findViewById(R.id.listFeed);

        if(hasActiveInternetConnection(this)) {

            alimentarLista();

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            alimentarLista();

                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 3000);
                }
            });

            lvFeed.setOnScrollListener(new AbsListView.OnScrollListener(){

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem == 0) {
                        mSwipeRefreshLayout.setEnabled(true);
                    }else {
                        mSwipeRefreshLayout.setEnabled(false);

                    }

                    };
                });

        }


    }

    private void alimentarLista() {

        Log.d("DADOS",""+"TATENDO");

        String response = makeRequest("http://52.27.21.4/api/feed");

        try {
            JSONArray jsonArray = new JSONArray(response);
            lstFeed.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                FeedDados fd = new FeedDados();

                fd.setImgUrl("ImgPadrao");

                if (jsonArray.getJSONObject(i).getInt("type") == 1 ||
                        jsonArray.getJSONObject(i).getInt("type") == 2) {
                    fd.setImgUrl(jsonArray.getJSONObject(i).getString("image"));
                }

                fd.setTitle(jsonArray.getJSONObject(i).getString("title"));
                fd.setDescricao(jsonArray.getJSONObject(i).getString("description"));

                lstFeed.add(fd);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        lvFeed.setAdapter(new CustomAdapter(this, lstFeed));
    }


    private String makeRequest ( String urlAddress ) {
        HttpURLConnection con = null ;
        URL url = null ;
        String response = null ;
        try {
            url = new URL( urlAddress );
            con = (HttpURLConnection) url.openConnection ();
            response = readStream ( con.getInputStream ());
        } catch ( Exception e) {
            e.printStackTrace ();
        } finally {
            con.disconnect ();
        }
        return response ;
    }

    private String readStream ( InputStream in) {
        BufferedReader reader = null ;
        StringBuilder builder = new StringBuilder ();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null ;
            while (( line = reader.readLine ()) != null ) {
                builder.append ( line + "\n");
            }
        } catch ( IOException e) {
            e.printStackTrace ();
        } finally {
            if ( reader != null ) {
                try {
                    reader.close ();
                } catch ( IOException e) {
                    e.printStackTrace ();
                }
            }
        }

        return builder.toString ();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://52.27.21.4/api/feed").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("TEM NET?", "Error checking internet connection", e);
            }
        } else {
            Log.d("TEM NET?", "No network available!");
        }
        return false;
    }


}
