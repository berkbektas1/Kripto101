package com.example.kripto101.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kripto101.Models.WordsModel;
import com.example.kripto101.R;

import java.util.ArrayList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<WordsModel> mWordsModelList;
    private ArrayList<WordsModel> getWordsListFilter; // search for

    public WordsAdapter(Context mcontext, ArrayList<WordsModel> mWordsModelList) {
        this.mcontext = mcontext;
        this.mWordsModelList = mWordsModelList;
        this.getWordsListFilter = mWordsModelList; // search
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

        String words = currentItem.getWord();
        String levels = currentItem.getLevel();

        holder.textWords.setText(words);

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
                        if (wordsModel.getWord().toLowerCase().contains(searchChr)) {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textWords;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textWords = itemView.findViewById(R.id.textWords);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Clicked = "+ textWords.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }




}
