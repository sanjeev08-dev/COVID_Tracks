package com.sanjeev.covidtracks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    TextView casesTV, recoveredTV, criticalTV, activeTV, todaycasesTV, totaldeathsTV, todaydeathsTV, affectedcountriesTV;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    Button btn_track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casesTV = findViewById(R.id.casesTV);
        recoveredTV = findViewById(R.id.recoveredTV);
        criticalTV = findViewById(R.id.criticalTV);
        activeTV = findViewById(R.id.activeTV);
        todaycasesTV = findViewById(R.id.todaycasesTV);
        totaldeathsTV = findViewById(R.id.totaldeathsTV);
        todaydeathsTV = findViewById(R.id.todaydeathsTV);
        affectedcountriesTV = findViewById(R.id.affectedcountriesTV);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollView);
        pieChart = findViewById(R.id.piechart);

        btn_track = findViewById(R.id.btn_track);
        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTrackCountries(v);
            }
        });

        fetchData();
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    casesTV.setText(jsonObject.getString("cases"));
                    recoveredTV.setText(jsonObject.getString("recovered"));
                    criticalTV.setText(jsonObject.getString("critical"));
                    activeTV.setText(jsonObject.getString("active"));
                    todaycasesTV.setText(jsonObject.getString("todayCases"));
                    totaldeathsTV.setText(jsonObject.getString("deaths"));
                    todaydeathsTV.setText(jsonObject.getString("todayDeaths"));
                    affectedcountriesTV.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(casesTV.getText().toString().trim()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(recoveredTV.getText().toString().trim()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(totaldeathsTV.getText().toString().trim()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(activeTV.getText().toString().trim()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error : "+error.getMessage(), Toast.LENGTH_LONG).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void goTrackCountries(View v) {
        startActivity(new Intent(MainActivity.this,AffectedCountriesActivity.class));
    }
}
