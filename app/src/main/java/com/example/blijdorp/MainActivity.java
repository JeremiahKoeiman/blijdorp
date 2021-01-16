package com.example.blijdorp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

    ListView listView;
    String[] mId = new String[4];
    String[] mName = new String[4];
    String[] mVideoUrl = new String[4];
    String[] mFeedingTime = new String[4];

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.collection("dieren")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            int docId = Integer.parseInt(document.getId());

                            Timestamp timestamp = (Timestamp) document.get("voedertijd");
                            String formattedTimestamp = timestamp.toDate().toString();

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

        Context context;
        String[] rId;
        String[] rName;
        String[] rVideoUrl;
        String[] rFeedingTime;

        MyAdapter(Context c, String[] id, String[] name, String[] feedingTime, String[] videoUrl) {
            super(c, R.layout.animal_item, id);
            this.context = c;
            this.rId = id;
            this.rName = name;
            this.rVideoUrl = videoUrl;
            this.rFeedingTime = feedingTime;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(R.layout.animal_item, parent, false);

            // TODO: the card gets the id of the document
            CardView myCard = row.findViewById(R.id.card_view);

            // TODO: The button sets the visibility of the Webview
            Button myShowVideo = row.findViewById(R.id.showVideo);

            TextView myAnimalName = row.findViewById(R.id.animalName);
            TextView myFeedingTime = row.findViewById(R.id.feedingTime);

            // TODO: show video
            WebView myDisplayVideo = row.findViewById(R.id.displayVideo);

            myAnimalName.setText(rName[position]);
            myFeedingTime.setText("Voedertijd: " + rFeedingTime[position]);

            return row;
        }

    }

}