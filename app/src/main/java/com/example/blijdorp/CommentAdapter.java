package com.example.blijdorp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class CommentAdapter extends ArrayAdapter<String> {
    // Instantiate global properties
    Context context;
    String[] rCommentId;
    String[] rComment;
    String[] rCommentVideoUrl;

    // Instantiate constructor
    CommentAdapter(Context c, String[] id, String[] comment, String[] commentVideoUrl) {
        super(c, R.layout.comment, id);
        this.context = c;
        this.rCommentId = id;
        this.rComment = comment;
        this.rCommentVideoUrl = commentVideoUrl;
    }

    // Create the necessary layout using the animal_item.xml
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Get the necessary views from the animal_item.xml layout
        View row = inflater.inflate(R.layout.comment, parent, false);

        TextView myComment = (TextView) row.findViewById(R.id.comment);

        if (rComment[position] != null) {
            myComment.setText(rComment[position]);
        }

        // Returns a single row
        return row;
    }
}
