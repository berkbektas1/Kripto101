package com.example.kripto101.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickedListener {

    private ViewPager2 viewPager2;
    List<SliderItem> sliderItems;
    private Handler sliderHandler = new Handler();
    LinearLayout dotsLayout;
    TextView[] dots;
    private TextView textUserName,textEgitimler;
    private ImageView imageUser;


    //Recyclerview
    private RecyclerView mRecyclerView;
    private EducationsAdapter mEducationAdapter;
    private ArrayList<EducationsModel> mEducationList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //APP Status Bar Color
        Window w = getWindow();
        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        w.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBackground));


        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        dotsLayout = findViewById(R.id.dotsContainer);
        textUserName = findViewById(R.id.textUserName);
        textEgitimler = findViewById(R.id.egitimler);
        imageUser = findViewById(R.id.profile_image);

        textUserName.setText("Berk Bektaş");
        textEgitimler.setText("Eğitimler");
        imageUser.setImageResource(R.drawable.profile_pic);



        //Here, i'm preparing list of images from drawbal,
        //YOu can get it from Api as well
        sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.header1, "https://www.binance.com/en"));
        sliderItems.add(new SliderItem(R.drawable.header2, "https://www.bybit.com/tr-TR/"));
        sliderItems.add(new SliderItem(R.drawable.header3, "https://www.gate.io/tr/"));
        sliderItems.add(new SliderItem(R.drawable.header4, "https://ftx.com/tr"));

        dots = new TextView[sliderItems.size()];
        dotsIndicator();

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2, this));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
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




        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewEducations);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // tanımlama
        mEducationList = new ArrayList<>();
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));

        mEducationAdapter = new EducationsAdapter(MainActivity.this,mEducationList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mEducationAdapter);


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
}
