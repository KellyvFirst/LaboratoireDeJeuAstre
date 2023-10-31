package com.example.laboratoirejeuastre;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements StartScreen.OnControlSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StartScreen startscreen = new StartScreen(this, this);
        setContentView(startscreen);
    }

    @Override
    public void onControlSelected(String controlType) {
        Intent intent = new Intent(this, SolarSystemActivity.class);
        intent.putExtra("controlMode", controlType);
        startActivity(intent);
    }
}
