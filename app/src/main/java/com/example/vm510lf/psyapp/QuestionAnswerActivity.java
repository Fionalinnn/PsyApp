package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private int type;
    String content;
    Boolean isStudent;
    String teacherName;

    public static final String OBJECTID = "question_id";
    public static final String JUDGESTUDENT = "judgeStudent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        //初始化消息数据

        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("匿名提问");

        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);

        initMsgs();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = inputText.getText().toString();
                Intent intent = getIntent();
                //接受传送过来的问题的id和消息的发送者
                final String questionId = intent.getStringExtra(QuestionAnswerActivity.OBJECTID);
                final String judgestudent = intent.getStringExtra(QuestionAnswerActivity.JUDGESTUDENT);

                AVQuery findQuestionId = new AVQuery<>("Question");
//                Log.d("QAquestionid",questionId);
                findQuestionId.getInBackground(questionId, new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        String studentNumber = (String)avObject.get("student_number");
//                        Log.d("QAstudentnumber",studentNumber);
                        //判断当前用户的id是否与提问者相同，若相同，则可以补充内容，不同则通过Toast提示
                        if(studentNumber.equals(MyLeanCloudApp.usersId)&&judgestudent.equals("student")){
                            AVObject detail = new AVObject("Detail");
                            //存储消息的信息

                            detail.put("content",content);
                            detail.put("isStudent",true);

                            if(judgestudent.equals("student")){
                                //如果是学生发的，信息的类型设为发送
                                type = Msg.TYPE_SENT;
                                detail.put("isStudent",true);
                            }
                            else{
                                //如果是老师发的，信息的类型设为接收
                                type = Msg.TYPE_RECEIVED;
                                detail.put("isStudent",false);

                                //将老师的id储存在Question中，用于查找“我的回答”的item
                                AVQuery questionquery = new AVQuery<>("Question");
                                questionquery.getInBackground(questionId, new GetCallback<AVObject>() {
                                    @Override
                                    public void done(AVObject avObject, AVException e) {
                                        String number = (String) avObject.get("teacher_number");
                                        //如果老师的id已经包含在"teacher_number"中，则不操作
                                        //如果老师的id不包含在"teacher_number"，则将id加入其中
                                        if(!number.contains(MyLeanCloudApp.usersId))
                                            number = number.concat(MyLeanCloudApp.usersId.concat(" "));
                                        //保存number
                                        avObject.put("teacher_number",number);
                                        avObject.saveInBackground();
                                    }

                                });
                            }

                            detail.put("content",content);
                            //储存这条消息是谁发的，用于显示在对话框的上面
                            detail.put("teacher",MyLeanCloudApp.usersId);

                            AVObject question = AVObject.createWithoutData("Question",questionId);
                            detail.put("question",question);
                            detail.saveInBackground();

                            if (!"".equals(content)){
                                Msg msg = new Msg(content,type,MyLeanCloudApp.usersId);
                                msgList.add(msg);
                                //当有新消息时，刷新RecyclerView中的显示
                                adapter.notifyItemInserted(msgList.size()-1);
                                //将RecyclerView定位到最后一行
                                recyclerView.scrollToPosition(msgList.size() - 1);
                                //清空输入框中的内容
                                inputText.setText("");
                            }
                        }
                        else {
                            Toast.makeText(QuestionAnswerActivity.this,"小可爱，你不能在非自己提问的问题下发言哦，去试着自己提个问题吧~",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }

    public void setupBackAsUp(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }

    //初始化对话框
    private void initMsgs() {
        Intent intent = getIntent();
        String questionId = intent.getStringExtra(QuestionAnswerActivity.OBJECTID);
        //传递当前提问的id
        AVQuery<AVObject> detailquery = new AVQuery<>("Detail");
        AVObject questionData = AVObject.createWithoutData("Question",questionId);
        //查询该id的问题
        detailquery.whereEqualTo("question", questionData);
        //按照创建的时间升序排列
        detailquery.orderByAscending("createdAt");
        //缓存
        detailquery.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        detailquery.setMaxCacheAge(24 * 3600 * 1000);
        detailquery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(AVObject detail:list){
                    //获取每条对话的内容、是否是学生发送的、以及老师的名字
                    content = (String)detail.get("content");
                    isStudent = (Boolean)detail.get("isStudent");
                    teacherName = (String)detail.get("teacher");
                    if(isStudent){
                        type = Msg.TYPE_SENT;
                    }
                    else {
                        type = Msg.TYPE_RECEIVED;
                    }
                    //用构造器创建一个msg
                    Msg msg = new Msg(content,type,teacherName);
                    //将消息加入RecylerView中
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                }
            }
        });
    }
}
