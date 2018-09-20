package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StudentPortActivity extends AppCompatActivity {

    Button bt_book;
    Button bt_read;
    Button bt_test;
    Button bt_mood;
    Button bt_answer;
    Button bt_talk;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_port);
        bt_book = (Button) findViewById(R.id.main_book);
        bt_book.setOnClickListener(listener_book);
        bt_read = (Button) findViewById(R.id.main_read);
        bt_read.setOnClickListener(listener_read);
        bt_test = (Button) findViewById(R.id.main_test);
        bt_test.setOnClickListener(listener_test);
        bt_mood = (Button) findViewById(R.id.main_mood);
        bt_mood.setOnClickListener(listener_mood);
        bt_answer = (Button) findViewById(R.id.main_answer);
        bt_answer.setOnClickListener(listener_answer);
        bt_talk = (Button) findViewById(R.id.main_talk);
        bt_talk.setOnClickListener(listener_talk);

        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("心灵之约");
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_reorder);
        }

        navView.setCheckedItem(R.id.nav_history);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    public void setupBackAsUp(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
    Button.OnClickListener listener_book = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(StudentPortActivity.this,BookActivity.class);
            startActivity(intent);

        }
    };
    Button.OnClickListener listener_read = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(StudentPortActivity.this,ReadingActivity.class);
            startActivity(intent);
        }
    };
    Button.OnClickListener listener_test = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(StudentPortActivity.this,TestActivity.class);
            startActivity(intent);
        }
    };
    Button.OnClickListener listener_mood = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(StudentPortActivity.this,MoodActivity.class);
            startActivity(intent);
        }
    };
    Button.OnClickListener listener_answer = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(StudentPortActivity.this,QuestionListActivity.class);
            startActivity(intent);
        }
    };
    Button.OnClickListener listener_talk = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent();
            intent.setClassName(StudentPortActivity.this,"com.netease.nim.demo.main.activity.MainActivity");
            startActivity(intent);
        }
    };
}