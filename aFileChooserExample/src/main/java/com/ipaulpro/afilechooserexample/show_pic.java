package com.ipaulpro.afilechooserexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by apple on 16/4/9.
 */
public class show_pic extends Activity {
    WebView wb;
    Handler h = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent i = this.getIntent();
        String path = i.getExtras().getString("path");
        String yanzhi = i.getExtras().getString("yanzhi");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        ImageView image = (ImageView)findViewById(R.id.imageView);
        TextView txt = (TextView)findViewById(R.id.t123);


        Log.i("new", path);
        Bitmap bm = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bm);//不会变形
        txt.setText("颜值:" + yanzhi);

    }
}
