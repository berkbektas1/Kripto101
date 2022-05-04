package com.example.kripto101.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import com.example.kripto101.Models.User;
import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputUserName, inputEmail, inputPassword, inputConfirmPassword;
    private Button btnSignUp;
    private TextView textSignIn;

    private ProgressBar progressBar;
    private PreferenceManager preferenceManager;

    private FirebaseAuth fAuth;

    Animation topAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        textSignIn = findViewById(R.id.textSignIn);
        inputUserName = findViewById(R.id.inputUserName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);

        btnSignUp = findViewById(R.id.buttonSignUp);
        progressBar = findViewById(R.id.progressBar);

        preferenceManager = new PreferenceManager(getApplicationContext());

        fAuth = FirebaseAuth.getInstance();


        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user data the form
                userRegister();
            }
        });

    }

    private void userRegister(){
        final String fullName = inputUserName.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confPas = inputConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            inputUserName.setError("Name is required.");
            return;
        }
        if (!(fullName.length() >= 3)) {
            inputUserName.setError("Min 3 characters.");
            return;
        }
        if (email.isEmpty()) {
            inputEmail.setError("Email is required.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email address.");
            return;
        }
        if (password.isEmpty()) {
            inputPassword.setError("Password is required.");
            return;
        }
        if (!(password.length() >= 8)) {
            inputPassword.setError("Password min 8 characters.");
            return;
        }
        if (confPas.isEmpty()) {
            inputConfirmPassword.setError("Confirm Password is required.");
            return;
        }
        if (!password.equals(confPas)) {
            inputConfirmPassword.setError("Password Do Not Match.");
            return;
        }

        // firebase save data

        btnSignUp.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    final String regDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                    User user = new User(fullName, email, regDate, "Base");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    database.getReference(Constants.KEY_COLLECTION_USERS)
                            .child(fAuth.getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                            preferenceManager.putString(Constants.KEY_FULL_NAME, fullName);
                            preferenceManager.putString(Constants.KEY_EMAIL, email);
                            preferenceManager.putString(Constants.KEY_REG_DATE, regDate);
                            preferenceManager.putString(Constants.KEY_LEVEL_ACCOUNT, "Base");
                            Log.d("Log", "Create Account");

                            Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            btnSignUp.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btnSignUp.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getForgetActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotActivity.class);
        startActivity(intent);
        finish();
    }
}
