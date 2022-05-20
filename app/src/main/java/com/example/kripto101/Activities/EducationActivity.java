package com.example.kripto101.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kripto101.Adapters.WordsAdapter;
import com.example.kripto101.EducationOnClickedListener;
import com.example.kripto101.Models.WordsModel;
import com.example.kripto101.R;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity implements EducationOnClickedListener {

    private RecyclerView mRecyclerView;
    private WordsAdapter mWordsAdapter;
    private ArrayList<WordsModel> mWordsList;
    private EditText editTextSearchView;
    private TextView textEduNameTitle;

    //preferences
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        preferenceManager = new PreferenceManager(getApplicationContext());

        textEduNameTitle = findViewById(R.id.textEduName);
        textEduNameTitle.setText(preferenceManager.getString(Constants.KEY_EDU_NAME));

        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewWords);
        editTextSearchView =  findViewById(R.id.searchView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // tanımlama
        mWordsList = new ArrayList<>();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Educations").child(preferenceManager.getString(Constants.KEY_EDU_NAME));
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mWordsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    WordsModel data = dataSnapshot.getValue(WordsModel.class);
                    if (data != null){

                        mWordsList.add(new WordsModel(data.getName(), data.getAuthor(), data.getDescription(), data.getImage()));
                    }
                }
                mWordsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error"+ error.getMessage());
            }
        });


        mWordsAdapter = new WordsAdapter(EducationActivity.this,mWordsList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mWordsAdapter);

        editTextSearchView.setMaxWidth(Integer.MAX_VALUE);
        editTextSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWordsAdapter.getFilter().filter(charSequence);
                System.out.println(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void getBackButton(View view){
        onBackPressed();
        finish();
    }


    @Override
    public void onEduWordsListener(int position) {
        Toast.makeText(this, "Position Subtitle: " + position, Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(EducationActivity.this, DetailActivity2.class);
        intent.putExtra("title", preferenceManager.getString(Constants.KEY_EDU_NAME));
        intent.putExtra("subTitle", mWordsList.get(position).getName());
        intent.putExtra("author", mWordsList.get(position).getAuthor());
        intent.putExtra("description", mWordsList.get(position).getDescription());
        intent.putExtra("image", mWordsList.get(position).getImage());
        startActivity(intent);

    }
}
