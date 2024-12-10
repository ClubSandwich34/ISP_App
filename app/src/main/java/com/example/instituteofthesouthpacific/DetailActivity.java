package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Article article = (Article) getIntent().getSerializableExtra("article");

        ImageView imageView = findViewById(R.id.detailImage);
        TextView title = findViewById(R.id.detailTitle);
        TextView date = findViewById(R.id.detailDate);
        TextView description = findViewById(R.id.detailDescription);

        Glide.with(this).load(article.getImage()).into(imageView);
        title.setText(article.getTitle());
        date.setText("Published on: " + article.getDate());
        description.setText(article.getDescription());
    }
}

