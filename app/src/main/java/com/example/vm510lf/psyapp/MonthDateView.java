package com.example.vm510lf.psyapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.PointValue;

import static com.example.vm510lf.psyapp.MoodActivity.levelJudge;

public class MonthDateView extends View {
    private static final int NUM_COLUMNS = 7;
    private static final int NUM_ROWS = 6;
    private Paint mPaint;
    private int mDayColor = Color.parseColor("#000000");
    private int mSelectDayColor = Color.parseColor("#ffffff");
    private int mSelectBGColor= Color.parseColor("#9aff9a");
    private int mCurrentColor = Color.parseColor("#9aff9a");
    private int mForbiddenColor = Color.parseColor("#cfcfcf");
    private int mCurrYear, mCurrMonth, mCurrDay;
    private int mSelYear, mSelMonth, mSelDay;
    private int mFixSelMonth;
    private int mColumnSize, mRowSize;
    private DisplayMetrics mDisplayMetrics;
    private int mDaySize = 18;
    private TextView tv_date, tv_week;
    private int weekRow;
    private int[][] daysString;
    private int mCircleRadius = 6;
    private DateClick dateClick;
    private DateTouch dateTouch;
    private int mCircleColor = Color.parseColor("#ff0000");
    private List<Integer> daysHasThingList;
    private int[] sRecX = new int[59];
    private int[] sRecY = new int[59];
    private int[] eRecX = new int[59];
    private int[] eRecY = new int[59];
    private int[] dateX = new int[59];
    private int[] dateY = new int[59];
    private int[] mColor = new int[59];
    private int chooseNumber=0;
    private int ninthLock=0;

    private  String[] date = {"5-23"};
    private  int[] score= {74};

    //获得当前日期数据

    public MonthDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        Calendar calendar = Calendar.getInstance();
        mPaint = new Paint();
        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);
        setSelectYearMonth(mCurrYear, mCurrMonth, mCurrDay);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //绘制日历及颜色框

    @Override
    public void onDraw(Canvas canvas) {
        initSize();
        daysString = new int[6][7];
        mPaint.setTextSize(mDaySize * mDisplayMetrics.scaledDensity);
        String dayString;
        int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
        int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);
        Log.d("DateView", "DateView:" + mSelMonth + "月1号周" + weekNumber);
        for (int day = 0; day < mMonthDays; day++) {
            dayString = (day + 1) + "";
            int column = (day + weekNumber - 1) % 7;
            int row = (day + weekNumber - 1) / 7;
            daysString[row][column] = day + 1;
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint
                    .measureText(dayString)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint
                    .ascent() + mPaint.descent()) / 2);
            if (dayString.equals(mSelDay + "")) {
                if (ninthLock==0) {
                    // 绘制背景色矩形，使用数据存储
                    int startRecX = mColumnSize * column;
                    int startRecY = mRowSize * row;
                    int endRecX = startRecX + mColumnSize;
                    int endRecY = startRecY + mRowSize;
                    sRecX[chooseNumber] = startRecX;
                    sRecY[chooseNumber] = startRecY;
                    eRecX[chooseNumber] = endRecX;
                    eRecY[chooseNumber] = endRecY;
                    dateX[chooseNumber] = startRecX;
                    dateY[chooseNumber] = startRecY;
                    mColor[chooseNumber] = mSelectBGColor;

                    if (chooseNumber > 0) {
                        MoodActivity.mPointValues.add(new PointValue(chooseNumber, levelJudge));
                        MoodActivity.mAxisXValues.add(new AxisValue(chooseNumber).setLabel(dayString));
                    }


                    chooseNumber = chooseNumber + 1;
                    for (int i = 0; i < sRecX.length; i++) {

                        mPaint.setColor(mColor[i]);
                        canvas.drawRect(sRecX[i], sRecY[i], eRecX[i], eRecY[i], mPaint);

                    }

                    // 记录第几行，即第几周
                }
                weekRow = row + 1;
            }

            if (tv_date != null) {
                tv_date.setText(mSelYear + "年" + (mSelMonth + 1) + "月");
            }

            if (tv_week != null) {
                tv_week.setText("第" + weekRow + "周");
            }
        }
        for (int day = 0; day < mMonthDays; day++) {
            dayString = (day + 1) + "";
            int column = (day + weekNumber - 1) % 7;
            int row = (day + weekNumber - 1) / 7;
            daysString[row][column] = day + 1;
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint
                    .measureText(dayString)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint
                    .ascent() + mPaint.descent()) / 2);
            if (dayString.equals(mSelDay + "")) {
                mPaint.setColor(mSelectDayColor);
            } else if (dayString.equals(mCurrDay + "") && mCurrDay != mSelDay
                    && mCurrMonth == mSelMonth && mCurrYear == mSelYear) {
                //设置今日颜色与其它日期不同
                mPaint.setColor(mCurrentColor);
            } else {
                mPaint.setColor(mDayColor);
            }
            canvas.drawText(dayString, startX, startY, mPaint);
            if(ninthLock==1) {
                mPaint.setColor(mForbiddenColor);
                canvas.drawText(dayString, startX, startY, mPaint);
            }
        }

    }

    private void drawCircle(int row, int column, int day, Canvas canvas) {
        if (daysHasThingList != null && daysHasThingList.size() > 0) {
            if (!daysHasThingList.contains(day))
                return;
            mPaint.setColor(mCircleColor);
            float circleX = (float) (mColumnSize * column + mColumnSize * 0.8);
            float circley = (float) (mRowSize * row + mRowSize * 0.2);
            canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int downX = 0, downY = 0;
    private int offset = 0, upoffset = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offset = (int) (event.getX() - downX) / 3;
                upoffset = (int) (event.getY() - downY);


                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {// 点击事件
                    performClick();
                    doClickAction((upX + downX) / 2, (upY + downY) / 2);
                }
                if (Math.abs(offset) > 10 && offset > 0) {
                    doTouchAction("R");
                }
                if (Math.abs(offset) > 10 && offset < 0) {
                    doTouchAction("L");
                }
                if (Math.abs(upoffset) > 20 && upoffset > 0) {
                    doTouchAction("D");
                }
                if (Math.abs(upoffset) > 20 && upoffset < 0) {
                    doTouchAction("U");
                }

                break;
        }
        return true;
    }

    public void refreshBGColor(){
        invalidate();
    }

    private void initSize() {
        mColumnSize = getWidth() / NUM_COLUMNS;
        mRowSize = getHeight() / NUM_ROWS;
    }

    private void setSelectYearMonth(int year, int month, int day) {
        mSelYear = year;
        mSelMonth = month;
        mSelDay = day;
    }

    private void doTouchAction(String touch) {
        // 执行activity发送过来的点击处理事件
        if (dateTouch != null) {
            dateTouch.onTouchOnDate(touch);
        }
    }

    private void doClickAction(int x, int y) {
        int row = y / mRowSize;
        int column = x / mColumnSize;
        setSelectYearMonth(mSelYear, mSelMonth, daysString[row][column]);
        // 执行activity发送过来的点击处理事件
        if (dateClick != null) {
            dateClick.onClickOnDate();
        }
    }

    public void onLeftClick() {
        int year = mSelYear;
        int month = mSelMonth;
        if(ninthLock==0){mFixSelMonth=mSelMonth;}
        int day = mSelDay;
        if (month == 0) {// 若果是1月份，则变成12月份
            year = mSelYear - 1;
            month = 11;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            // 如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month - 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month - 1;
        }
        setSelectYearMonth(year, month, day);
        ninthLock=1;
        if(mSelMonth==mFixSelMonth){ninthLock=0;}
        invalidate();
    }

    public void onRightClick() {
        int year = mSelYear;
        int month = mSelMonth;
        if(ninthLock==0){mFixSelMonth=mSelMonth;}
        int day = mSelDay;
        if (month == 11) {// 若果是12月份，则变成1月份
            year = mSelYear + 1;
            month = 0;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            // 如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month + 1;
        }
        setSelectYearMonth(year, month, day);
        ninthLock=1;
        if(mSelMonth==mFixSelMonth){ninthLock=0;}
        invalidate();
    }

    public int getmSelYear() {
        return mSelYear;
    }

    public int getmSelMonth() {
        return mSelMonth;
    }

    public int getmSelDay() {
        return this.mSelDay;
    }

    public int getmSelDate() {
        return mSelYear * 10000 + (mSelMonth + 1) * 100+this.mSelDay;
    }

    public void setmDayColor(int mDayColor) {
        this.mDayColor = mDayColor;
    }

    public void setmSelectDayColor(int mSelectDayColor) {
        this.mSelectDayColor = mSelectDayColor;
    }

    public  void setmSelectBGColor(int mSelectBGColor) {
        this.mSelectBGColor = mSelectBGColor;
    }

    public void setmCurrentColor(int mCurrentColor) {
        this.mCurrentColor = mCurrentColor;
    }

    public void setmDaySize(int mDaySize) {
        this.mDaySize = mDaySize;
    }

    public void setTextView(TextView tv_date, TextView tv_week) {
        this.tv_date = tv_date;
        this.tv_week = tv_week;
    }

    public void setDaysHasThingList(List<Integer> daysHasThingList) {
        this.daysHasThingList = daysHasThingList;
    }

    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public interface DateClick {
        public void onClickOnDate();
    }

    public void setDateClick(DateClick dateClick) {
        this.dateClick = dateClick;
    }

    public void setTodayToView() {
        setSelectYearMonth(mCurrYear, mCurrMonth, mCurrDay);
    }

    public interface DateTouch {
        public void onTouchOnDate(String touch);
    }

    public void setDateTouch(DateTouch dateTouch) {
        this.dateTouch = dateTouch;
    }
}