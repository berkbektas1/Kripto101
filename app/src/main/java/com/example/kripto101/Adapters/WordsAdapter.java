package com.example.kripto101.Adapters;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kripto101.ClickedListener;
import com.example.kripto101.EducationOnClickedListener;
import com.example.kripto101.Models.WordsModel;
import com.example.kripto101.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<WordsModel> mWordsModelList;
    private ArrayList<WordsModel> getWordsListFilter; // search for
    private EducationOnClickedListener clickedListener;

    public WordsAdapter(Context mcontext, ArrayList<WordsModel> mWordsModelList, EducationOnClickedListener clickedListener) {
        this.mcontext = mcontext;
        this.mWordsModelList = mWordsModelList;
        this.getWordsListFilter = mWordsModelList; // search
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.custom_card_words, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordsModel currentItem = mWordsModelList.get(position);

        holder.textWords.setText(currentItem.getName());
        holder.textDescription.setText(Html.fromHtml(currentItem.getDescription()));
        Picasso.get().load(currentItem.getImage()).into(holder.imageEdu);

    }

    @Override
    public int getItemCount() {
        return mWordsModelList.size();
    }

    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = getWordsListFilter.size();
                    filterResults.values = getWordsListFilter;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();

                    ArrayList<WordsModel> resultData = new ArrayList<>();
                    for (WordsModel wordsModel : getWordsListFilter) {
                        if (wordsModel.getName().toLowerCase().contains(searchChr)) {
                            resultData.add(wordsModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mWordsModelList = (ArrayList<WordsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textWords,textDescription;
        ImageView imageEdu;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            textWords = itemView.findViewById(R.id.textWords);
            textDescription = itemView.findViewById(R.id.textDescriptions);
            imageEdu = itemView.findViewById(R.id.imageEdu);

            itemView.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "Clicked = "+ textWords.getText().toString(), Toast.LENGTH_SHORT).show();
                clickedListener.onEduWordsListener(getAdapterPosition());
            });
        }
    }




}
