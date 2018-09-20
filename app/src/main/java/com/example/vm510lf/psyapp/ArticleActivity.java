package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tencent.qc.stat.StatReportStrategy.e;

public class ArticleActivity extends AppCompatActivity {

    public static final String ARTICLE_TITLE = "article_title";

    public static final String ARTICLE_IMAGE_URL = "article_image_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();

        String articleTitle = intent.getStringExtra(ARTICLE_TITLE);
        String articleImageURL = intent.getStringExtra(ARTICLE_IMAGE_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        ImageView articleImageView = (ImageView) findViewById(R.id.article_image_view);
        Picasso.with(ArticleActivity.this).load(articleImageURL).into(articleImageView);

        TextView articleContentText = (TextView) findViewById(R.id.article_content_text);
        articleContentText.setText(articleTitle);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(articleTitle);

        final AVQuery<AVObject> avQuery = new AVQuery<>("reading");
        avQuery.whereEqualTo("title", articleTitle);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                for (AVObject avObject : list) {
                    String articleContent = avObject.getString("content");
                    TextView content = (TextView) findViewById(R.id.article_content_text);
                    content.setText(articleContent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}