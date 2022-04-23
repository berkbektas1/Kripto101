package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kripto101.Adapters.WordsAdapter;
import com.example.kripto101.EducationOnClickedListener;
import com.example.kripto101.Models.WordsModel;
import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;

import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity implements EducationOnClickedListener {

    private RecyclerView mRecyclerView;
    private WordsAdapter mWordsAdapter;
    private ArrayList<WordsModel> mWordsList;
    private EditText editTextSearchView;
    private Image imageWord;
    private TextView textEduNameTitle;

    //preferences
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        preferenceManager = new PreferenceManager(getApplicationContext());

        textEduNameTitle = findViewById(R.id.textEduName);
        textEduNameTitle.setText(preferenceManager.getString(Constants.KEY_EDU_NAME));

        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewWords);
        editTextSearchView =  findViewById(R.id.searchView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //if eğer categori 0(Eğitimler) education 0(price action) daki eğitimler ->

        /*
        2 Adet Detay sayfası var biri cardSwipe diğeri konu anlatımı
            -Eğitimler
                -Temel Kelimeler position 0
                    -Seviye 1
                    -Seviye 2...
                -Price Action
                    -imbalance
                    -order block
                -On chain
            -Airdrops

        */


        // tanımlama
        mWordsList = new ArrayList<>();
        mWordsList.add(new WordsModel("Imbalance", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Order Block ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Qasimodo", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Supply", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Demand", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Trend", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Destek", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Trend1", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Trend2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Trend3", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mWordsList.add(new WordsModel("Trend4", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));


        mWordsAdapter = new WordsAdapter(EducationActivity.this,mWordsList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mWordsAdapter);



        editTextSearchView.setMaxWidth(Integer.MAX_VALUE);
        editTextSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWordsAdapter.getFilter().filter(charSequence);
                System.out.println(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });



    }

    //
    public void getBackButton(View view){
        onBackPressed();
        finish();
    }


    @Override
    public void onEduWordsListener(int position) {
        //Categories 0 ve Education 0 ise Eğitimler->temel kelimeler dedir, 1.Detail Activityye Eğer konsept eğitimi ise 2.Detay sayfanına tönlenmeli
        if (preferenceManager.getIntPosition(Constants.KEY_EDU_POSITION) == 0 && preferenceManager.getIntPosition(Constants.KEY_CATEGORIES_POSITION) == 0){
        Intent intent = new Intent(EducationActivity.this, DetailActivity1.class);
        startActivity(intent);
        }else {
            //Diğer tüm olasılıklar eğitim sayfasına
            Toast.makeText(this, "Position : " + preferenceManager.getIntPosition(Constants.KEY_EDU_POSITION), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EducationActivity.this, DetailActivity2.class);
            startActivity(intent);
        }

    }
}
