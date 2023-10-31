package com.example.laboratoirejeuastre;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SolarSystemActivity extends AppCompatActivity {

    private String controlMode;
    private SolarSystemView solarSystemView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        controlMode = getIntent().getStringExtra("controlMode");
        solarSystemView = new SolarSystemView(this, controlMode);
        solarSystemView.initializePlanets();
        setContentView(solarSystemView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("accelerometer".equals(controlMode)) {
            solarSystemView.registerAccelerometerListener();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ("accelerometer".equals(controlMode)) {
            solarSystemView.unregisterAccelerometerListener();
        }
    }




}

