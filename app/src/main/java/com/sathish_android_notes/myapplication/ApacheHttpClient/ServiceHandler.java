package com.sathish_android_notes.myapplication.ApacheHttpClient;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int FILES = 3;

    public ServiceHandler() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null,null);
    }

    public String makeServiceCall(String url, int method, List<NameValuePair> params) {
        return this.makeServiceCall(url, method, params,null);
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method, List<NameValuePair> params, List<NameValuePair> fileparams) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json; charset=utf-8");//mani
              //  httpPost.setHeader("Authorization", "Bearer " + "");//mani

                // adding post params
                if (params != null) {
                    String jsonString = params.get(0).getValue();
                    httpPost.setEntity(new StringEntity(jsonString, "UTF8"));
                    //  httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                try {

                    httpResponse = httpClient.execute(httpPost);

                }catch (IOException ioee){

                }

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                try {

                    httpResponse = httpClient.execute(httpGet);

                }catch (IOException ioe){

                }

            }
            else if (method == FILES) {
                HttpPost httpPost = new HttpPost(url);
                //   MultipartEntity entity = new MultipartEntity();
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                //example for setting a HttpMultipartMode
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                if(fileparams!=null) {

                    for (int i = 0; i < fileparams.size(); i++) {
                        Log.d(" FileName : ", fileparams.get(i).toString());
                        NameValuePair np = fileparams.get(i);
                        String filename = np.getValue();
                        File image = new File(filename);
                        builder.addPart("files[]", new FileBody(image));
                    }
                }
                if(params!=null) {
                    for (int i = 0; i < params.size(); i++) {
                        Log.d(" FileName : ", params.get(i).toString());
                        NameValuePair np = params.get(i);
                        String var = np.getName();
                        String val = np.getValue();
                        builder.addTextBody(var, val,  ContentType.TEXT_PLAIN);
                    }
                }

                httpPost.setEntity(builder.build());

                try {

                    httpResponse = httpClient.execute(httpPost);

                }catch (IOException ie){

                }

            }

            if(httpResponse!=null){
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
            }
            else{
                response=null;
            }
            // httpEntity = httpResponse.getEntity();
            //response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
