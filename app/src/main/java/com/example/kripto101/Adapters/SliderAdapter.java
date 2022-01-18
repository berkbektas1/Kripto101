package com.example.kripto101.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kripto101.ClickedListener;
import com.example.kripto101.R;
import com.example.kripto101.Models.SliderItem;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItems;
    private ViewPager2 viewPager2;
    private ClickedListener clickedListener;

     public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2, ClickedListener clickedListener) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;

         SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(final SliderItem sliderItem) {
            //If you want to display image from the internet
            //You can put code here using glide or picasso
            imageView.setImageResource(sliderItem.getImage());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    clickedListener.onPictureClicked(getAdapterPosition());


                }
            });

        }

    }




}
