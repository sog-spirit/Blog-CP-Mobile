package com.example.blog.apiservice;

import com.example.blog.model.CommentModel;
import com.example.blog.model.HomeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
    AppService appService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.6:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                    new OkHttpClient().newBuilder()
                            .cookieJar(new SessionCookieJar()).build()
            )
            .build()
            .create(AppService.class);

    class SessionCookieJar implements CookieJar {
        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (url.encodedPath().endsWith("login")) {
                this.cookies = new ArrayList<>(cookies);
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (!url.encodedPath().endsWith("login") && cookies != null) {
                return cookies;
            }
            return Collections.emptyList();
        }
    }

    @POST("user/login")
    Call<Void> loginUser(@Body HashMap<String, String> userLoginData);

    @POST("user/register")
    Call<Void> registerUser(@Body HashMap<String, String> userRegistrationData);

    @GET("posts")
    Call<List<HomeModel>> getAllPosts();

    @POST("posts")
    Call<List<HomeModel>> getPostsByCurrentUser();

    @GET("post")
    Call<List<HomeModel>> getPostsByTitleQuery(@Query("query") String titleQuery);

    @POST("post")
    Call<Void> createPost(@Body HashMap<String, String> newPostData);

    @PATCH("post")
    Call<Void> updatePost(@Body HashMap<String, Object> postUpdateData);

    @POST("post/detail")
    Call<HomeModel> getPostDetail(@Body HashMap<String, Integer> postId);

    @GET("comment")
    Call<List<CommentModel>> getThisPostComments(@Query("post_id") int postId);

    @GET("comment/detail")
    Call<CommentModel> getCommentById(@Query("comment_id") int commentId);

    @POST("comment")
    Call<Void> createComment(@Body HashMap<String, Object> newCommentData);

    @PATCH("comment")
    Call<Void> updateComment(@Body HashMap<String, Object> updateCommentData);

    @DELETE("comment")
    Call<Void> deleteComment(@Body HashMap<String, Integer> commentId);
}
