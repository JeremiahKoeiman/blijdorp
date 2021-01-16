package com.example.blijdorp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class MyAdapter extends ArrayAdapter<String> {

    // Instantiate global properties
    Context context;
    String[] rId;
    String[] rName;
    String[] rVideoUrl;
    String[] rFeedingTime;

    // Instantiate constructor
    MyAdapter(Context c, String[] id, String[] name, String[] feedingTime, String[] videoUrl) {
        super(c, R.layout.animal_item, id);
        this.context = c;
        this.rId = id;
        this.rName = name;
        this.rVideoUrl = videoUrl;
        this.rFeedingTime = feedingTime;
    }

    // Create the necessary layout using the animal_item.xml
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Get the necessary views from the animal_item.xml layout
        View row = inflater.inflate(R.layout.animal_item, parent, false);

        // TODO: the card gets the id of the document
        CardView myCard = (CardView) row.findViewById(R.id.card_view);

        Button myShowVideo = (Button) row.findViewById(R.id.showVideo);
        TextView myAnimalName = (TextView) row.findViewById(R.id.animalName);
        TextView myFeedingTime = (TextView) row.findViewById(R.id.feedingTime);
        WebView myDisplayVideo = (WebView) row.findViewById(R.id.displayVideo);


        // Implement onClick handler for myShowVideo button
        myShowVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDisplayVideo.loadUrl(rVideoUrl[position]);
            }
        });

        // Add the dynamic values to the animal_item.xml layout
        myAnimalName.setText(rName[position]);

        if (rFeedingTime[position] == null) {
            myFeedingTime.setText("Voedertijd: ");
        } else {
            myFeedingTime.setText("Voedertijd: " + rFeedingTime[position]);
        }

        // Returns a single row
        return row;
    }

}
