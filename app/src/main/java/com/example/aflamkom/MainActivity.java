package com.example.aflamkom;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.aflamkom.adapter.BannerMoviesPagerAdapter;
import com.example.aflamkom.adapter.MainRecyclerAdapter;
import com.example.aflamkom.model.AllCategory;
import com.example.aflamkom.model.BannerMovies;
import com.example.aflamkom.model.CategoryItemList;
import com.example.aflamkom.retrofit.RetrofictClient;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout tabIndicator, categoryTab;
    ViewPager bannerMoviesViewPager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> tvShowBannerList;
    List<BannerMovies> movieBannerList;
    List<BannerMovies> kidsBannerList;
    Timer sliderTimer;
    List<AllCategory> allCategoryList;
    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabIndicator = findViewById(R.id.tab_indication);
        nestedScrollView = findViewById(R.id.nested_scroll);
        categoryTab = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.appbar);

        homeBannerList = new ArrayList<>();
        tvShowBannerList = new ArrayList<>();
        movieBannerList = new ArrayList<>();
        kidsBannerList = new ArrayList<>();
        allCategoryList = new ArrayList<>();








        // fecctch banner date from server
        getBannerDate();




        getAllMoviesDate(1);


        //  on tab selected data
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(tvShowBannerList);
                       getAllMoviesDate(1);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(movieBannerList);
                        getAllMoviesDate(2);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(kidsBannerList);
                        getAllMoviesDate(3);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(homeBannerList);
                        getAllMoviesDate(4);

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList) {
        bannerMoviesViewPager = findViewById(R.id.banner_view);
        bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(this, bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);
        tabIndicator.setupWithViewPager(bannerMoviesViewPager);
        sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);


    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    if (bannerMoviesViewPager.getCurrentItem() < homeBannerList.size() - 1) {
                        bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem() + 1);
                    } else {
                        bannerMoviesViewPager.setCurrentItem(0);
                    }
                }
            });
        }


    }


    public void setMainRecycler(List<AllCategory> allCategoryList) {
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);

    }

    private void setScrollDefaultState() {
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0, 0);
        appBarLayout.setExpanded(true);
    }

private void getBannerDate(){

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    compositeDisposable.add(RetrofictClient.getRetrofitClient().getAllBanners()
    .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<List<BannerMovies>>() {
                @Override
                public void onNext(List<BannerMovies> bannerMovies) {
                    for (int i = 0; i < bannerMovies.size(); i++) {

                        if (bannerMovies.get(i).getBannerCategoryId().toString().equals("1")) {
                            homeBannerList.add(bannerMovies.get(i));

                        } else if (bannerMovies.get(i).getBannerCategoryId().toString().equals("2")) {
                            tvShowBannerList.add(bannerMovies.get(i));
                        } else if (bannerMovies.get(i).getBannerCategoryId().toString().equals("3")) {
                            movieBannerList.add(bannerMovies.get(i));
                        } else if (bannerMovies.get(i).getBannerCategoryId().toString().equals("4")) {
                            kidsBannerList.add(bannerMovies.get(i));
                        } else {

                        }
                    }

                    Toast.makeText(getApplicationContext(),"hello"+bannerMovies, Toast.LENGTH_SHORT).show();
                   // Log.d("bannerDate",""+bannerMovies);
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("bannerDate",""+e);
                }

                @Override
                public void onComplete() {
                    setBannerMoviesPagerAdapter(homeBannerList);
                }
            })
    );
}
private void getAllMoviesDate(int categoryId) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetrofictClient.getRetrofitClient().getAllCategoryMovies(categoryId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<AllCategory>>() {
                    @Override
                    public void onNext(List<AllCategory>allCategoryList) {
                        setMainRecycler(allCategoryList);
                        Toast.makeText(getApplicationContext(),"hello"+allCategoryList, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplicationContext(),"hello"+allCategoryList, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }






}