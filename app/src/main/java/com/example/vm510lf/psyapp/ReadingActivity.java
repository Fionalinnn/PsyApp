package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ReadingActivity extends AppCompatActivity {

    Button bt_blue;
    Button bt_counsel;
    Button bt_distance;
    Button bt_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("阅读");
        bt_blue = (Button) findViewById(R.id.button_blue);
        bt_blue.setOnClickListener(listener_blue);
        bt_counsel = (Button) findViewById(R.id.button_counsel);
        bt_counsel.setOnClickListener(listener_counsel);
        bt_distance = (Button) findViewById(R.id.button_zero);
        bt_distance.setOnClickListener(listener_distance);
        bt_test = (Button) findViewById(R.id.button_test);
        bt_test.setOnClickListener(listener_test);
    }

    public void setupBackAsUp(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    Button.OnClickListener listener_blue = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(ReadingActivity.this,ReadListActivity.class);
            intent.putExtra(ReadListActivity.THEME, "blue");
            startActivity(intent);
        }
    };

    Button.OnClickListener listener_counsel = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(ReadingActivity.this,ReadListActivity.class);
            intent.putExtra(ReadListActivity.THEME, "consult");
            startActivity(intent);
        }
    };

    Button.OnClickListener listener_distance = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(ReadingActivity.this,ReadListActivity.class);
            intent.putExtra(ReadListActivity.THEME, "zero");
            startActivity(intent);
        }
    };

    Button.OnClickListener listener_test = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(ReadingActivity.this,ReadListActivity.class);
            intent.putExtra(ReadListActivity.THEME, "test");
            startActivity(intent);
        }
    };

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
