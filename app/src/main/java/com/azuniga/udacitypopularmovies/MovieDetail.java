package com.azuniga.udacitypopularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azuniga.udacitypopularmovies.models.Movie;
import com.azuniga.udacitypopularmovies.utils.APINetwork;
import com.azuniga.udacitypopularmovies.utils.RetroFitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {
    Movie movieDetail ;
    TextView mTitleMovie;
    TextView mRating;
    TextView mReleaseDate;
    TextView mSypnosis;
    ImageView mThumbnailMovie;
    static final String BASE_URL_IMG ="https://image.tmdb.org/t/p/w342/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_movie_detail);
        Intent intent = getIntent();
        String idMovie = intent.getStringExtra(MainActivity.ID_MOVIE);
        mTitleMovie = findViewById(R.id.titleMovie);
        mThumbnailMovie = findViewById (R.id.thumbnailMovie);
        mRating = findViewById (R.id.rateMovie);
        mReleaseDate = findViewById (R.id.releaseDate);
        mSypnosis = findViewById (R.id.sypnosisMovie);

        loadMovie(idMovie);
    }

    public void loadMovie(String idMovie){
        APINetwork service = RetroFitClient.getRetrofitInstance().create(APINetwork.class);
        Call<Movie> getMovieDetail = service.getMovie( idMovie, APINetwork.MOVIE_TOKEN);
        getMovieDetail.enqueue (new Callback<Movie> () {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    movieDetail = response.body();

                    String URL = BASE_URL_IMG + movieDetail.getUrlBackground ();
                    Picasso.get()
                            .load(URL)
                            .into(mThumbnailMovie);
                    mTitleMovie.setText( movieDetail.getTitle());
                    mRating.setText(Double.toString (movieDetail.getRating()));
                    mReleaseDate.setText (movieDetail.getRelease());
                    mSypnosis.setText(movieDetail.getSynopsis());


                } else {
                    System.out.println(response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetail.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }


