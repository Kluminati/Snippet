package com.herokuapp.nodejsserverproject.snippet.utils;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.herokuapp.nodejsserverproject.snippet.FileUploadService;
import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.async_tasks.SnippetObjectPush;
import com.herokuapp.nodejsserverproject.snippet.async_tasks.UpdateFeedTask;
import com.herokuapp.nodejsserverproject.snippet.web_handlers.MultipartUtility;
import com.herokuapp.nodejsserverproject.snippet.web_handlers.ServiceGenerator;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WebUtills {

    public static MainActivity mainActivity;
    public static String desc;
    public static String username;


    public static String postRequestToDb(String queryParameters, String method)
    {
        String url = "https://nodejsserverproject.herokuapp.com/"+method;
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            //add reuqest header
            Log.v("query,query params:", method + ',' + queryParameters);
                    con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(queryParameters);
            wr.flush();
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("postRequestToDb", "Filed the post request");
            return e.getMessage();
        }

    }

    public static boolean paramCheck(String... stringArray) {
        for (String str : stringArray)
            if(str == null || str.equals(""))
                return false;
        return stringArray.length >= 3;
    }

    public static String postRequestFileSend(String url,File file,String user)
    {

        try {//create dummy test file:

            FileWriter writer = new FileWriter(file);
            writer.append("Hello ,Server");
            writer.flush();writer.close();
            MultipartUtility multipart = new MultipartUtility(url, "UTF-8");
            multipart.addHeaderField("User-Agent", "Java");
            multipart.addHeaderField("Accept-Language", "en-US,en;q=0.5");
            multipart.addFormField("user", user);
            multipart.addFilePart("file", file);
            return multipart.execute();
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    public static File createFileOutOfInputStream(InputStream inputStream)
    {
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        File tempFile = null;
        try{
            tempFile = File.createTempFile(uid, ".png");
            tempFile.deleteOnExit();
            FileUtils.copyInputStreamToFile(inputStream, tempFile);


        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return tempFile;

    }

    public static void uploadFile(File file, final String descrep, final String userName, final FragmentManager fragManager, final MainActivity main) {
        // create upload service client
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                try {
                    try {
                        JSONObject result = new JSONObject(response.body().string());
                        String url = result.get("url").toString();
                        if(url!=null && !url.equals(""))
                        {

                            SnippetObjectPush push = new SnippetObjectPush(mainActivity, descrep, userName, url);
                            push.execute();


                            UpdateFeedTask feedTask = new UpdateFeedTask(main,fragManager ,userName);
                            feedTask.execute();
                        }

                    } catch (JSONException ignored) {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mainActivity, "Crashed While Getting the file url from server", Toast.LENGTH_SHORT).show();
                Log.e("Upload error:", t.getMessage());

            }
        });

    }

}
