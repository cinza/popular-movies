package com.azuniga.udacitypopularmovies;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.azuniga.udacitypopularmovies.adapters.ReviewAdapter;
import com.azuniga.udacitypopularmovies.adapters.VideoAdapter;
import com.azuniga.udacitypopularmovies.database.AppExecutors;
import com.azuniga.udacitypopularmovies.database.MovieRoomDataBase;
import com.azuniga.udacitypopularmovies.models.Movie;
import com.azuniga.udacitypopularmovies.database.MoviesViewModel;
import com.azuniga.udacitypopularmovies.models.Review;
import com.azuniga.udacitypopularmovies.models.ReviewAPIResponse;
import com.azuniga.udacitypopularmovies.models.Video;
import com.azuniga.udacitypopularmovies.models.VideoAPIResponse;
import com.azuniga.udacitypopularmovies.utils.APINetwork;
import com.azuniga.udacitypopularmovies.utils.RetroFitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    Movie movieDetail;
    List<Video> videos;
    List<Review> reviews;
    TextView mTitleMovie;
    TextView mRating;
    TextView mReleaseDate;
    TextView mSynopsis;
    ImageView mThumbnailMovie;
    FloatingActionButton mFavMovie;
    boolean isFavorite;
    static final String VIDEO_URL = "https://www.youtube.com/watch?v=";
    static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342/";

    MoviesViewModel moviesViewModel;
    List<Movie> listFavoriteMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();

        String idMovie = intent.getStringExtra(MainActivity.ID_MOVIE);
        isFavorite = intent.getBooleanExtra(MainActivity.FAVORITE_MOVIE,false);
        mTitleMovie = findViewById(R.id.titleMovie);
        mThumbnailMovie = findViewById(R.id.thumbnailMovie);
        mRating = findViewById(R.id.rateMovie);
        mReleaseDate = findViewById(R.id.releaseDate);
        mSynopsis = findViewById(R.id.sypnosisMovie);
        mFavMovie= findViewById(R.id.favorite_button);
        if(isFavorite)
            populateUI(true);
        else populateUI(false);
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        loadMovie(idMovie);
        setupViewMode();

        mFavMovie.setOnClickListener((new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new AppExecutors().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(isFavMovie(listFavoriteMovies, movieDetail)){
                            moviesViewModel.deleteFavoriteMovie(movieDetail);

                        }else{
                            moviesViewModel.addFavoriteMovie(movieDetail);

                        }

                    }

                });
            }

        }));
    }

    public void setupViewMode(){
        moviesViewModel.getFavoritesMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                listFavoriteMovies = movies;
                moviesViewModel.getFavoritesMovies().removeObserver(this);
                if(isFavMovie(listFavoriteMovies, movieDetail)){
                    populateUI(true);
                }else{
                    populateUI(false);
                }

            }
        });
    }

    public void populateUI(boolean isFav){
        if(isFav)mFavMovie.setImageResource(R.drawable.ic_favorite_dark_active);
        else  mFavMovie.setImageResource(R.drawable.ic_favorite_border);
    }
    public void loadMovie(String idMovie) {
        APINetwork service = RetroFitClient.getRetrofitInstance().create(APINetwork.class);
        Call<Movie> getMovieDetail = service.getMovie(idMovie, APINetwork.MOVIE_TOKEN);
        getMovieDetail.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movieDetail = response.body();

                    String URL = BASE_URL_IMG + movieDetail.getUrlBackground();
                    Picasso.get()
                            .load(URL)
                            .into(mThumbnailMovie);
                    mTitleMovie.setText(movieDetail.getTitle());
                    mRating.setText(Double.toString(movieDetail.getRating()));
                    mReleaseDate.setText(movieDetail.getRelease());
                    mSynopsis.setText(movieDetail.getSynopsis());
                    getSupportActionBar().setTitle(movieDetail.getTitle());


                } else {
                    System.out.println(response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetail.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


        Call<VideoAPIResponse> getVideos = service.getVideos(idMovie, APINetwork.MOVIE_TOKEN);
        getVideos.enqueue(new Callback<VideoAPIResponse>() {
            @Override
            public void onResponse(Call<VideoAPIResponse> call, Response<VideoAPIResponse> response) {
                if (response.isSuccessful()) {
                    videos = response.body().getResults();
                    loadVideos(videos);

                } else {
                    System.out.println(response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<VideoAPIResponse> call, Throwable t) {

            }
        });

        Call<ReviewAPIResponse> getReviews = service.getReviews(idMovie, APINetwork.MOVIE_TOKEN);
        getReviews.enqueue(new Callback<ReviewAPIResponse>() {
            @Override
            public void onResponse(Call<ReviewAPIResponse> call, Response<ReviewAPIResponse> response) {
                if(response.isSuccessful()){
                    reviews = response.body().getResults();
                    loadReviews(reviews);
                }
            }

            @Override
            public void onFailure(Call<ReviewAPIResponse> call, Throwable t) {

            }
        });


    }

    // load videos listView and add functionality for clicking items of list and open an intent
    public void loadVideos(List<Video> videosList) {
        ArrayList<Video> videos = new ArrayList<>();
        final VideoAdapter adapter = new VideoAdapter(this, 0,  videos);
        ListView listView = (ListView) findViewById(R.id.listVideos);
        listView.setAdapter(adapter);
        adapter.addAll(videosList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video videoURL = MovieDetail.this.videos.get(position);
                String videoID = videoURL.getKey();
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URL+videoID));
                intent.putExtra("VIDEO_ID", videoID);
                startActivity(intent);
            }


        });


    }

    public void loadReviews(List<Review> reviewsList) {
        ArrayList<Review> reviews = new ArrayList<>();
        final ReviewAdapter adapter = new ReviewAdapter(this, 0, reviews);
        ListView listViewReview = (ListView) findViewById(R.id.listReviews);
        listViewReview.setAdapter(adapter);
        adapter.addAll(reviewsList);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public boolean isFavMovie(List<Movie> moviesList, Movie movie){
        boolean isFav = false;
        isFav =  moviesList.contains(movie)? true : false;
        return isFav;

    }


}



