package com.example.carparking1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile
        );

        signOut = (Button) findViewById(R.id.logout);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(User_Profile.this, StartScreen.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = findViewById(R.id.greeting);
        final TextView NameTextView = findViewById(R.id.Name);
        final TextView EmailTextView = findViewById(R.id.EmailAddress);
        final TextView PhoneNoTextView = findViewById(R.id.PhoneNo);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userProfile = snapshot.getValue(UserHelperClass.class);

                if (userProfile != null) {
                    String Name = userProfile.name;
                    String Email = userProfile.email;
                    String PhoneNo = userProfile.phoneNo;

                    greetingTextView.setText("Welcome, " + Name + "!");
                    NameTextView.setText(Name);
                    EmailTextView.setText(Email);
                    PhoneNoTextView.setText(PhoneNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Profile.this, "Something wrong happened", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callParkingSlot(View view) {
        startActivity(new Intent(User_Profile.this, ParkingSlot.class));
    }
}