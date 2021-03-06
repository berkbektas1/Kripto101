package com.example.kripto101.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kripto101.ClickedListener;
import com.example.kripto101.Models.EducationsModel;
import com.example.kripto101.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EducationsAdapter extends RecyclerView.Adapter<EducationsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<EducationsModel> mEducationList;
    private ClickedListener clickedListener;

    public EducationsAdapter(Context mContext, ArrayList<EducationsModel> mEducationList, ClickedListener clickedListener) {
        this.mContext = mContext;
        this.mEducationList = mEducationList;
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_cardview_educations, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EducationsModel currentItem = mEducationList.get(position);
        holder.textTitle.setText(currentItem.getName());
        holder.textDescription.setText(Html.fromHtml(currentItem.getDescription()));

        Picasso.get().load(currentItem.getImageEdu()).into(holder.imageEducation);

        getColor(holder,position);

    }

    @Override
    public int getItemCount() {
        return mEducationList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle, textDescription;
        ImageView imageEducation;
        LinearLayout containerCard;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textCategories);
            textDescription = itemView.findViewById(R.id.textDescriptions);
            imageEducation = itemView.findViewById(R.id.imageEducations);
            containerCard = itemView.findViewById(R.id.containerCustomCard);

            itemView.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "Clicked = "+ textTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                clickedListener.onEducationClicked(getAdapterPosition());


            });
        }
    }

    private void getColor(ViewHolder holder, int position){
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
