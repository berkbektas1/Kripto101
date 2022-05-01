package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;

import java.util.prefs.PreferenceChangeEvent;

public class DetailActivity2 extends AppCompatActivity {

    private TextView textEduName, textSubName, textAuthor, textConcept;
    private ImageView imageConcept, ic_mark;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        preferenceManager = new PreferenceManager(getApplicationContext());

        textEduName = findViewById(R.id.textEduName);
        textSubName = findViewById(R.id.textSubName);
        textConcept = findViewById(R.id.textConcept);
        textAuthor = findViewById(R.id.textAuthor);
        imageConcept = findViewById(R.id.imageConcept);
        ic_mark = findViewById(R.id.ic_mark);

        textEduName.setText("Price Action");
        textSubName.setText("Range Trades");
        textAuthor.setText("Author: @berkbektas");

        ic_mark.setColorFilter(getColor(R.color.colorEducations1));

    }


    public void getBackButton(View view){
        onBackPressed();
        finish();
    }

    public void getPrevious(View view){

    }

    public void getVideo(View view){

    }

    public void getNext(View view){

    }

}
