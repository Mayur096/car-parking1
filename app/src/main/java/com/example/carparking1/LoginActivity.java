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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout regEmail, regPassword;
    TextView logo;
    Button userlogin, forgotPassword;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = (TextView) findViewById(R.id.slogan_name);
        logo.setOnClickListener(this);

        userlogin = (Button) findViewById(R.id.Login);
        userlogin.setOnClickListener(this);

        regEmail = (TextInputLayout) findViewById(R.id.reg_email);
        regPassword= (TextInputLayout) findViewById(R.id.reg_password);

        progressBar= findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logo:
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.Login:
                userlogin();
                break;

            case R.id.forgot_password:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userlogin() {
        String email = regEmail.getEditText().getText().toString().trim();
        String password = regPassword.getEditText().getText().toString().trim();

        if (email.isEmpty()){
            regEmail.setError("Email is required");
            regEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regEmail.setError("Please enter a valid email");
            regEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            regPassword.setError("Password is required");
            regPassword.requestFocus();
            return;
        }

        if (password.length() < 8){
            regPassword.setError("Min password length is 8 characters!");
            regPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //redirect to user profile
                    startActivity(new Intent(LoginActivity.this, User_Profile.class));

                }else{
                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your cradentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void callForgetPassword(View view) {
    }

    public void callsignup(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
    }
}