package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.prefs.PreferenceChangeEvent;

public class DetailActivity2 extends AppCompatActivity {

    private TextView textEduName, textSubName, textAuthor, textDescription;
    private ImageView imageConcept, ic_mark;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        preferenceManager = new PreferenceManager(getApplicationContext());

        textEduName = findViewById(R.id.textEduName);
        textSubName = findViewById(R.id.textSubName);
        textDescription = findViewById(R.id.textDescription);
        textAuthor = findViewById(R.id.textAuthor);
        imageConcept = findViewById(R.id.imageConcept);
        ic_mark = findViewById(R.id.ic_mark);

        Intent i = getIntent();
        //getData
        String title = i.getStringExtra("title");
        String subTitle = i.getStringExtra("subTitle");
        String author = i.getStringExtra("author");
        String description = i.getStringExtra("description");
        String image = i.getStringExtra("image");


        textEduName.setText(title);
        textSubName.setText(subTitle);
        textAuthor.setText(author);
        textDescription.setText(Html.fromHtml(description));
        Picasso.get().load(image).into(imageConcept);

        getMarkerColor(preferenceManager.getIntPosition(Constants.KEY_EDU_POSITION));
    }

    public void getMarkerColor(int position){
        Log.d("color", String.valueOf(position));
        if (position % 6 == 0) {
            ic_mark.setColorFilter(getColor(R.color.colorEducations0));
        } else if (position % 6 == 1) {
            ic_mark.setColorFilter(getColor(R.color.colorEducations1));
        } else if (position % 6 == 2) {
            ic_mark.setColorFilter(getColor(R.color.colorEducations2));
        } else if (position % 6 == 3) {
            ic_mark.setColorFilter(getColor(R.color.colorEducations3));
        } else if (position % 6 == 4) {
            ic_mark.setColorFilter(getColor(R.color.colorEducations4));
        } else if (position % 6 == 5) {
            ic_mark.setColorFilter(getColor(R.color.colorEducations5));
        }
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
