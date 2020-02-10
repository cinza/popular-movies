package com.azuniga.udacitypopularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.azuniga.udacitypopularmovies.R;
import com.azuniga.udacitypopularmovies.models.Review;

import java.util.List;

import androidx.annotation.NonNull;

public class ReviewAdapter extends ArrayAdapter<Review> {


    public ReviewAdapter(@NonNull Context context, int resource, @NonNull List<Review> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Review review = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_review_item, parent, false);

        }
        // Lookup view for data population
        TextView mReviewAuthor = (TextView) convertView.findViewById(R.id.authorReview);
        TextView mReviewContent = (TextView) convertView.findViewById(R.id.contentReview);

        mReviewAuthor.setText(review.getAuthor());
        mReviewContent.setText(review.getContent());


        // Return the completed view to render on screen
        return convertView;
    }


}
