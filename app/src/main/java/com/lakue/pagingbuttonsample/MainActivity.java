package com.lakue.pagingbuttonsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.lakue.pagingbutton.LakuePagingButton;
import com.lakue.pagingbutton.OnPageSelectListener;

public class MainActivity extends AppCompatActivity {

    LakuePagingButton lpb_buttonlist;
    int page = 1;
    int max_page = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lpb_buttonlist = findViewById(R.id.lpb_buttonlist);

        lpb_buttonlist.addBottomPageButton(max_page,1);
        lpb_buttonlist.setPageItemCount(5);

        lpb_buttonlist.setOnPageSelectListener(new OnPageSelectListener() {
            @Override
            public void onPageBefore(int now_page) {
                lpb_buttonlist.addBottomPageButton(max_page,5);
                Toast.makeText(MainActivity.this, ""+now_page, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageCenter(int now_page) {
                page = now_page;
                Toast.makeText(MainActivity.this, ""+now_page, Toast.LENGTH_SHORT).show();
              //  lpb_buttonlist.addBottomPageButton(max_page,page);
            }

            @Override
            public void onPageNext(int now_page) {
                Toast.makeText(MainActivity.this, ""+now_page, Toast.LENGTH_SHORT).show();
                lpb_buttonlist.addBottomPageButton(max_page,6);
            }
        });
    }
}
