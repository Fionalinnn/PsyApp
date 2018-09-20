package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    RadioButton bt_student;
    RadioButton bt_teacher;
    Button bt_login;
    Boolean isStudent = true;
    Boolean isPush = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        bt_student = (RadioButton) findViewById(R.id.log_in_student);
        bt_student.setOnClickListener(listener_student);
        bt_teacher = (RadioButton) findViewById(R.id.log_in_teacher);
        bt_teacher.setOnClickListener(listener_teacher);
        bt_login = (Button) findViewById(R.id.log_in);
        bt_login.setOnClickListener(listener_login);
    }


    RadioButton.OnClickListener listener_student = new Button.OnClickListener(){
        public void onClick(View v){
            isStudent = true;
            isPush = true;
        }
    };

    RadioButton.OnClickListener listener_teacher = new Button.OnClickListener(){
        public void onClick(View v){
            isStudent = false;
            isPush = true;
        }
    };

    Button.OnClickListener listener_login = new Button.OnClickListener(){
        public void onClick(View v){
            EditText userName = (EditText) findViewById(R.id.user_name);
            MyLeanCloudApp.usersId = userName.getText().toString();
            if (isPush) {
                if (!MyLeanCloudApp.usersId.equals("")) {
                    if (isStudent) {
                        Intent intent = new Intent(LogInActivity.this, StudentPortActivity.class);
                        startActivity(intent);
                        LogInActivity.this.finish();
                    } else {
                        Intent intent = new Intent(LogInActivity.this, AnswerListActivity.class);
                        startActivity(intent);
                        LogInActivity.this.finish();
                    }
                }
                else{
                    Toast.makeText(LogInActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(LogInActivity.this, "请选择用户", Toast.LENGTH_SHORT).show();
            }
        }
    };
}

