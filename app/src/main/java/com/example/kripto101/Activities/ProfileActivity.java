package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kripto101.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textTitle = findViewById(R.id.textTitle);
        textTitle.setText("PROFILE");



    }

    public void getBackButton(View view){
        onBackPressed();
        finish();
    }

}
