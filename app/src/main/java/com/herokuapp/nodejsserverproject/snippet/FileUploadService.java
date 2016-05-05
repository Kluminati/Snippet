package com.herokuapp.nodejsserverproject.snippet;



import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by klu shvartsman on 29/04/2016.
 */
public interface FileUploadService {
    @Multipart
    @POST("api/1.2/upload.php")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}