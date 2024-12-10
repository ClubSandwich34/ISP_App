package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Parse XML and set up the RecyclerView
        List<Article> articles = NewsParser.parseNews(getResources().openRawResource(R.raw.news));
        ArticleAdapter adapter = new ArticleAdapter(articles, this::onArticleClick);
        recyclerView.setAdapter(adapter);
    }

    private void onArticleClick(Article article) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }
}
class NewsParser {
    public static List<Article> parseNews(InputStream inputStream) {
        List<Article> articles = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            Article currentArticle = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("article".equals(tagName)) {
                            currentArticle = new Article();
                        } else if (currentArticle != null) {
                            switch (tagName) {
                                case "title":
                                    currentArticle.setTitle(parser.nextText());
                                    break;
                                case "image":
                                    currentArticle.setImage(parser.nextText());
                                    break;
                                case "description":
                                    currentArticle.setDescription(parser.nextText());
                                    break;
                                case "date":
                                    currentArticle.setDate(parser.nextText());
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("article".equals(tagName) && currentArticle != null) {
                            articles.add(currentArticle);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}