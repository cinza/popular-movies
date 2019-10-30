package com.azuniga.udacitypopularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Movie> dataList;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    static final String BASE_URL_IMG ="https://image.tmdb.org/t/p/w500/";

    RecyclerViewAdapter(Context context, List<Movie> data){
        this.mInflater = LayoutInflater.from(context);
        this.dataList = data;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String URL = BASE_URL_IMG + dataList.get(i).getUrlImage();
        Picasso.get()
                .load(URL)
                .into(holder.movieImage);

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;

        ViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // method to retrieve id from movie
    String getItem(int id) {
        Movie movieSelected = dataList.get(id);
        String movieId = movieSelected.getId();
        return movieId;
    }


    void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
