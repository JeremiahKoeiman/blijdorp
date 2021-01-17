package com.example.blijdorp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class CommentAdapter extends ArrayAdapter<String> {
    // Instantiate global properties
    Context context;
    String[] rCommentId;
    String[] rComment;
    String[] rCommentVideoUrl;

    // Instantiate constructor
    CommentAdapter(Context c, String[] id, String[] comment, String[] commentVideoUrl) {
        super(c, R.layout.activity_comment_row, id);
        context = c;
        rCommentId = id;
        rComment = comment;
        rCommentVideoUrl = commentVideoUrl;
    }

    // Create the necessary layout using the activity_comment_row.xml file
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*Log.d("COMMENT", "ID: " + rCommentId[position]);
        Log.d("COMMENT", "COMMENT: " + rComment[position]);
        Log.d("COMMENT", "VIDEO_URL: " + rCommentVideoUrl[position]);*/

        LayoutInflater inflater = LayoutInflater.from(context);

        // Get the necessary views from the animal_item.xml layout
        View row = inflater.inflate(R.layout.activity_comment_row, parent, false);

        TextView myComment = row.findViewById(R.id.actualComment);
        myComment.setText(rComment[position]);

        // Returns a single row
        return row;
    }
}