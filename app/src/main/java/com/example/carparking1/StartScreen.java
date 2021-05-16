package com.example.carparking1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

    }

    public void callParkingSlot(View view) {
        Intent intent = new Intent(getApplicationContext(),ParkingSlot.class);
        startActivity(intent);
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }
}