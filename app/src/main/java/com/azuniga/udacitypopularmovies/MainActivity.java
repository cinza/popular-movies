package com.azuniga.udacitypopularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.azuniga.udacitypopularmovies.models.Movie;
import com.azuniga.udacitypopularmovies.models.MovieAPIResponse;
import com.azuniga.udacitypopularmovies.database.MoviesViewModel;
import com.azuniga.udacitypopularmovies.utils.APINetwork;
import com.azuniga.udacitypopularmovies.utils.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    public static final String preferenceFilterBy = "PrefFilterBy";
    public List<Movie> movies;
    ProgressDialog progressDialog = null ;
    public static final String ID_MOVIE = "movie_id";
    public static final String FAVORITE_MOVIE = "is_favorite";
    public static final String SortBy = "sortBy";
    SharedPreferences settings;
    private MoviesViewModel  moviesViewModel;
    public boolean favSelected = false;

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading movies...");
        settings = getSharedPreferences(preferenceFilterBy,Context.MODE_PRIVATE);
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        if(isOnline()){
            String preference = settings.getString(SortBy, "");
            if (preference == "popular")
                loadPopularMovies();
            else if (preference == "topRated")
                loadTopRatedMovies ();
                else
                    favSelected=true;
                loadFavoritesMovies();

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

    private  void loadFavoritesMovies(){

        moviesViewModel.getFavoritesMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                createDataList(movies);

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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        settings = getSharedPreferences(preferenceFilterBy, Context.MODE_PRIVATE);
        String preference = settings.getString(SortBy, "");
        switch(preference){
            case "popular":
                menu.findItem(R.id.popularMenu).setChecked(true);
                favSelected = false;
                break;
            case "topRated":
                menu.findItem(R.id.topRatedMenu).setChecked(true);
                favSelected = false;
                break;
            case "favorites":
                favSelected=true;
                menu.findItem(R.id.topRatedMenu).setChecked(false);
                menu.findItem(R.id.popularMenu).setChecked(false);
                menu.findItem(R.id.action_favorite).setIcon(getResources().getDrawable(R.drawable.ic_favorite_active));

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        SharedPreferences.Editor editorPreference = settings.edit();
        switch (id) {
            case R.id.popularMenu:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editorPreference.putString(SortBy, "popular");
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite));
                favSelected = false;
                loadPopularMovies();

                break;
            case R.id.topRatedMenu:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editorPreference.putString(SortBy, "topRated");
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite));
                favSelected = false;
                loadTopRatedMovies ();

                break;
            case R.id.action_favorite:
                loadFavoritesMovies();
                favSelected=true;
                menu.findItem(R.id.topRatedMenu).setChecked(false);
                menu.findItem(R.id.popularMenu).setChecked(false);
                item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_active));
                editorPreference.putString(SortBy, "favorites");
                break;
            default:
                break;
        }
        editorPreference.commit();
        return super.onOptionsItemSelected(item);
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
        intentDetailMovie.putExtra(FAVORITE_MOVIE, favSelected);
        startActivity(intentDetailMovie);

    }

}
