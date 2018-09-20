package com.example.vm510lf.psyapp;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Question> mQuestionList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTheme;
        TextView questionDetail;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            questionTheme = (TextView) view.findViewById(R.id.question_theme);
            questionDetail = (TextView) view.findViewById(R.id.question_detail);
            cardView = (CardView)view.findViewById(R.id.question_card);
        }
    }

    public QuestionAdapter(List<Question> questionsList) {
        mQuestionList = questionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Question question = mQuestionList.get(position);
                Intent intent = new Intent(parent.getContext(),QuestionAnswerActivity.class);
//                Log.d("QuestionAdapter", String.valueOf(parent.getContext().toString().contains("QuestionListActivity")));
//                Log.d("QuestionAdapter", String.valueOf(parent.getContext().toString().contains("MyQuestionActivity")));
                //如果当前界面是QuestionActivity或者MyquestionActivity,说明是学生端的互动
                //否则，为教师端的活动
                if(parent.getContext().toString().contains("QuestionListActivity") || parent.getContext().toString().contains("MyQuestionActivity"))
                    intent.putExtra(QuestionAnswerActivity.JUDGESTUDENT,"student");
                else
                    intent.putExtra(QuestionAnswerActivity.JUDGESTUDENT,"teacher");
                intent.putExtra(QuestionAnswerActivity.OBJECTID,question.getId());
//                Log.d("QuestionAdapter",question.getId());
                parent.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Question question = mQuestionList.get(position);
        holder.questionDetail.setText(question.getDetail());
        holder.questionTheme.setText(question.getTheme());
    }

    @Override
    public int getItemCount(){
        return mQuestionList.size();
    }
}

