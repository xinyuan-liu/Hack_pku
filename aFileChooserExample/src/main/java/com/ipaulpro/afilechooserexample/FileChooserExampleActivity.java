/*
 * Copyright (C) 2012 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ipaulpro.afilechooserexample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.util.ArrayList;

/**
 * @author paulburke (ipaulpro)
 */
public class FileChooserExampleActivity extends Activity {

    private static final String TAG = "FileChooserExampleActivity";
    public static String res;
    public static String path;
    private static final int REQUEST_CODE = 6384; // onActivityResult request
                                                  // code

    public boolean mode=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);

    }

    public void onSelectButtonClicked(View view) {
        mode=false;
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    public void onPhotoButtonClicked(View view)
    {
        mode=true;
        Intent intent = new Intent(this, camera.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();

                        try {
                            // Get the file path from the URI
                            path = FileUtils.getPath(this, uri);
                            Toast.makeText(FileChooserExampleActivity.this,
                                    "File Selected!: " + path, Toast.LENGTH_LONG).show();
                            res = test.HttpClientPost(path);
                            Log.i("tag", res);
                        } catch (Exception e) {
                            Log.e("FileSelectorActivity", "File select error", e);
                        }
                        Intent intent = new Intent(this, show_pic.class);
                        c pic = new c(path, Integer.parseInt(res));
                        ArrayList<c> p = new  ArrayList<c>();
                        p.add(pic);
                        p.add(pic);
                        p.add(pic);
                        intent.putParcelableArrayListExtra("pic", p);
                        startActivity(intent);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
