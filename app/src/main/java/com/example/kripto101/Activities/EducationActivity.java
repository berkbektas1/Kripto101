package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.kripto101.Adapters.WordsAdapter;
import com.example.kripto101.Models.WordsModel;
import com.example.kripto101.R;

import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private WordsAdapter mWordsAdapter;
    private ArrayList<WordsModel> mWordsList;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);


        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewWords);
        searchView =  findViewById(R.id.searchView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // tanımlama
        mWordsList = new ArrayList<>();
        mWordsList.add(new WordsModel("Temel Kavramlar", "Seviye 1"));
        mWordsList.add(new WordsModel("Tedede ", "Seviye 1"));
        mWordsList.add(new WordsModel("Tededaedea", "Seviye 1"));
        mWordsList.add(new WordsModel("Teadaar3", "Seviye 1"));
        mWordsList.add(new WordsModel("Teaddasramlar4", "Seviye 1"));
        mWordsList.add(new WordsModel("Teservramlar5", "Seviye 1"));


        mWordsAdapter = new WordsAdapter(EducationActivity.this,mWordsList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mWordsAdapter);



        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWordsAdapter.getFilter().filter(charSequence);
                System.out.println(charSequence);
                mRecyclerView.setVisibility(View.VISIBLE);
                if (charSequence.length()==0){
                    mRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });



    }

}
