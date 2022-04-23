package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;

import java.util.prefs.PreferenceChangeEvent;

public class DetailActivity2 extends AppCompatActivity {

    private TextView textEduTitle, textArticle;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        preferenceManager = new PreferenceManager(getApplicationContext());
        textEduTitle = findViewById(R.id.textEduName);
        textArticle = findViewById(R.id.textArticle);

        textEduTitle.setText(preferenceManager.getString(Constants.KEY_EDU_NAME));
        textArticle.setText(Html.fromHtml("<b>Lorem</b> Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. www.berkbektas.com " +
                "<br> <b>Lorem</b> Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer " +
                "took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, " +
                "but also the leap into electronic typesetting, remaining essentially unchanged. " + "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."+
                "but also the leap into electronic typesetting, remaining essentially unchanged."));

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
