package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MoodActivity extends AppCompatActivity {
    public static int colorJudge = 0;
    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_date;
    private TextView tv_week;
    private TextView tv_today;
    private MonthDateView monthDateView;

    public static MoodActivity dataPoint;

    // figure
    private LineChartView lineChart;
    private static String[] date = {"5-23"};
    private static int[] score= {74};
    public static List<PointValue> mPointValues = new ArrayList<PointValue>();
    public static List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    public static int levelJudge = 0;
    private int dataNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Integer> list = new ArrayList<Integer>();
        list.add(20160810);
        list.add(20160812);
        list.add(20160815);
        list.add(20160816);
        setContentView(R.layout.activity_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("心情记录");
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);

        tv_date = (TextView) findViewById(R.id.date_text);
        tv_week = (TextView) findViewById(R.id.week_text);
        tv_today = (TextView) findViewById(R.id.tv_today);
        monthDateView.setTextView(tv_date, tv_week);
        monthDateView.setDaysHasThingList(list);
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {


                Intent intent=new Intent(MoodActivity.this,Mood2Activity.class);
                startActivity(intent);
            }
        });
        monthDateView.setDateTouch(new MonthDateView.DateTouch() {

            @Override
            public void onTouchOnDate(String touch) {
                if(touch.equals("L")){
                    monthDateView.onRightClick();
                }
                if(touch.equals("R")){
                    monthDateView.onLeftClick();
                }
            }
        });
        setOnlistener();

        //figure
        lineChart = (LineChartView)findViewById(R.id.line_chart);
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



    private void setOnlistener() {

        iv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onLeftClick();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onRightClick();
            }
        });

        tv_today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.setTodayToView();
            }
        });

    }

    @Override
    //颜色选择
    protected void onResume() {
        super.onResume();
        switch (colorJudge){
            case 1:
                monthDateView.setmSelectBGColor(Color.parseColor("#ff0000"));
                levelJudge = 9;
                break;
            case 2:
                monthDateView.setmSelectBGColor(Color.parseColor("#fe2e2e"));
                levelJudge = 8;
                break;
            case 3:
                monthDateView.setmSelectBGColor(Color.parseColor("#fa5858"));
                levelJudge = 7;
                break;
            case 4:
                monthDateView.setmSelectBGColor(Color.parseColor("#f78181"));
                levelJudge = 6;
                break;
            case 5:
                monthDateView.setmSelectBGColor(Color.parseColor("#fbefef"));
                levelJudge = 5;
                break;
            case 6:
                monthDateView.setmSelectBGColor(Color.parseColor("#d8d8d8"));
                levelJudge = 4;
                break;
            case 7:
                monthDateView.setmSelectBGColor(Color.parseColor("#bdbdbd"));
                levelJudge = 3;
                break;
            case 8:
                monthDateView.setmSelectBGColor(Color.parseColor("#a4a4a4"));
                levelJudge = 2;
                break;
            case 9:
                monthDateView.setmSelectBGColor(Color.parseColor("#848484"));
                levelJudge = 1;
                break;
        }
        monthDateView.refreshBGColor();
        initLineChart();
    }

    //figure
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(false);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.parseColor("#D6D6D9"));

        axisX.setTextSize(11);
        axisX.setMaxLabelChars(7);
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);

        Axis axisY = new Axis();
        axisY.setName("");
        axisY.setTextSize(11);
        data.setAxisYLeft(axisY);

        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 3);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }


    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }
    public void getFigureElements(String dayString){
        score[dataNumber]=levelJudge;
        date[dataNumber]=dayString;
        mPointValues.add(new PointValue(dataNumber, score[dataNumber]));
        mAxisXValues.add(new AxisValue(dataNumber).setLabel(date[dataNumber]));
        dataNumber =dataNumber+1;
    }

}