package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class BookActivity extends AppCompatActivity {
    Button bt_book_direct;
    Button bt_enter_test;
    Button bt_book_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bt_book_direct = (Button) findViewById(R.id.book_test);
        bt_book_direct.setOnClickListener(listener_test);
        bt_enter_test = (Button) findViewById(R.id.book_paper);
        bt_enter_test.setOnClickListener(listener_paper);
        bt_book_answer = (Button) findViewById(R.id.book_answer);
        bt_book_answer.setOnClickListener(listener_answer);

    }


    Button.OnClickListener listener_test = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(BookActivity.this,WebBookActivity.class);
            startActivity(intent);
            BookActivity.this.finish();
        }
    };

    Button.OnClickListener listener_paper = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(BookActivity.this,TestActivity.class);
            startActivity(intent);
            BookActivity.this.finish();
        }
    };

    Button.OnClickListener listener_answer = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(BookActivity.this,QuestionListActivity.class);
            startActivity(intent);
            BookActivity.this.finish();
        }
    };
}

