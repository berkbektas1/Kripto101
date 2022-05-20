package com.example.kripto101.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kripto101.Adapters.EducationsAdapter;
import com.example.kripto101.ClickedListener;
import com.example.kripto101.Models.EducationsModel;
import com.example.kripto101.R;
import com.example.kripto101.Adapters.SliderAdapter;
import com.example.kripto101.Models.SliderItem;
import com.example.kripto101.utilities.Constants;
import com.example.kripto101.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickedListener {

    SliderView sliderView;
    List<SliderItem> sliderItems;
    SliderAdapter sliderAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Dialog dialog;

    private TextView textUserName, textTitles;
    private ImageView imageUser, imageAlert;

    //Recyclerview Education
    private RecyclerView mRecyclerView;
    private EducationsAdapter mEducationAdapter;
    private ArrayList<EducationsModel> mEducationList;
    LayoutAnimationController animation;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferenceManager = new PreferenceManager(getApplicationContext());

        Log.d("Log", preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN).toString());

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        sliderView = findViewById(R.id.imageSlider);
        textUserName = findViewById(R.id.textUserName);
        textTitles = findViewById(R.id.textTitles);
        imageUser = findViewById(R.id.profile_image);
        imageAlert = findViewById(R.id.imageAlert_i);
        imageUser.setImageResource(R.drawable.ic_settings_white);

        textTitles.setText(R.string.e_itimler);
        textUserName.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));

        //Recyclerview Animation
        animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);

        swipeRefreshLayout.setOnRefreshListener(this::loadData);
        loadData();
        createSlider();
        createRecyclerView();

        imageAlert.setOnClickListener(view -> {
            boolean temp = true;
            showCustomDialog(temp);
            temp = false;
        });

    }

    private void loadData() {
        //slider
        sliderItems = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Slider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sliderItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SliderItem data = snapshot.getValue(SliderItem.class);
                    if (data != null ) {
                        sliderItems.add(new SliderItem(data.getImage(), data.getLink()));
                    }
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Educations
        mEducationList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("EducationAbstract");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mEducationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    swipeRefreshLayout.setRefreshing(false);
                    EducationsModel eduModel = dataSnapshot.getValue(EducationsModel.class);
                    if (eduModel != null){
                        mEducationList.add(new EducationsModel(eduModel.getName(), eduModel.getDescription(), eduModel.getImageEdu()));
                    }
                }
                mEducationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createSlider() {
        sliderAdapter = new SliderAdapter(sliderItems, this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.NONE);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();
    }

    private void createRecyclerView() {
        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewEducations);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEducationAdapter = new EducationsAdapter(MainActivity.this, mEducationList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mEducationAdapter);
        mRecyclerView.setLayoutAnimation(animation);
    }

    public void showCustomDialog(boolean temp) {
        dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_popup);

        //initialize
        final ImageView btnClose = dialog.findViewById(R.id.imageClose);
        final TextView textAlertDialog = dialog.findViewById(R.id.textAlertDialog);
        TextView textContinue = dialog.findViewById(R.id.textDevam);
        TextView textExit = dialog.findViewById(R.id.textCıkıs);

        if (temp) {
            textAlertDialog.setText(R.string.loremIpsum); // i button
            textContinue.setVisibility(View.GONE);
            textExit.setVisibility(View.GONE);
        } else {
            textContinue.setVisibility(View.VISIBLE);
            textExit.setVisibility(View.VISIBLE);
            textAlertDialog.setText(R.string.ExitString); //exit
        }

        dialog.show();

        textContinue.setOnClickListener(view -> dialog.dismiss());
        textExit.setOnClickListener(view -> finishAffinity());
        btnClose.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    public void getProfileActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPictureClicked(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sliderItems.get(position).getLink()));
        startActivity(browserIntent);
    }

    @Override
    public void onEducationClicked(int position) {
        Intent intent = new Intent(MainActivity.this, EducationActivity.class);
        preferenceManager.putString(Constants.KEY_EDU_NAME, mEducationList.get(position).getName());
        preferenceManager.putIntPosition(Constants.KEY_EDU_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showCustomDialog(false);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog!=null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
