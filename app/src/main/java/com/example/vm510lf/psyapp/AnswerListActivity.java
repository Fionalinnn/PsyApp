package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class AnswerListActivity extends AppCompatActivity {
    private List<Question> questionList = new ArrayList<>();
    QuestionAdapter adapter = new QuestionAdapter(questionList);
    String content = new String();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("提问");

        initView();
        initQuestions();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbarbar,menu);
        return true;
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
        int i = item.getItemId();
        if (i == R.id.my_history) {
            Intent intent = new Intent(AnswerListActivity.this, MyAnswerActivity.class);
            startActivity(intent);

        } else if (i == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

    private void initView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.QA_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initQuestions() {
        AVQuery<AVObject> question = new AVQuery<>("Question");
        question.orderByDescending("createdAt");
        question.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        question.setMaxCacheAge(24 * 3600 * 1000);
        question.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(final AVObject avObject:list){

                    final String theme = (String)avObject.get("theme");
                    final Question question_item = new Question(theme,index);
                    questionList.add(question_item);
                    index ++;

                    AVObject questionData = AVObject.createWithoutData("Question",avObject.getObjectId());
                    AVQuery<AVObject> query = new AVQuery<>("Detail");

                    query.whereEqualTo("question", questionData);
                    query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
                    query.setMaxCacheAge(24 * 3600 * 1000);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            content = (String) list.get(0).get("content");

                            questionList.get(question_item.getIndex()).setId(avObject.getObjectId());
                            Log.d("QuestionListId",questionList.get(question_item.getIndex()).getId());
                            questionList.get(question_item.getIndex()).setDetail(content);

                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
