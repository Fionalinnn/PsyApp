package com.example.vm510lf.psyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 答题系统
 */
public class PaperActivity extends AppCompatActivity {

    //数据库的名称
    private String DB_NAME = "question.db";
    //数据库的地址
    private String DB_PATH = "/data/data/com.example.vm510lf.psyapp/databases/";
    //当前显示的题目
    private int corrent = 1;
    //问题
    private TextView tv_title;
    //选项
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;

    private int numA1 = 0;
    private int numB1 = 0;
    private int numC1 = 0;
    private int numD1 = 0;
    private int numA2 = 0;
    private int numB2 = 0;
    private int numC2 = 0;
    private int numD2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("SDS抑郁自评量表");

        initFile();
        initView();
        initDB();

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

    /**
     * 初始化View
     */
    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        buttonA = (Button) findViewById(R.id.btn_A);
        buttonB = (Button) findViewById(R.id.btn_B);
        buttonC = (Button) findViewById(R.id.btn_C);
        buttonD = (Button) findViewById(R.id.btn_D);

    }

    /**
     * 初始化数据库服务
     */
    private void initDB() {

        AVQuery question = new AVQuery("test");
        question.whereEqualTo("ID",corrent);
        question.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                AVObject q = list.get(0);
                tv_title.setText((String)q.get("question"));

                buttonA.setText((String)q.get("answerA"));
                buttonB.setText((String)q.get("answerB"));
                buttonC.setText((String)q.get("answerC"));
                buttonD.setText((String)q.get("answerD"));
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(corrent >= 20) {
                    numA2++;
                    new AlertDialog.Builder(PaperActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否提交？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long score = getScore();
                                    Intent intent = new Intent(PaperActivity.this, AnalyzeActivity.class);
                                    intent.putExtra("score", score);
                                    startActivity(intent);
                                    PaperActivity.this.finish();
                                }
                            }).setNegativeButton("取消",null).show();
                }else if(corrent > 10) {
                    corrent++;
                    numA2++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }else{
                    corrent++;
                    numA1++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }

            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(corrent >= 20) {
                    numB2++;
                    new AlertDialog.Builder(PaperActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否提交？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long score = getScore();
                                    Intent intent = new Intent(PaperActivity.this, AnalyzeActivity.class);
                                    intent.putExtra("score", score);
                                    startActivity(intent);
                                    PaperActivity.this.finish();
                                }
                            }).setNegativeButton("取消",null).show();
                }else if(corrent > 10){
                    corrent++;
                    numB2++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }else{
                    corrent++;
                    numB1++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }

            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(corrent >= 20) {
                    numC2++;
                    new AlertDialog.Builder(PaperActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否提交？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long score = getScore();
                                    Intent intent = new Intent(PaperActivity.this, AnalyzeActivity.class);
                                    intent.putExtra("score", score);
                                    startActivity(intent);
                                    PaperActivity.this.finish();
                                }
                            }).setNegativeButton("取消",null).show();
                }else if(corrent > 10){
                    corrent++;
                    numC2++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }else{
                    corrent++;
                    numC1++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }

            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(corrent >= 20) {
                    numD2++;
                    new AlertDialog.Builder(PaperActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否提交？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long score = getScore();
                                    Intent intent = new Intent(PaperActivity.this, AnalyzeActivity.class);
                                    intent.putExtra("score", score);
                                    startActivity(intent);
                                    PaperActivity.this.finish();
                                }
                            }).setNegativeButton("取消",null).show();
                }else if(corrent > 10){
                    corrent++;
                    numD2++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }else{
                    corrent++;
                    numD1++;
                    AVQuery question = new AVQuery("test");
                    question.whereEqualTo("ID",corrent);
                    question.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject q = list.get(0);
                            tv_title.setText((String)q.get("question"));

                            buttonA.setText((String)q.get("answerA"));
                            buttonB.setText((String)q.get("answerB"));
                            buttonC.setText((String)q.get("answerC"));
                            buttonD.setText((String)q.get("answerD"));
                        }
                    });

                }

            }
        });

    }

    /**
     * 将数据库拷贝到相应目录
     */
    private void initFile() {
        //判断数据库是否拷贝到相应的目录下
        if (new File(DB_PATH + DB_NAME).exists() == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }

            //复制文件
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                //用来复制文件
                byte[] buffer = new byte[1024];
                //保存已经复制的长度
                int length;

                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                //刷新
                os.flush();
                //关闭
                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 利用公式计算得分
     */
    public long getScore(){
        double score;
        score = (1*numA1 + 2*numB1 + 3*numC1 +4*numD1 +4*numA2 + 3*numB2 + 2*numC2 + 1*numD2)*1.25;
        long intscore = Math.round(score);
        return intscore;
    }
}
