package com.example.carparking1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParkingSlot extends AppCompatActivity {

    private ImageView image1, image2, image3;
    long animationDuration = 500;//miliseconds

    Button regSlot1;
    Button regSlot2;
    Button regSlot3;

    int slot_1;
    int slot_2;
    int slot_3;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    private View imageofcaroutside;
    private View imageofcarinside;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_slot);

        image1 = (ImageView) findViewById(R.id.car_image1);
        image2 = (ImageView) findViewById(R.id.car_image2);
        image3 = (ImageView) findViewById(R.id.car_image3);

        regSlot1 = findViewById(R.id.slot1);
        regSlot2 = findViewById(R.id.slot2);
        regSlot3 = findViewById(R.id.slot3);

        ParkingHelperClass parkingHelperClass = new ParkingHelperClass(slot_1, slot_2 ,slot_3);
        FirebaseDatabase.getInstance().getReference("ParkingStatus")
                .child(String.valueOf(FirebaseDatabase.getInstance().getReference("Slot1")))
                .child(String.valueOf(FirebaseDatabase.getInstance().getReference("Slot2")))
                .child(String.valueOf(FirebaseDatabase.getInstance().getReference("Slot3")))
                .setValue(parkingHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (slot_1 == 0) {
                        imageofcaroutside.setVisibility(View.VISIBLE);
                        imageofcarinside.setVisibility(View.GONE);
                    } else if (slot_1 == 1) {
                        imageofcaroutside.setVisibility(View.GONE);
                        imageofcarinside.setVisibility(View.VISIBLE);
                    }
                    if (slot_2 == 0) {
                        imageofcaroutside.setVisibility(View.VISIBLE);
                        imageofcarinside.setVisibility(View.GONE);
                    } else if (slot_2 == 1) {
                        imageofcaroutside.setVisibility(View.GONE);
                        imageofcarinside.setVisibility(View.VISIBLE);
                    }
                    if (slot_3 == 0) {
                        imageofcaroutside.setVisibility(View.VISIBLE);
                        imageofcarinside.setVisibility(View.GONE);
                    } else if (slot_3 == 1) {
                        imageofcaroutside.setVisibility(View.GONE);
                        imageofcarinside.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void callStartScreen(View view) {
        Intent intent = new Intent(ParkingSlot.this, StartScreen.class);
        startActivity(intent);
    }

}