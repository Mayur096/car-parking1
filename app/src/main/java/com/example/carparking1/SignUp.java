package com.example.carparking1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout regName, regEmail, regPhoneNo, regPassword;
    TextView slogan_name;
    Button regBtn;
    ProgressBar progressbar;

    private FirebaseAuth mAuth;
    private FirebaseAnalytics mfirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mfirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        slogan_name = (TextView) findViewById(R.id.slogan_name);
        slogan_name.setOnClickListener(this);

        regBtn = (Button) findViewById(R.id.SignUp);
        regBtn.setOnClickListener(this);

        regEmail = findViewById(R.id.reg_email);
        regName =  findViewById(R.id.reg_name);
        regPassword= findViewById(R.id.reg_password);
        regPhoneNo = findViewById(R.id.reg_phoneNo);

        progressbar= findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slogan_name:
                startActivity(new Intent(this,StartScreen.class));
                break;
            case R.id.SignUp:
                regBtn();
                break;
        }
    }

    private void regBtn() {
        String email = regEmail.getEditText().getText().toString().trim();
        String name = regName.getEditText().getText().toString().trim();
        String password = regPassword.getEditText().getText().toString().trim();
        String phoneNo = regPhoneNo.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            regName.setError("Name is required");
            regName.requestFocus();
            return;
        }

        if (phoneNo.isEmpty()) {
            regPhoneNo.setError("Phone No is required");
            regPhoneNo.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            regEmail.setError("Email is required");
            regEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            regEmail.setError("Please provide valid email");
            regEmail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            regPassword.setError("Password is required");
            regPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            regPassword.setError("Minimum password length should be 8 characters");
            regPassword.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserHelperClass user = new UserHelperClass(name,email,password,phoneNo);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, email);
                                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
                                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                                    mfirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "SignUp Successfully..", Toast.LENGTH_LONG).show();
                                        progressbar.setVisibility(View.VISIBLE);
                                        startActivity(new Intent(SignUp.this, LoginActivity.class));

                                    } else {
                                        Toast.makeText(SignUp.this, "SignUp Failed! Try again", Toast.LENGTH_LONG).show();
                                        progressbar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUp.this, "SignUp Failed", Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(SignUp.this,LoginActivity.class);
        startActivity(intent);
    }

    public FirebaseAnalytics getMfirebaseAnalytics() {
        return mfirebaseAnalytics;
    }
}