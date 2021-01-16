package com.example.blijdorp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

    // Initialize global properties
    TextView incomingTime;
    ListView listView;
    String[] mId = new String[4];
    String[] mName = new String[4];
    String[] mVideoUrl = new String[4];
    String[] mFeedingTime = new String[4];

    // Get a Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        incomingTime = (TextView) findViewById(R.id.incomingTime);
        incomingTime.setText("BINNENKOMST TIJD: " + new Date().getHours() + ":" + new Date().getMinutes());

        // Get all the documents from the "dieren" collection
        db.collection("dieren")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        // Loop through each document
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            // Get the document's id and store it in docId, which is of type int
                            int docId = Integer.parseInt(document.getId());

                            // Get the "voedertijd" field from th document and store it in timestamp, which is of type Timestamp
                            Timestamp timestamp = (Timestamp) document.get("voedertijd");

                            // Format saved timestamp, cast it to a string and store it in formattedTimestamp, which is of type String
                            String formattedTimestamp = timestamp.toDate().toString();

                            // Add the previously saved data to it's corresponding global String array property
                            mId[docId] = Integer.toString(docId);
                            mName[docId] = (String) document.get("naam");
                            mVideoUrl[docId] = (String) document.get("video_url");
                            mFeedingTime[docId] = formattedTimestamp;
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, mId, mName, mFeedingTime, mVideoUrl);
        listView.setAdapter(adapter);

        Log.d(TAG, "Adapter : " + adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {

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

            // TODO: The button sets the visibility of the Webview
            Button myShowVideo = (Button) row.findViewById(R.id.showVideo);

            TextView myAnimalName = (TextView) row.findViewById(R.id.animalName);
            TextView myFeedingTime = (TextView) row.findViewById(R.id.feedingTime);

            // TODO: show video
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
            myFeedingTime.setText("Voedertijd: " + rFeedingTime[position]);

            return row;
        }

    }

}