package com.ipaulpro.afilechooserexample;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class camera extends Activity implements Callback, OnClickListener {
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private boolean mPreviewRunning;
    private ImageView mImageView;
    public String dir="/storage/sdcard1/test";
    public String picpath="/storage/sdcard1/test.jpg";
    boolean startflag=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();



    }
    private void init()
    {
        setContentView(R.layout.camera);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera);
        mImageView = (ImageView) findViewById(R.id.image);
        mImageView.setVisibility(View.GONE);
        mSurfaceView.setOnClickListener(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }
        Parameters params = mCamera.getParameters();
        params.setPictureFormat(PixelFormat.JPEG);// 设置图片格式
        // params.setPreviewSize(width, height);
        params.set("rotation", 90);
        mCamera.setParameters(params);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        mPreviewRunning = true;
    }

    private AutoFocusCallback mAutoFocusCallBack = new AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            Log.v("AutoFocusCallback", "AutoFocusCallback , boolean success:"+ success);
            Camera.Parameters Parameters = mCamera.getParameters();
            Parameters.setPictureFormat(PixelFormat.JPEG);// 设置图片格式
            mCamera.setParameters(Parameters);
            mCamera.takePicture(mShutterCallback, null, mPictureCallback);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
        mCamera = null;
    }





    /**
     * 拍照的回调接口
     */
    PictureCallback mPictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.v("new", "pic call back…onPictureTaken…");
            if (data != null) {
                Log.i("new", "data != null");
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                        data.length);
                Log.i("hack", picpath);
                File myCaptureFile = new File(picpath);

                try
                {
                    Log.i("new", "start photo");
                    BufferedOutputStream bos=new BufferedOutputStream
                            (new FileOutputStream(myCaptureFile));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                    bos.flush();
                    bos.close();
                    bitmap.recycle();
                    //mCamera.stopPreview();
                    //mPreviewRunning = false;
                    //mCamera.release();
                    Log.i("new", picpath);
                    //resetCamera();
                    //Log.i("new", picpath);

                }
                catch (Exception e)
                {
                    Log.e("new", e.getMessage());
                }


                mCamera.stopPreview();
                //mCamera.release();
                startflag=true;
                init();
                //mCamera.startPreview();
            }
        }
    };

    private void resetCamera()
    {
        if (mCamera != null )
        {
            mCamera.stopPreview();
       /* 扩展学习，释放Camera对象 */
            mCamera.release();
            mCamera = null;

        }
    }
    /**
     * 在相机快门关闭时候的回调接口，通过这个接口来通知用户快门关闭的事件，
     * 普通相机在快门关闭的时候都会发出响声.
     * 根据需要可以在该回调接口中定义各种动作， 例如：使设备震动
     */
    ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            // just log ,do nothing
            Log.v("ShutterCallback", "…onShutter…");
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            if (mCamera != null) {
                // mCamera.takePicture(null, null,mPictureCallback);
                mCamera.autoFocus(mAutoFocusCallBack);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View arg0) {
        Log.v("new", "…onClick…");
        startflag=false;
        /*
        for(int i=0;i<5;i++)
        {
            try{
                Thread.sleep(1500);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.i("hack",dir+Integer.toString(i)+".jpg");
            // mCamera.autoFocus(mAutoFocusCallBack);
            //Camera.Parameters Parameters = mCamera.getParameters();
            //Parameters.setPictureFormat(PixelFormat.JPEG);// 设置图片格式
            //mCamera.setParameters(Parameters);
            mCamera.takePicture(mShutterCallback, null, mPictureCallback);
        }*/
        picpath=dir+0+".jpg";

        mCamera.autoFocus(mAutoFocusCallBack);

        Log.i("new", "finish photo");
        new Thread(){
            public void run()
            {
                for(int i=0;i<5;)
                {
                    picpath=dir+i+".jpg";
                    try{
                        Thread.sleep(2000);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(startflag)
                    {
                        mCamera.autoFocus(mAutoFocusCallBack);
                        startflag=false;
                        i++;
                    }
                }

            }
        }.start();
    }
}