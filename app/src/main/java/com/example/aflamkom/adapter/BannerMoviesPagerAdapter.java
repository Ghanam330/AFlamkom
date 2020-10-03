package com.example.aflamkom.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.aflamkom.MovieDetails;
import com.example.aflamkom.R;
import com.example.aflamkom.model.BannerMovies;

import java.util.List;

public class BannerMoviesPagerAdapter extends PagerAdapter {

    Context context;
    List<BannerMovies> bannerMovies;

    public BannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMovies) {
        this.context = context;
        this.bannerMovies = bannerMovies;
    }

    @Override
    public int getCount() {
        return bannerMovies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout, null);
        ImageView bannerImage = view.findViewById(R.id.banner_image);
        Glide.with(context).load(bannerMovies.get(position).getImageUrl()).into(bannerImage);
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movieId",bannerMovies.get(position).getId());
                i.putExtra("movieName",bannerMovies.get(position).getMovieName());
                i.putExtra("movieImageUrl",bannerMovies.get(position).getImageUrl());
                i.putExtra("movieFile",bannerMovies.get(position).getFileUrl());
                context.startActivity(i);
            }
        });
        container.addView(view);
        return view;
    }
}
