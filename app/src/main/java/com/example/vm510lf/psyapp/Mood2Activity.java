package com.example.vm510lf.psyapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class Mood2Activity extends FragmentActivity {

    private MonthDateView colorChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_mood);
        colorChange = (MonthDateView) findViewById(R.id.monthDateView);
    }
//九个颜色选择按钮
    public void ClickRed1(View view){
        MoodActivity.colorJudge=1;
        Mood2Activity.this.finish();
    }

    public void ClickRed2(View view){
        MoodActivity.colorJudge=2;
        Mood2Activity.this.finish();
    }

    public void ClickRed3(View view){
        MoodActivity.colorJudge=3;
        Mood2Activity.this.finish();
    }

    public void ClickRed4(View view){
        MoodActivity.colorJudge=4;
        Mood2Activity.this.finish();
    }

    public void ClickRed5(View view){
        MoodActivity.colorJudge=5;
        Mood2Activity.this.finish();
    }

    public void ClickBlack1(View view){
        MoodActivity.colorJudge=6;
        Mood2Activity.this.finish();
    }

    public void ClickBlack2(View view){
        MoodActivity.colorJudge=7;
        Mood2Activity.this.finish();
    }

    public void ClickBlack3(View view){
        MoodActivity.colorJudge=8;
        Mood2Activity.this.finish();
    }

    public void ClickBlack4(View view){
        MoodActivity.colorJudge=9;
        Mood2Activity.this.finish();
    }
}
