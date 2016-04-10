package com.ipaulpro.afilechooserexample;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by apple on 16/4/9.
 */
public class show_pic extends TabActivity {
    WebView wb;
    Handler h = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent i = this.getIntent();
        final ArrayList<c> pic = i.getExtras().getParcelableArrayList("pic");
        Collections.sort(pic, new c.Sort());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        final ImageView image = (ImageView)findViewById(R.id.imageView);
        final TextView txt = (TextView)findViewById(R.id.t123);

        TabHost tabHost = this.getTabHost();

        tabHost.addTab(tabHost
                .newTabSpec("第一张")
                .setIndicator("", getResources().getDrawable(R.drawable.ic_file))
                .setContent(R.id.tab1));

        tabHost.addTab(tabHost
                .newTabSpec("第二张")
                .setIndicator("",
                        getResources().getDrawable(R.drawable.ic_file))
                .setContent(R.id.tab2));

        tabHost.addTab(tabHost.newTabSpec("第三张")
                .setIndicator("", getResources().getDrawable(R.drawable.ic_file))
                .setContent(R.id.tab3));


        tabHost.setCurrentTab(0);
        Bitmap bm = BitmapFactory.decodeFile(pic.get(0).path);
        image.setImageBitmap(bm);//不会变形
        txt.setText("颜值:" + pic.get(0).score);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                int loc = 0;
                if(tabId.equals("tab1"))
                    loc = 0;
                if(tabId.equals("tab2"))
                    loc = 1;
                if(tabId.equals("tab3"))
                    loc = 2;
                Toast.makeText(show_pic.this, tabId, Toast.LENGTH_SHORT)
                        .show();
                Bitmap bm = BitmapFactory.decodeFile(pic.get(loc).path);
                image.setImageBitmap(bm);//不会变形
                txt.setText("颜值:" + pic.get(loc).score);
            }
        });
    }
}
