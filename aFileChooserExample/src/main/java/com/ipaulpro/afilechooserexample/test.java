package com.ipaulpro.afilechooserexample;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
public class test {
        public static final String api_id = "610b4fb11619467cbd2cf43a50a6268f";
        public static final String api_secret = "f5141fccbac246b6a7f009a8ff456f13";
        public static final String POST_URL = "https://v1-api.visioncloudapi.com/face/detection";
        public static String line = "";
        public static String HttpClientPost(final String filepath) throws ClientProtocolException, IOException {
            new Thread(){
                public void run () {
                    try{
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost post = new HttpPost(POST_URL);
                        FileBody fileBody = new FileBody(new File(filepath));
                        StringBody id = new StringBody(api_id);
                        StringBody secret = new StringBody(api_secret);
                        StringBody att = new StringBody("true");

                        MultipartEntity entity = new MultipartEntity(); entity.addPart("file", fileBody);
                        entity.addPart("api_id", id);
                        entity.addPart("api_secret", secret);
                        entity.addPart("attributes", att);

                        post.setEntity(entity);

                        System.out.print("post");
                        HttpResponse response = httpclient.execute(post);
                        System.out.println("after");
                        if (response.getStatusLine().getStatusCode() == 200)
                        {
                            HttpEntity entitys = response.getEntity(); BufferedReader reader = new BufferedReader(
                                new InputStreamReader(entitys.getContent()));
                            line = reader.readLine();
                            System.out.println(line);
                            JSONObject jo = new JSONObject(line);
                            jo = jo.getJSONArray("faces").getJSONObject(0);
                            line = jo.getJSONObject("attributes").getString("attractive");
                            return;
                        }
                        else
                        {
                            HttpEntity r_entity = response.getEntity();
                            String responseString = EntityUtils.toString(r_entity);
                            System.out.println("错误码是: "+response.getStatusLine().getStatusCode()+response.getStatusLine().getReasonPhrase());
                            System.out.println("出错原因是:"+responseString);
                        }
                        httpclient.getConnectionManager().shutdown();
                    }catch(Exception e){e.printStackTrace();}
                }
            }.start();

            try {
                while (line.equals("")) {
                    Thread.sleep(100);
                }
            }catch (Exception e){}
            return line;
    }
    public static void main(String[] args)
    {
        try{
            System.out.print("begin");
            HttpClientPost( "");
        }catch(Exception e){}

    }
}