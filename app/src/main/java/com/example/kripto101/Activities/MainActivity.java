package com.example.kripto101.Activities;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.ContextCompat;
        import androidx.recyclerview.widget.DefaultItemAnimator;
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
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.view.animation.LayoutAnimationController;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.kripto101.Adapters.CategoriesCardAdapter;
        import com.example.kripto101.Adapters.EducationsAdapter;
        import com.example.kripto101.ClickedListener;
        import com.example.kripto101.Models.CategoriesCardModel;
        import com.example.kripto101.Models.EducationsModel;
        import com.example.kripto101.R;
        import com.example.kripto101.Adapters.SliderAdapter;
        import com.example.kripto101.Models.SliderItem;
        import com.example.kripto101.utilities.Constants;
        import com.example.kripto101.utilities.PreferenceManager;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickedListener {

    private ViewPager2 viewPager2;
    List<SliderItem> sliderItems;
    private Handler sliderHandler = new Handler();
    LinearLayout dotsLayout;
    TextView[] dots;
    private TextView textUserName,textTitles;
    private ImageView imageUser;

    //Recyclerview Education Categories Card
    private RecyclerView recyclerViewCard;
    private ArrayList<CategoriesCardModel> mcategoriesCardList;
    private CategoriesCardAdapter categoriesCardAdapter;



    //Recyclerview Education
    private RecyclerView mRecyclerView;
    private EducationsAdapter mEducationAdapter;
    private ArrayList<EducationsModel> mEducationList;

    private PreferenceManager preferenceManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.putIntPosition(Constants.KEY_EDU_POSITION,0);// ilk açılışta default olarak 0 geliyor


        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        dotsLayout = findViewById(R.id.dotsContainer);
        textUserName = findViewById(R.id.textUserName);
        textTitles = findViewById(R.id.textTitles);
        imageUser = findViewById(R.id.profile_image);

        imageUser.setImageResource(R.drawable.ic_settings_white);
        textUserName.setText("Berk Bektaş");

        //Recyclerview Animation
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);


        //Here, i'm preparing list of images from drawbal,
        //YOu can get it from Api as well
        sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.header_slider, "https://www.binance.com/en"));
        sliderItems.add(new SliderItem(R.drawable.header_slider, "https://www.bybit.com/tr-TR/"));
        sliderItems.add(new SliderItem(R.drawable.header_slider, "https://www.gate.io/tr/"));
        sliderItems.add(new SliderItem(R.drawable.header_slider, "https://ftx.com/tr"));

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

        //Recyclerview Categories Card
        recyclerViewCard = findViewById(R.id.RVEducationCard);
        mcategoriesCardList = new ArrayList<>();
        mcategoriesCardList.add(new CategoriesCardModel(R.drawable.profile_pic,"Eğitimler"));
        mcategoriesCardList.add(new CategoriesCardModel(R.drawable.profile_pic,"Pre-Sales"));
        mcategoriesCardList.add(new CategoriesCardModel(R.drawable.profile_pic,"Airdrops"));
        mcategoriesCardList.add(new CategoriesCardModel(R.drawable.profile_pic,"NFTs"));

        //Design Horizontal
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerViewCard.setLayoutManager(layoutManager);
        recyclerViewCard.setItemAnimator(new DefaultItemAnimator());

        //Initialize CategoriesCardAdapter
        categoriesCardAdapter = new CategoriesCardAdapter(MainActivity.this, mcategoriesCardList,this);
        //Set CategoriesCardAdapter to Recyclerview
        recyclerViewCard.setAdapter(categoriesCardAdapter);

        //Titles
        textTitles.setText(mcategoriesCardList.get(0).getEduCategoriesTitle());


        // Eğitimler
        mRecyclerView = findViewById(R.id.recyclerviewEducations);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // tanımlama
        mEducationList = new ArrayList<>();
        mEducationList.add(new EducationsModel("Temel Kelimeler1","Lorem Ipsum is simply dummy text of the printingebility and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler2","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler3","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler4","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler5","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler6","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
        mEducationList.add(new EducationsModel("Temel Kelimeler7","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));



        mEducationAdapter = new EducationsAdapter(MainActivity.this,mEducationList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);


        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mEducationAdapter);
        mRecyclerView.setLayoutAnimation(animation);


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
        preferenceManager.putIntPosition(Constants.KEY_EDU_POSITION,position);
        startActivity(intent);
        //position bilgisini gönder bu bilgi 0 ise temel kelimedir

        //hangi kategoriden geldiyse o konular gelmeli
        //onPause();
        //put title bu title ile ilgili bilgiler yeni activity de açılmalı
    }

    @Override
    public void onEduCardClicked(int position) {

        textTitles.setText(mcategoriesCardList.get(position).getEduCategoriesTitle());
        preferenceManager.putIntPosition(Constants.KEY_CATEGORIES_POSITION, position);

        if (position == 0){

            mEducationList.clear();
            mEducationList.add(new EducationsModel("Temel Kelimeler1","Lorem Ipsum is simply dummy text of the printingebility and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Temel Kelimeler2","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Temel Kelimeler3","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Temel Kelimeler4","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Temel Kelimeler5","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Temel Kelimeler6","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Temel Kelimeler7","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mEducationAdapter);
            mRecyclerView.startLayoutAnimation();

        }if (position == 1){
            mEducationList.clear();
            // tanımlama
            mEducationList.add(new EducationsModel("Pre-Sale 1","Lorem Ipsum is simply dummy text of the printingebility and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Pre-Sale 2","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Pre-Sale 3","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Pre-Sale 4","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Pre-Sale 5","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Pre-Sale 6","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Pre-Sale 7","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));

            mEducationAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mEducationAdapter);
            mRecyclerView.startLayoutAnimation();

        }if (position ==2 ){
            mEducationList.clear();
            // tanımlama
            mEducationList.add(new EducationsModel("Airdroplar 1","Lorem Ipsum is simply dummy text of the printingebility and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Airdroplar 2","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Airdroplar 3","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Airdroplar 4","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Airdroplar 5","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Airdroplar 6","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("Airdroplar 7","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));

            mEducationAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mEducationAdapter);
            mRecyclerView.startLayoutAnimation();
        }if (position ==3 ){
            mEducationList.clear();
            // tanımlama
            mEducationList.add(new EducationsModel("NFTs 1","Lorem Ipsum is simply dummy text of the printingebility and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("NFTs 2","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("NFTs 3","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("NFTs 4","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("NFTs 5","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("NFTs 6","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));
            mEducationList.add(new EducationsModel("NFTs 7","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",R.drawable.profile_pic));

            mEducationAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mEducationAdapter);
            mRecyclerView.startLayoutAnimation();

        }
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
}
