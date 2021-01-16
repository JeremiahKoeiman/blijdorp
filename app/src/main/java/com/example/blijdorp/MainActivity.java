package com.example.blijdorp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;

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

        getAllDocuments();

        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, mId, mName, mFeedingTime, mVideoUrl);
        listView.setAdapter(adapter);

    }

    // Get all the documents from the "dieren" collection
    protected void getAllDocuments() {
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
    }
}