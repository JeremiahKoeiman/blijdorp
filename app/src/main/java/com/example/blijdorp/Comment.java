package com.example.blijdorp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Comment extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

    // Initialize properties
    ListView commentListView;
    String[] mCommentVideoUrl = new String[5];
    String[] mCommentId = new String[5];
    String[] mComment = new String[5];
    String intentVideoUrlMessage;

    // Get a Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        TextView commentTextView = findViewById(R.id.editText_comment);
        Button buttonComment = findViewById(R.id.buttonComment);

        /*Intent intent = getIntent();
        intentVideoUrlMessage = intent.getStringExtra("EXTRA_MESSAGE");*/
        intentVideoUrlMessage = "https://www.youtube.com/watch?v=qCocJTdJiNg";

        getAllComments();

        String text = commentTextView.getText().toString();

        Map<String, Object> newComment = new Hashtable<>();
        newComment.put("comment_text", text);
        newComment.put("video_url", intentVideoUrlMessage);

        buttonComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int upperbound = 6;

                db.collection("comments").document(String.valueOf(rand.nextInt(upperbound))).set(newComment)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Comment.this, "Comment saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Comment.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Comment.this, "Error!: " + e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
            }
        });

        /*db
                .collection("comments")
                .whereEqualTo("video_url", intentVideoUrlMessage)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Log.d(TAG, document.getId() + " => " + document.getData());

                            int docId = Integer.parseInt(document.getId());
                            mCommentId[docId] = Integer.toString(docId);
                            mComment[docId] = (String) document.get("comment_text");
                            mCommentVideoUrl[docId] = (String) document.get("video_url");

                        /*TextView textView = findViewById(R.id.textView3);
                        textView.setText(mComment[docId]);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });*/

        /*Intent intent = getIntent();
        intentVideoUrlMessage = intent.getStringExtra(MyAdapter.EXTRA_MESSAGE);*/
        /*TextView textView = findViewById(R.id.textView3);
        textView.setText(intentVideoUrlMessage);*/

        commentListView = findViewById(R.id.commentListView);
        CommentAdapter adapter = new CommentAdapter(this, mCommentId, mComment, mCommentVideoUrl);
        commentListView.setAdapter(adapter);
    }

    public void getAllComments() {
        db
            .collection("comments")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d(TAG, document.getId() + " => " + document.getData());

                        int docId = Integer.parseInt(document.getId());
                        mCommentId[docId] = Integer.toString(docId);
                        mComment[docId] = (String) document.get("comment_text");
                        mCommentVideoUrl[docId] = (String) document.get("video_url");

                /*TextView textView = findViewById(R.id.textView3);
                textView.setText(mComment[docId]);*/
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            });
    }

    // Get al the documents from the "comments" collection
    /*protected void getAllComments() {
        db
                .collection("comments")
                .whereEqualTo("video_url", intentVideoUrlMessage)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Log.d(TAG, document.getId() + " => " + document.getData());

                            int docId = Integer.parseInt(document.getId());
                            mCommentId[docId] = Integer.toString(docId);
                            mComment[docId] = (String) document.get("comment_text");
                            mCommentVideoUrl[docId] = (String) document.get("video_url");

                        TextView textView = findViewById(R.id.textView3);
                        textView.setText(mComment[docId]);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }*/
}