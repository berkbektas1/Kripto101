package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kripto101.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView imageLogo;
    EditText inputUserName, inputEmail, inputPassword;
    TextView textSignIn;
    Animation topAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //APP Status Bar Color
        Window w = getWindow();
        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        w.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBackground));

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        imageLogo = findViewById(R.id.logo);

        textSignIn = findViewById(R.id.textSignIn);
        inputUserName = findViewById(R.id.inputUserName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        imageLogo.setAnimation(topAnim);



        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }

    public void getForgetActivity(View view){
        Intent intent = new Intent(getApplicationContext(), ForgotActivity.class);
        startActivity(intent);
    }
}
