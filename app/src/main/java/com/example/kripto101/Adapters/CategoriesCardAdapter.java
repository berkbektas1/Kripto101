package com.example.kripto101.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kripto101.ClickedListener;
import com.example.kripto101.Models.CategoriesCardModel;
import com.example.kripto101.R;

import java.util.ArrayList;

public class CategoriesCardAdapter extends RecyclerView.Adapter<CategoriesCardAdapter.ViewHolder> {

    ArrayList<CategoriesCardModel> categoriesCardModels;
    Context context;
    private ClickedListener clickedListener;

    public CategoriesCardAdapter(Context context, ArrayList<CategoriesCardModel> categoriesCardModels, ClickedListener clickedListener){
        this.context = context;
        this.categoriesCardModels = categoriesCardModels;
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_categories,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set image
        CategoriesCardModel currentItem = categoriesCardModels.get(position);

        holder.imageViewCard.setImageResource(currentItem.getEduCategoriesImage());
        holder.textViewCard.setText(currentItem.getEduCategoriesTitle());
    }

    @Override
    public int getItemCount() {
        return categoriesCardModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize Variable
        ImageView imageViewCard;
        TextView textViewCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            imageViewCard = itemView.findViewById(R.id.imageCard);
            textViewCard = itemView.findViewById(R.id.textEduCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(), "Clicked = "+ textViewCard.getText().toString(), Toast.LENGTH_SHORT).show();
                    clickedListener.onEduCardClicked(getAdapterPosition());
                }
            });

        }
    }

}
