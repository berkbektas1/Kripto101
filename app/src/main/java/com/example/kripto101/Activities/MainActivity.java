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

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickedListener {

    private ViewPager2 viewPager2;
    List<SliderItem> sliderItems;
    private Handler sliderHandler = new Handler();

    LinearLayout dotsLayout;
    TextView[] dots;
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
        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        dotsLayout = findViewById(R.id.dotsContainer);
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
        //Here, i'm preparing list of images from drawbal,
        //YOu can get it from Api as well
        sliderItems = new ArrayList<>();
/*
        databaseReference = FirebaseDatabase.getInstance().getReference("Slider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SliderItem data = snapshot.getValue(SliderItem.class);
                    sliderItems.add(new SliderItem(data.getImage(),data.getLink()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
*/

        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/priceactionwar-2406b.appspot.com/o/slider%2Fheader_slider.png?alt=media&token=35ff8e5e-41ec-4540-9c5a-27ab7e852e63", "https://www.binance.com/en"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/priceactionwar-2406b.appspot.com/o/slider%2Fheader_slider.png?alt=media&token=35ff8e5e-41ec-4540-9c5a-27ab7e852e63", "https://www.bybit.com/tr-TR/"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/priceactionwar-2406b.appspot.com/o/slider%2Fheader_slider.png?alt=media&token=35ff8e5e-41ec-4540-9c5a-27ab7e852e63", "https://www.gate.io/tr/"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/priceactionwar-2406b.appspot.com/o/slider%2Fheader_slider.png?alt=media&token=35ff8e5e-41ec-4540-9c5a-27ab7e852e63", "https://ftx.com/tr"));

        dots = new TextView[sliderItems.size()];
        dotsIndicator();

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2, this));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.9f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedIndicator(position);
                super.onPageSelected(position);

                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);//slide duration 3 second

                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

            }
        });
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
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            //slider back to top
            if (viewPager2.getCurrentItem() < sliderItems.size()-1){
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }else{
                viewPager2.setCurrentItem(0);
            }

        }
    };

    private void selectedIndicator(int position) {
        for(int i=0;i<dots.length;i++){
            if (i==position){
                dots[i].setTextColor(getResources().getColor(R.color.colorBlue));
            }else
            {
                dots[i].setTextColor(getResources().getColor(R.color.white));
            }
        }


    }

    private void dotsIndicator() {
        for(int i= 0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(18);
            dotsLayout.addView(dots[i]);
        }

    }

    public void getProfileActivity(View view){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);

    }

    @Override
    public void onPictureClicked(int position) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sliderItems.get(position).getLink()));
        startActivity(browserIntent);

        /*
        if activity için ;

        Intent intent = new Intent(getApplicationContext(), DetaildActivity.class);
        intent.putExtra("key",position);
        startActivity(intent);

         */
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


    protected void onStop() {
        super.onStop();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showCustomDialog(false);

    }
}
