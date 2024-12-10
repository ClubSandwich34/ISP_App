package com.example.instituteofthesouthpacific;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private final List<Article> articles;
    private final OnArticleClickListener listener;

    public ArticleAdapter(List<Article> articles, OnArticleClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        Glide.with(holder.image.getContext()).load(article.getImage()).into(holder.image);
        holder.itemView.setOnClickListener(v -> listener.onArticleClick(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.articleImage);
            title = itemView.findViewById(R.id.articleTitle);
        }
    }

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }
}
