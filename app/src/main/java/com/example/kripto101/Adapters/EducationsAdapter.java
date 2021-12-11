package com.example.kripto101.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kripto101.Models.EducationsModel;
import com.example.kripto101.R;

import java.util.ArrayList;

public class EducationsAdapter extends RecyclerView.Adapter<EducationsAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<EducationsModel> mEducationList;
    int sayi=-1;

    public EducationsAdapter(Context mcontext, ArrayList<EducationsModel> mEducationList) {
        this.mcontext = mcontext;
        this.mEducationList = mEducationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.custom_cardview_educations, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EducationsModel currentItem = mEducationList.get(position);
        holder.textTitle.setText(currentItem.getName());
        holder.textDescription.setText(currentItem.getDescription());
        holder.imageEducation.setImageResource(currentItem.getImageEdu());

        holder.containerCard.setBackgroundResource(getColor());
    }

    @Override
    public int getItemCount() {
        return mEducationList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle, textDescription;
        ImageView imageEducation;
        LinearLayout containerCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textCategories);
            textDescription = itemView.findViewById(R.id.textDescriptions);
            imageEducation = itemView.findViewById(R.id.imageEducations);
            containerCard = itemView.findViewById(R.id.containerCustomCard);


        }
    }

    public int getColor(){

        int[] colors =new int[5];
        colors[0] = R.color.colorEducations1;
        colors[1] = R.color.colorEducations2;
        colors[2] = R.color.colorEducations3;
        colors[3] = R.color.colorEducations4;
        colors[4] = R.color.colorEducations5;

        sayi++;
        if (sayi ==5){
            sayi = 0;
        }

        return colors[sayi];
    }

}
