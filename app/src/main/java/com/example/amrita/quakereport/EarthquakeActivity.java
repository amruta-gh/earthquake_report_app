package com.example.amrita.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        // Create a fake list of earthquake locations.
//      final ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
//        earthquakes.add(new Earthquake("7.2", "San Francisco","Feb 2, 2016"));
//        earthquakes.add(new Earthquake("6.1", "London","July 20, 2015"));
//        earthquakes.add(new Earthquake("3.9", "Tokyo","Nov 10, 2014"));
//        earthquakes.add(new Earthquake("5.4", "Mexico City","May 3, 2014"));
//        earthquakes.add(new Earthquake("2.8", "Moscow","Jan 31, 2013"));
//        earthquakes.add(new Earthquake("4.9", "Rio de Janeiro ","Aug 19, 2012"));
//        earthquakes.add(new Earthquake("1.6", "Paris","Oct 30, 2011"));


        // Get the list of earthquakes from {@link QueryUtils}
        ArrayList<Earthquake> earthquakes = null;
        try {
            earthquakes = QueryUtils.extractEarthquakes();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the list of earthquakes as input
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

}