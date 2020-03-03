# LakuePagingButton
LakuePagingButton make it easy to page through when loading a lot of data.
* When you select a button, you simply get the page you selected and call it.
* You can customize it for ease of use.

# Sample

<div>
<img width="40%" src="https://user-images.githubusercontent.com/31702431/75774172-b7e3f600-5d92-11ea-99a0-6787dbecf0bc.gif">
<img width="40%" src="https://user-images.githubusercontent.com/31702431/75774183-badee680-5d92-11ea-83c0-3488209486ba.gif">
</div>

# Install
Add Jitpack to your repositories in your build.gradle file
<pre>
<code>
  allprojects {
      repositories {
        // ...
        maven { url 'https://jitpack.io' }
      }
  }
</code>
</pre>

Add the below to your dependencies, again in your gradle.build file

<pre>
<code>

dependencies {
    //Add LakuePagingButton Library
    implementation 'com.github.lakue119:LakuePagingButton:1.0.0'
}
</code>
</pre>

# Code
add following code in xml

```
      <com.lakue.pagingbutton.LakuePagingButton
            android:id="@+id/lpb_buttonlist"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
```

How to write in Java code

<pre>
<code>
        //Number of buttons displayed at one time (Default : 5)
        lpb_buttonlist.setPageItemCount(4);
       
        //Set the total number of page buttons and the current page
        lpb_buttonlist.addBottomPageButton(max_page,1);

        //Event when we clicked page Listener
        lpb_buttonlist.setOnPageSelectListener(new OnPageSelectListener() {
            //PrevButton Click
            @Override
            public void onPageBefore(int now_page) {
                //When you click the prev button, it resets and draws the button.
                lpb_buttonlist.addBottomPageButton(max_page,now_page);
                Toast.makeText(MainActivity.this, ""+now_page, Toast.LENGTH_SHORT).show();
                //Write source code for there page
                //...
            }

            @Override
            public void onPageCenter(int now_page) {
                Toast.makeText(MainActivity.this, ""+now_page, Toast.LENGTH_SHORT).show();
                //Write source code for there page
                //...
            }

            //NextButton Click
            @Override
            public void onPageNext(int now_page) {
                //When you click the next button, it resets and draws the button.
                lpb_buttonlist.addBottomPageButton(max_page,now_page);
                Toast.makeText(MainActivity.this, ""+now_page, Toast.LENGTH_SHORT).show();
                //Write source code for there page
                //...
            }
        });
</code>
</pre>

# Note

https://lakue.tistory.com/34
