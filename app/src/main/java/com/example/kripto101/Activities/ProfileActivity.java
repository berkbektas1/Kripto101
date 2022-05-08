package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private TextView textName, textEmail, textRegDate, textLevel;
    private Button btnExit;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textRegDate = findViewById(R.id.textRegistrationDate);
        textLevel = findViewById(R.id.textLevel);
        btnExit = findViewById(R.id.btnExit);

        preferenceManager = new PreferenceManager(getApplicationContext());

        textName.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
        textEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        textRegDate.setText(preferenceManager.getString(Constants.KEY_REG_DATE));
        textLevel.setText(preferenceManager.getString(Constants.KEY_LEVEL_ACCOUNT));


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.clearPreferences();
                FirebaseAuth.getInstance().signOut();
                Log.d("Log", "LogOut");
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                finishAffinity();
            }
        });



    }

    public void getBackButton(View view){
        onBackPressed();
        finish();
    }

}
