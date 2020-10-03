package com.example.aflamkom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aflamkom.MovieDetails;
import com.example.aflamkom.R;
import com.example.aflamkom.model.CategoryItemList;

import java.util.List;

public class ItemRecyclerAdabter extends RecyclerView.Adapter<ItemRecyclerAdabter.ItemViewHolder> {
Context context;
List<CategoryItemList>categoryItemList;

    public ItemRecyclerAdabter(Context context, List<CategoryItemList> categoryItemlist) {
        this.context=context;
        this.categoryItemList=categoryItemlist;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder,final int position) {
        Glide.with(context).load(categoryItemList.get(position).getImageUrl()).into(holder.itemImage);
holder.itemImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(context, MovieDetails.class);
        i.putExtra("moviedId",categoryItemList.get(position).getId());
        i.putExtra("moviedName",categoryItemList.get(position).getMovieName());
        i.putExtra("moviedUrl",categoryItemList.get(position).getImageUrl());
        i.putExtra("moviedFile",categoryItemList.get(position).getFileUrl());
        context.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.item_image);
        }
    }
}
