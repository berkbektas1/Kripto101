package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kripto101.R;

public class LoginActivity extends AppCompatActivity {

    Animation topAnim;

    private ImageView imageHeaderLogo;
    private EditText inputEmail, inputPassword;
    TextView textSignUp, textForgot;
    Button btnSignIn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //APP Status Bar Color
        Window w = getWindow();
        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        w.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBackground));


        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        imageHeaderLogo = findViewById(R.id.loginHeaderLogo);
        textSignUp = findViewById(R.id.textSignUp);
        textForgot = findViewById(R.id.textForgot);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.buttonSignIn);
        progressBar = findViewById(R.id.signInProgressBar);

        imageHeaderLogo.setAnimation(topAnim);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                //Kaydı al
                try {


                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                //Intent
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                progressBar.setVisibility(View.INVISIBLE);


            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });



    }



    public void getForget(View view){
        Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
        startActivity(intent);
    }
}
