package com.example.kripto101.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kripto101.ClickedListener;
import com.example.kripto101.Models.EducationsModel;
import com.example.kripto101.R;

import java.util.ArrayList;

public class EducationsAdapter extends RecyclerView.Adapter<EducationsAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<EducationsModel> mEducationList;
    private ClickedListener clickedListener;

    public EducationsAdapter(Context mcontext, ArrayList<EducationsModel> mEducationList, ClickedListener clickedListener) {
        this.mcontext = mcontext;
        this.mEducationList = mEducationList;
        this.clickedListener = clickedListener;
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

        getColor(holder,position);

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



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Clicked = "+ textTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                    clickedListener.onEducationClicked(getAdapterPosition());


                }
            });
        }

    }


    public void getColor(ViewHolder holder, int position){
        System.out.println(position);
        if (position % 6 == 0) {
            holder.containerCard.setBackgroundResource(R.color.colorEducations0);
        } else if (position % 6 == 1) {
            holder.containerCard.setBackgroundResource(R.color.colorEducations1);
        } else if (position % 6 == 2) {
            holder.containerCard.setBackgroundResource(R.color.colorEducations2);
        } else if (position % 6 == 3) {
            holder.containerCard.setBackgroundResource(R.color.colorEducations3);
        } else if (position % 6 == 4) {
            holder.containerCard.setBackgroundResource(R.color.colorEducations4);
        } else if (position % 6 == 5) {
            holder.containerCard.setBackgroundResource(R.color.colorEducations5);
        }

    }


}
