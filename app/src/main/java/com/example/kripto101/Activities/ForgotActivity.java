package com.example.kripto101.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kripto101.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private ProgressBar progressBar;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        inputEmail = findViewById(R.id.inputEmail);
        btnReset = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBarReset);

        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    public void resetPassword(){
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty()){
            inputEmail.setError("Email is required.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email address.");
            return;
        }

        btnReset.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this, "Check your email to reset your password!",  Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgotActivity.this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void closeActivity(View view){
        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
