package com.azuniga.udacitypopularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.azuniga.udacitypopularmovies.utils.APINetwork;
import com.azuniga.udacitypopularmovies.utils.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    public String sortOrder = "popular";
    public List<Movie> movies;
    ProgressDialog progressDialog = null ;
    public static final String ID_MOVIE = "movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading movies...");
        if(isOnline()){
            loadPopularMovies();
        }else{
            Toast.makeText(MainActivity.this, "No internet connection! Please check your network", Toast.LENGTH_SHORT).show();

        }




    }
    private void loadPopularMovies(){
        progressDialog.show();
        APINetwork service = RetroFitClient.getRetrofitInstance().create(APINetwork.class);
        Call<MovieAPIResponse> getMovies = service.getPopularMovies ( APINetwork.MOVIE_TOKEN);
        getMovies.enqueue (new Callback<MovieAPIResponse> () {
            @Override
            public void onResponse(Call<MovieAPIResponse> call, Response<MovieAPIResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                    movies = response.body().getResults ();
                    createDataList(movies);
                } else {
                    System.out.println(response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<MovieAPIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadTopRatedMovies(){
        progressDialog.show();
        APINetwork service = RetroFitClient.getRetrofitInstance().create(APINetwork.class);
        Call<MovieAPIResponse> getTopRatedMovies = service.getTopRatedMovies(APINetwork.MOVIE_TOKEN);
        getTopRatedMovies.enqueue (new Callback<MovieAPIResponse> () {
            @Override
            public void onResponse(Call<MovieAPIResponse> call, Response<MovieAPIResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                    movies = response.body().getResults ();
                    createDataList(movies);
                } else {
                    System.out.println(response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<MovieAPIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createDataList(List<Movie> movieList){

        RecyclerView recyclerView = findViewById(R.id.rvMovies);
        adapter= new RecyclerViewAdapter (this, movieList);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager (this, numberOfColumns));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popularMenu:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                loadPopularMovies ();
            case R.id.topRatedMenu:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                loadTopRatedMovies ();

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onItemClick(View view, int id) {
        Intent intentDetailMovie = new Intent(this, MovieDetail.class);
        String idMovie = adapter.getItem(id);
        intentDetailMovie.putExtra(ID_MOVIE, idMovie);
        startActivity(intentDetailMovie);

    }

}
