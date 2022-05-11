package com.example.kripto101.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.kripto101.ClickedListener;
import com.example.kripto101.Models.SliderItem;
import com.example.kripto101.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    private List<SliderItem> sliderItems;
    private ClickedListener clickedListener;

    public SliderAdapter(List<SliderItem> sliderItems, ClickedListener clickedListener) {
        this.sliderItems = sliderItems;
        this.clickedListener = clickedListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slide_item_container,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        SliderItem item = sliderItems.get(position);
        Picasso.get().load(item.getImage()).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(view -> clickedListener.onPictureClicked(position));

    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    static class Holder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
