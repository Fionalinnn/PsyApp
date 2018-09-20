package com.example.vm510lf.psyapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{

    //private static final String TAG = "ArticleAdapter";

    private Context mContext;

    private List<Article> mArticleList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView articleTitle;
        ImageView articleImage;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            articleImage = (ImageView) view.findViewById(R.id.article_image);
            articleTitle = (TextView) view.findViewById(R.id.article_title);
        }
    }

    public ArticleAdapter(List<Article> articleList){
        mArticleList = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Article article = mArticleList.get(position);
                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra(ArticleActivity.ARTICLE_TITLE, article.getTitle());
                intent.putExtra(ArticleActivity.ARTICLE_IMAGE_URL, article.getImageURL());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Article article = mArticleList.get(position);
        holder.articleTitle.setText(article.getTitle());
        Picasso.with(mContext).load(article.getImageURL()).into(holder.articleImage);
    }

    @Override
    public int getItemCount(){
        return mArticleList.size();
    }

}