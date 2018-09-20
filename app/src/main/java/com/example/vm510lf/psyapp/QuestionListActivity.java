package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.ImageButton;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private List<Question> questionList = new ArrayList<>();
    QuestionAdapter adapter = new QuestionAdapter(questionList);
    String content = new String();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("匿名提问");

        FloatingActionButton button_to_question = (FloatingActionButton)findViewById(R.id.to_question);
        button_to_question.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(QuestionListActivity.this, ToQuestionActivity.class);
                startActivity(intent);
            }
        });

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
            Intent intent = new Intent(QuestionListActivity.this, MyQuestionActivity.class);
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
                    questionList.add(question_item);
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
                            //取detail中的第一项
                            content = (String) list.get(0).get("content");
                            //将id和content存在相对应的item中
                            questionList.get(question_item.getIndex()).setId(avObject.getObjectId());
//                            Log.d("QuestionListId",questionList.get(question_item.getIndex()).getId());
                            questionList.get(question_item.getIndex()).setDetail(content);

                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
