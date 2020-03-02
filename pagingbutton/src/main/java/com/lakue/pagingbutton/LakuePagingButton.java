package com.lakue.pagingbutton;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class LakuePagingButton extends LinearLayout {

    Context context;

    int beforePage = -1;

    //31. SEL_ADDPAGEBUTTON_TYPE
    public static final int ADD_PAGE_TYPE_FIRST= 10;
    public static final int ADD_PAGE_TYPE_FIRST_NEXT = 11;
    public static final int ADD_PAGE_TYPE_CENTER = 12;
    public static final int ADD_PAGE_TYPE_LAST_BEFORE = 13;

    OnPageSelectListener onPageSelectListener;

    int MAX_PAGE = 5;

    public LakuePagingButton(Context context) {
        super(context);
        this.context = context;
        initLayout();
    }

    public LakuePagingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLayout();
    }

    public LakuePagingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initLayout();
    }

    private void initLayout(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        this.setGravity(Gravity.CENTER);
    }



    private int pageType(int startpage, int maxpage){
        //다음 버튼이 없는 처음페이지
        if(maxpage <= MAX_PAGE ){
            return ADD_PAGE_TYPE_FIRST;
        }

        //다음 버튼이 있는 페이지
        if(startpage <= 1){
            return ADD_PAGE_TYPE_FIRST_NEXT;
        }


        if(startpage-1 >= MAX_PAGE && maxpage >= startpage+MAX_PAGE){
            return ADD_PAGE_TYPE_CENTER;
        }

//        if(startpage-1 >= 5 && maxpage < startpage-5){
//            return Define.ADD_PAGE_TYPE_LAST_BEFORE;
//        }

        //이전버튼이 있는 페이지
        if(startpage+MAX_PAGE > maxpage){
            return ADD_PAGE_TYPE_LAST_BEFORE;
        }
        return -1;
    }

    public void setPageItemCount(int count){
        MAX_PAGE = count;
    }

    private void setText(TextView textView,int i){
        textView.setId(i + 1);
        textView.setTag(i + 1);
        textView.setText(String.valueOf(i + 1));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(20,20,20,20);
        textView.setBackgroundColor(Color.TRANSPARENT);
    }

    TextView textView;
    public void addBottomPageButton(int listsize, int nowpage) {
        this.removeAllViews();
        int nowpage_paging = (int)Math.ceil(nowpage/MAX_PAGE);
        if(nowpage % MAX_PAGE == 0){
            nowpage_paging = nowpage_paging -1;
        }

        final int startpage = nowpage_paging * MAX_PAGE;
        int lastpage = (nowpage_paging+1) * MAX_PAGE;

        if(lastpage > listsize){
            lastpage = listsize;
        }
        switch (pageType(startpage+1,listsize)){
            case ADD_PAGE_TYPE_FIRST:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_FIRST");
                for (int i = startpage; i < lastpage; i++) {
                    textView = new TextView(context);
                    setText(textView,i);

                    if (i == 0) {
                        textView.setTextColor(Color.BLACK);
                    } else {
                        textView.setTextColor(context.getResources().getColor(R.color.colorGray));
                    }

                    final int finalLastpage = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage);
                            textView = (TextView) v.findViewById(v.getId());
                            textView.setTextColor(Color.BLACK);
                            onPageSelectListener.onPageCenter(textView.getId());
                        }
                    });

                    this.addView(textView);
                }
                break;
            case ADD_PAGE_TYPE_FIRST_NEXT:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_FIRST_NEXT");
                for (int i = startpage; i < lastpage+1; i++) {
                    textView = new TextView(context);
                    setText(textView,i);

                    if(i == lastpage){
                        textView.setText("다음");
                        textView.setTextSize(15);
                    }

                    if(beforePage > 0){
                        if (i == beforePage) {
                            textView.setTextColor(Color.BLACK);
                            beforePage = -1;
                        } else {
                            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
                        }
                    } else {
                        if (i == startpage) {
                            textView.setTextColor(Color.BLACK);
                        } else {
                            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
                        }
                    }

                    final int finalLastpage1 = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage1);
                            textView = (TextView) v.findViewById(v.getId());
                            if(textView.getText().toString().equals("다음")){
                                onPageSelectListener.onPageNext(textView.getId());
                            } else if(textView.getText().toString().equals("이전")){
                                onPageSelectListener.onPageBefore(textView.getId());
                                beforePage = startpage-1;
                            } else {
                                onPageSelectListener.onPageCenter(textView.getId());
                            }

                           // textView.setTextColor(Color.BLACK);
                        }
                    });
                    this.addView(textView);
                }
                break;
            case ADD_PAGE_TYPE_CENTER:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_CENTER");
                for (int i = startpage-1; i < lastpage+1; i++) {
                    textView = new TextView(context);

                    setText(textView,i);

                    if(i == lastpage){
                        textView.setText("다음");
                        textView.setTextSize(15);
                    } else if(i == startpage-1){
                        textView.setText("이전");
                        textView.setTextSize(15);
                    }

                    if(beforePage > 0){
                        if (i == beforePage) {
                            textView.setTextColor(Color.BLACK);
                            beforePage = -1;
                        } else {
                            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
                        }
                    } else {
                        if (i == startpage) {
                            textView.setTextColor(Color.BLACK);
                        } else {
                            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
                        }
                    }

                    final int finalLastpage2 = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage2);
                            textView = (TextView) v.findViewById(v.getId());
                            if(textView.getText().toString().equals("다음")){
                                onPageSelectListener.onPageNext(textView.getId());
                            } else if(textView.getText().toString().equals("이전")){
                                onPageSelectListener.onPageBefore(textView.getId());
                                beforePage = startpage-1;
                            } else {
                                onPageSelectListener.onPageCenter(textView.getId());
                            }
                            textView.setTextColor(Color.BLACK);
                        }
                    });
                    this.addView(textView);
                }
                break;
            case ADD_PAGE_TYPE_LAST_BEFORE:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_LAST_BEFORE");
                for (int i = startpage-1; i < lastpage; i++) {
                    textView = new TextView(context);

                    setText(textView,i);

                    if(i == startpage-1){
                        textView.setText("이전");
                        textView.setTextSize(15);
                    } else{
                        textView.setText(String.valueOf(i + 1));
                        textView.setTextSize(20);
                    }

                    if (i == startpage) {
                        Log.i("PAGELIST", "startpage / " + i + " is Brack");
                        textView.setTextColor(Color.BLACK);
                    } else {
                        Log.i("PAGELIST", "else / " + i + " is colorGray");
                        textView.setTextColor(context.getResources().getColor(R.color.colorGray));
                    }

                    final int finalLastpage3 = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage3);
                            textView = (TextView) v.findViewById(v.getId());
                            if(textView.getText().toString().equals("다음")){
                                onPageSelectListener.onPageNext(textView.getId());
                            } else if(textView.getText().toString().equals("이전")){
                                onPageSelectListener.onPageBefore(textView.getId());
                                beforePage = startpage-1;
                            } else {
                                onPageSelectListener.onPageCenter(textView.getId());
                            }
                            textView.setTextColor(Color.BLACK);
                        }
                    });
                    this.addView(textView);
                }
                break;
        }
    }

    private void buttonInit(int startpage, int lastpage) {
        for (int i = startpage; i < lastpage; i++) {
            Log.i("PAGELIST", "buttonInit / " + i + " is Gray");
            textView = this.findViewById(i + 1);
            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
        }
    }

    public void setOnPageSelectListener(OnPageSelectListener onPageSelectListener) {
        this.onPageSelectListener = onPageSelectListener;
    }
}
