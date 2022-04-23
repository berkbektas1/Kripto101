package com.example.kripto101.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.kripto101.Models.CardSwipeModel;
import com.example.kripto101.R;
import com.huxq17.swipecardsview.BaseCardAdapter;

import java.util.List;

public class CardSwipeAdapter extends BaseCardAdapter {

    private List<CardSwipeModel> modelList;
    private Context context;

    public CardSwipeAdapter(List<CardSwipeModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.item_card_swipe;
    }

    @Override
    public void onBindData(int position, View cardview) {

        if (modelList == null || modelList.size() == 0){
            return;
        }
        TextView textWords = cardview.findViewById(R.id.textWord);
        TextView textDescriptions = cardview.findViewById(R.id.textWordDescription);

        CardSwipeModel model = modelList.get(position);

        textWords.setText(model.getWord());
        textDescriptions.setText(model.getDescriptions());
        //Resim var ise picasso ile load edilmeli

    }
}
