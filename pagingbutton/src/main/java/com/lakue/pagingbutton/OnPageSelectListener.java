package com.lakue.pagingbutton;

public interface OnPageSelectListener {
    void onPageBefore(int now_page);
    void onPageCenter(int now_page);
    void onPageNext(int now_page);
}
