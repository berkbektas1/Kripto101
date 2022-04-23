package com.example.kripto101.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kripto101.Adapters.CardSwipeAdapter;
import com.example.kripto101.Models.CardSwipeModel;
import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity1 extends AppCompatActivity {
    private TextView textEduName;
    private PreferenceManager preferenceManager;

    private SwipeCardsView swipeCardsView;
    private List<CardSwipeModel> wordsModelList = new ArrayList<>();
    CardSwipeAdapter cardSwipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);

        preferenceManager = new PreferenceManager(getApplicationContext());


        textEduName = findViewById(R.id.textEduName);
        textEduName.setText(preferenceManager.getString(Constants.KEY_EDU_NAME));



        swipeCardsView = findViewById(R.id.cardSwipeView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);

        getData();


    }


    public void getData(){
        wordsModelList.add(new CardSwipeModel("Bitcoin","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
        wordsModelList.add(new CardSwipeModel("Ethereum","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
        wordsModelList.add(new CardSwipeModel("Ripple","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
        wordsModelList.add(new CardSwipeModel("Litecoin","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
        wordsModelList.add(new CardSwipeModel("Avax","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));

        cardSwipeAdapter = new CardSwipeAdapter(wordsModelList,this);
        swipeCardsView.setAdapter(cardSwipeAdapter);
        String temp= " " + cardSwipeAdapter.getCount();
        System.out.println(temp);

    }

    public void doReload(View view){
        swipeCardsView.setAdapter(cardSwipeAdapter);
        swipeCardsView.notifyDatasetChanged(0);//listeden seçildiğinde bu 3. kelimeden getir denilebilir
    }

    public void getBackButton(View view){
        onBackPressed();
        finish();
    }
}
