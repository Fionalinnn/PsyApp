package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnalyzeActivity extends AppCompatActivity {

    Button bt_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        bt_book = (Button) findViewById(R.id.enter_book);
        bt_book.setOnClickListener(listener_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("测试结果");

        Intent intent = getIntent();
        long score = intent.getLongExtra("score", 1);

        TextView textview = (TextView) findViewById(R.id.score);
        textview.setText("分数\n"+score);

        if(score <= 50){
            TextView textview_talk = (TextView) findViewById(R.id.tv_result);
            textview_talk.setText("没有抑郁的烦恼\n要继续保持哦");
        }

        else if (score > 50 && score <= 60){
            TextView textview_talk = (TextView) findViewById(R.id.tv_result);
            textview_talk.setText("需要注意一下情绪喽\n可能有抑郁的倾向");
        }
        else {
            TextView textview_talk = (TextView) findViewById(R.id.tv_result);
            textview_talk.setText("稍微有些严重喽\n拜访一下医生吧");
        }
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

    Button.OnClickListener listener_book = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(AnalyzeActivity.this,WebBookActivity.class);
            startActivity(intent);
        }
    };
}
