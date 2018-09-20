package com.example.vm510lf.psyapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class MyAnswerActivity extends AppCompatActivity {

    private List<Question> myanswerList = new ArrayList<>();
    String content = new String();
    QuestionAdapter adapter = new QuestionAdapter(myanswerList);
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("我的回答");

        initView();
        initQuestions();
    }

    public void setupBackAsUp(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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

    private void initView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_QA_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initQuestions() {

        AVQuery<AVObject> question = new AVQuery<>("Question");
        //查找当前用户所回答的问题
        question.whereContains("teacher_number",MyLeanCloudApp.usersId);
        //按最后一次更改时间降序排序
        question.orderByDescending("updatedAt");
        //缓存
        question.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        question.setMaxCacheAge(24 * 3600 * 1000);
        question.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(final AVObject avObject:list){

                    final String theme = (String)avObject.get("theme");
                    //先进行一次排序，确保显示在RecylerView中的item顺序是正确的，用index记录当前项的序号
                    final Question question_item = new Question(theme,index);
                    myanswerList.add(question_item);
                    index ++;

                    AVObject questionData = AVObject.createWithoutData("Question",avObject.getObjectId());
                    AVQuery<AVObject> query = new AVQuery<>("Detail");

                    //查找该问题的内容
                    query.whereEqualTo("question", questionData);
                    //缓存
                    query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
                    query.setMaxCacheAge(24 * 3600 * 1000);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            content = (String) list.get(0).get("content");

                            //将id和content存在相对应的item中
                            myanswerList.get(question_item.getIndex()).setId(avObject.getObjectId());
                            myanswerList.get(question_item.getIndex()).setDetail(content);

                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
