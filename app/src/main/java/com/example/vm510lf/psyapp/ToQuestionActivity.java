package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;

public class ToQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("我要提问");

        final EditText txt_question_theme = (EditText)findViewById(R.id.theme_to_question);
        final EditText txt_question_detail = (EditText)findViewById(R.id.detail_to_question);

        Button button_send_question = (Button)findViewById(R.id.send_question);
        button_send_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question_theme = txt_question_theme.getText().toString();
                String question_detail = txt_question_detail.getText().toString();

                if(question_theme.equals("")){
                    Toast.makeText(ToQuestionActivity.this, "主题栏不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(question_detail.equals("")){
                    Toast.makeText(ToQuestionActivity.this, "问题的内容不能为空！", Toast.LENGTH_SHORT).show();
                }
                else{
                    AVObject question = new AVObject("Question");
                    AVObject detail = new AVObject("Detail");

                    question.put("theme",question_theme);
                    question.put("student_number",MyLeanCloudApp.usersId);
                    question.put("teacher_number","");

                    detail.put("content",question_detail);
                    detail.put("isStudent",true);
                    detail.put("question",question);
                    detail.saveInBackground();

                    Toast.makeText(ToQuestionActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ToQuestionActivity.this,QuestionListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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
