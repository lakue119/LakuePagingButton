package com.lakue.pagingbutton;

import android.content.Context;

public class DivisionPageType {

    Context context;

    public DivisionPageType(Context context){

    }

    //보여질 페이지 형식을 구분
    public int pageType(int startpage, int maxpage, int MAX_PAGE){
        //다음 버튼이 없는 처음페이지
        if(maxpage <= MAX_PAGE ){
            return Define.ADD_PAGE_TYPE_FIRST;
        }

        //다음 버튼이 있는 페이지
        if(startpage <= 1){
            return Define.ADD_PAGE_TYPE_FIRST_NEXT;
        }

        //다음버튼과 이전페이지 둘 다 있는 페이지
        if(startpage-1 >= MAX_PAGE && maxpage >= startpage+MAX_PAGE){
            return Define.ADD_PAGE_TYPE_CENTER;
        }

        //이전버튼이 있는 페이지
        if(startpage+MAX_PAGE > maxpage){
            return Define.ADD_PAGE_TYPE_LAST_BEFORE;
        }
        return -1;
    }
}
