package com.azuniga.udacitypopularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.azuniga.udacitypopularmovies.R;
import com.azuniga.udacitypopularmovies.models.Video;

import java.util.List;

import androidx.annotation.NonNull;

public class VideoAdapter  extends ArrayAdapter<Video> {

    public VideoAdapter(@NonNull Context context, int resource, @NonNull List<Video> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Video video = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_videos, parent, false);

        }
        // Lookup view for data population
        TextView mVideoItem = (TextView) convertView.findViewById(R.id.textViewVideo);
        String videoType = video.getSite();
        if(videoType.equals("YouTube")){
            mVideoItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_youtube, 0, 0, 0);

        }

        mVideoItem.setText(video.getName());

        // Return the completed view to render on screen
        return convertView;
    }

}
