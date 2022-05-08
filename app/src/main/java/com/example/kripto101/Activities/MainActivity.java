package com.example.kripto101.Activities;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.viewpager2.widget.CompositePageTransformer;
        import androidx.viewpager2.widget.MarginPageTransformer;
        import androidx.viewpager2.widget.ViewPager2;

        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.text.Html;
        import android.transition.Slide;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.view.accessibility.AccessibilityManager;
        import android.view.animation.AnimationUtils;
        import android.view.animation.LayoutAnimationController;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
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
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
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

    private TextView textUserName,textTitles;
    private ImageView imageUser, imageAlert;

    //Recyclerview Education
    private RecyclerView mRecyclerView;
    private EducationsAdapter mEducationAdapter;
    private ArrayList<EducationsModel> mEducationList;
    LayoutAnimationController animation;

    private DatabaseReference databaseReference;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.putIntPosition(Constants.KEY_EDU_POSITION,0);// ilk açılışta default olarak 0 geliyor

        Log.d("Log", preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN).toString());

        sliderView = findViewById(R.id.imageSlider);
        textUserName = findViewById(R.id.textUserName);
        textTitles = findViewById(R.id.textTitles);
        imageUser = findViewById(R.id.profile_image);
        imageAlert = findViewById(R.id.imageAlert_i);
        imageUser.setImageResource(R.drawable.ic_settings_white);


        textUserName.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));

        //Recyclerview Animation
        animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);


        createSlider();

        createRecyclerView();


        imageAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean temp = true;
                showCustomDialog(temp);
                temp = false;
            }
        });

    }

    private void createSlider() {
        sliderItems = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Slider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SliderItem data = snapshot.getValue(SliderItem.class);
                    sliderItems.add(new SliderItem(data.getImage(),data.getLink()));
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        sliderAdapter = new SliderAdapter(sliderItems,this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

    }

    private void createRecyclerView() {
        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewEducations);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // tanımlama
        mEducationList = new ArrayList<>();
        /*
        mEducationList.add(new EducationsModel("Temel Kelimeler1","Lorem Ipsum is simply dummy text of the printingebility and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler2","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler3","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler4","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler5","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler6","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler7","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));

*/
        databaseReference = FirebaseDatabase.getInstance().getReference("Educations");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    EducationsModel eduModel = dataSnapshot.getValue(EducationsModel.class);

                    mEducationList.add(new EducationsModel(eduModel.getName(),eduModel.getDescription(),eduModel.getImageEdu()));
                }
                mEducationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mEducationAdapter = new EducationsAdapter(MainActivity.this,mEducationList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);


        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mEducationAdapter);
        mRecyclerView.setLayoutAnimation(animation);
    }

    public void showCustomDialog (boolean temp){
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_popup);

        //initialize
        final ImageView btnClose = dialog.findViewById(R.id.imageClose);
        final TextView textAlertDialog = dialog.findViewById(R.id.textAlertDialog);
        TextView textDevam = dialog.findViewById(R.id.textDevam);
        TextView textCıkıs = dialog.findViewById(R.id.textCıkıs);

        if (temp == true){
            textAlertDialog.setText(R.string.loremIpsum);
            textDevam.setVisibility(View.GONE);
            textCıkıs.setVisibility(View.GONE);
        }else {
            textDevam.setVisibility(View.VISIBLE);
            textCıkıs.setVisibility(View.VISIBLE);
            textAlertDialog.setText("Uygulamadan çıkmak istediğinize emin misiniz?");
        }

        textDevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textCıkıs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void getProfileActivity(View view){
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
        preferenceManager.putString(Constants.KEY_EDU_NAME,mEducationList.get(position).getName());
        startActivity(intent);
        //position bilgisini gönder bu bilgi 0 ise temel kelimedir

        //hangi kategoriden geldiyse o konular gelmeli
        //onPause();
        //put title bu title ile ilgili bilgiler yeni activity de açılmalı
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showCustomDialog(false);

    }
}
