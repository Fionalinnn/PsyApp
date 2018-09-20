package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class ReadListActivity extends AppCompatActivity {

    final static String THEME = "theme";
    private DrawerLayout mDrawerLayout;
    //private Article[] articles = new Article[3];
    private List<Article> articleList = new ArrayList<>();
    private ArticleAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_list);

        Intent intent = getIntent();
        String theme = intent.getStringExtra(THEME);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        switch (theme){
            case "blue":
                setupBackAsUp("关于抑郁");
                break;
            case "consult":
                setupBackAsUp("心理咨询");
                break;
            case "zero":
                setupBackAsUp("心理零距离");
                break;
            case "test":
                setupBackAsUp("心理测试");
        }




        initArticles(theme);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshArticles();
            }
        });
    }

    private void refreshArticles() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //initArticles();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initArticles(final String theme){
        final AVQuery<AVObject> avQuery = new AVQuery<>("reading");
        avQuery.whereEqualTo("theme", theme);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                for (AVObject avObject : list) {
                    String title = avObject.getString("title");

                    String imageURL = avObject.getAVFile("picture").getUrl();
                    articleList.add(new Article(title, imageURL));

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void setupBackAsUp(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
