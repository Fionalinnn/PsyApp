package com.example.vm510lf.psyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Titlebar extends LinearLayout {

    public Titlebar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        /*ImageButton imagebutton_backup = (ImageButton) this.<View>findViewById(R.id.backup);*/
        ImageButton imagebutton_main_home = (ImageButton) this.<View>findViewById(R.id.main_home);

        /*imagebutton_backup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });*/
        imagebutton_main_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home = new Intent("BackStudentPortActivity");
                intent_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent_home);
            }
        });
    }
}
