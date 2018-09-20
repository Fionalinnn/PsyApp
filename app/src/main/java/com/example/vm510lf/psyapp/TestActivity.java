package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {

    Button bt_enter_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("心理测试");
        bt_enter_test = (Button) findViewById(R.id.enter_test);
        bt_enter_test.setOnClickListener(listener);
    }
    Button.OnClickListener listener = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(TestActivity.this,PaperActivity.class);
            startActivity(intent);
            TestActivity.this.finish();
        }
    };

    public void setupBackAsUp(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
}
