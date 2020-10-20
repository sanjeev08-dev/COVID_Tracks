package com.sanjeev.covidtracks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CountryDetailsActivity extends AppCompatActivity {
    TextView casesTV, recoveredTV, criticalTV, activeTV, todaycasesTV, deathsTV, todaydeathsTV;
    ImageView flagImage;
    private int positionCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);



        casesTV = findViewById(R.id.casesTV);
        recoveredTV = findViewById(R.id.recoveredTV);
        criticalTV = findViewById(R.id.criticalTV);
        activeTV = findViewById(R.id.activeTV);
        todaycasesTV = findViewById(R.id.todaycasesTV);
        deathsTV = findViewById(R.id.deathsTV);
        todaydeathsTV = findViewById(R.id.todaydeathsTV);
        flagImage = findViewById(R.id.flagImage);


        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        Glide.with(this).load(AffectedCountriesActivity.countryModelList.get(positionCountry).getFlag()).into(flagImage);

        casesTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getCases());
        recoveredTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getRecovered());
        criticalTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getCritical());
        activeTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getActive());
        todaycasesTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getTodayCases());
        deathsTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getDeaths());
        todaydeathsTV.setText(AffectedCountriesActivity.countryModelList.get(positionCountry).getTodayDeaths());

        getSupportActionBar().setTitle(AffectedCountriesActivity.countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
