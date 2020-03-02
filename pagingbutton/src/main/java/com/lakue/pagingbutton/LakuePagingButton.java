package com.lakue.pagingbutton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

    OnPageSelectListener onPageSelectListener;

    int MAX_PAGE = 5;

    String prev = "prev";
    String next = "next";
    TextView textView;

    DivisionPageType divisionPageType;
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



    //한번에 보여질 페이지의 수
    public void setPageItemCount(int count){
        MAX_PAGE = count;
    }

    //텍스트 생성
    private void setText(TextView textView,int i){
        textView.setId(i + 1);
        textView.setTag(i + 1);
        textView.setText(String.valueOf(i + 1));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(20,20,20,20);
        textView.setBackgroundColor(Color.TRANSPARENT);
    }


    //페이지 버튼 생성
    public void addBottomPageButton(int listsize, int nowpage) {
        this.removeAllViews();
        if(divisionPageType == null){
            divisionPageType = new DivisionPageType(context);
        }
        int nowpage_paging = (int)Math.ceil(nowpage/MAX_PAGE);
        if(nowpage % MAX_PAGE == 0){
            nowpage_paging = nowpage_paging -1;
        }

        final int startpage = nowpage_paging * MAX_PAGE;
        int lastpage = (nowpage_paging+1) * MAX_PAGE;

        if(lastpage > listsize){
            lastpage = listsize;
        }

        //타입으로 구분
        switch (divisionPageType.pageType(startpage+1,listsize,MAX_PAGE)){
            case Define.ADD_PAGE_TYPE_FIRST:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_FIRST");
                for (int i = startpage; i < lastpage; i++) {
                    textView = new TextView(context);
                    setText(textView,i);

                    createButton(textView,i,startpage,lastpage,Define.ADD_PAGE_TYPE_FIRST);

                    final int finalLastpage = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage);
                            clickButton(v,startpage);
                        }
                    });

                    this.addView(textView);
                }
                break;
            case Define.ADD_PAGE_TYPE_FIRST_NEXT:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_FIRST_NEXT");
                for (int i = startpage; i < lastpage+1; i++) {
                    textView = new TextView(context);
                    setText(textView,i);

                    createButton(textView,i,startpage,lastpage,Define.ADD_PAGE_TYPE_FIRST_NEXT);

                    final int finalLastpage1 = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage1);
                            clickButton(v,startpage);

                        }
                    });
                    this.addView(textView);
                }
                break;
            case Define.ADD_PAGE_TYPE_CENTER:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_CENTER");
                for (int i = startpage-1; i < lastpage+1; i++) {
                    textView = new TextView(context);

                    setText(textView,i);
                    createButton(textView,i,startpage,lastpage,Define.ADD_PAGE_TYPE_CENTER);

                    final int finalLastpage2 = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage2);
                            clickButton(v,startpage);
                        }
                    });
                    this.addView(textView);
                }
                break;
            case Define.ADD_PAGE_TYPE_LAST_BEFORE:
                Log.i("PAGELIST", "ADD_PAGE_TYPE_LAST_BEFORE");
                for (int i = startpage-1; i < lastpage; i++) {
                    textView = new TextView(context);

                    setText(textView,i);

                    createButton(textView,i,startpage,lastpage,Define.ADD_PAGE_TYPE_LAST_BEFORE);

                    final int finalLastpage3 = lastpage;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonInit(startpage, finalLastpage3);
                            clickButton(v,startpage);
                        }
                    });
                    this.addView(textView);
                }
                break;
        }
    }

    private void clickButton(View v, int startpage){
        textView = (TextView) v.findViewById(v.getId());
        if(textView.getText().toString().equals(next)){
            onPageSelectListener.onPageNext(textView.getId());
        } else if(textView.getText().toString().equals(prev)){
            beforePage = startpage-1;
            onPageSelectListener.onPageBefore(textView.getId());
        } else {
            selectButton(textView,true);
            onPageSelectListener.onPageCenter(textView.getId());
        }
    }

    private void createButton(TextView textView, int idx, int startpage,int lastpage, int type){
        switch (type){
            case Define.ADD_PAGE_TYPE_FIRST:
                if (idx == 0) {
                    selectButton(textView,true);
                } else {
                    selectButton(textView,false);
                }
                break;
            case Define.ADD_PAGE_TYPE_FIRST_NEXT:
                if(idx == lastpage){
                    textView.setText(next);
                    textView.setTextSize(15);
                }

                if(beforePage > 0){
                    if (idx == beforePage) {
                        selectButton(textView,true);
                        beforePage = -1;
                    } else {
                        selectButton(textView,false);
                    }
                } else {
                    if (idx == startpage) {
                        selectButton(textView,true);
                    } else {
                        selectButton(textView,false);
                    }
                }
                break;
            case Define.ADD_PAGE_TYPE_CENTER:
                if(idx == lastpage){
                    textView.setText(next);
                    textView.setTextSize(15);
                } else if(idx == startpage-1){
                    textView.setText(prev);
                    textView.setTextSize(15);
                }

                if(beforePage > 0){
                    if (idx == beforePage) {
                        selectButton(textView,true);
                        beforePage = -1;
                    } else {
                        selectButton(textView,false);
                    }
                } else {
                    if (idx == startpage) {
                        selectButton(textView,true);
                    } else {
                        selectButton(textView,false);
                    }
                }
                break;
            case Define.ADD_PAGE_TYPE_LAST_BEFORE:
                if(idx == startpage-1){
                    textView.setText(prev);
                    textView.setTextSize(15);
                } else{
                    textView.setText(String.valueOf(idx + 1));
                    textView.setTextSize(20);
                }

                if (idx == startpage) {
                    selectButton(textView,true);
                } else {
                    selectButton(textView,false);
                }
                break;
        }
    }

    private void selectButton(TextView textView, Boolean isSelect){
        if(isSelect){
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(null, Typeface.BOLD);
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
            textView.setTypeface(null, Typeface.NORMAL);
        }

    }

    private void buttonInit(int startpage, int lastpage) {
        for (int i = startpage; i < lastpage; i++) {
            textView = this.findViewById(i + 1);
            textView.setTextColor(context.getResources().getColor(R.color.colorGray));
        }
    }

    public void setOnPageSelectListener(OnPageSelectListener onPageSelectListener) {
        this.onPageSelectListener = onPageSelectListener;
    }
}
