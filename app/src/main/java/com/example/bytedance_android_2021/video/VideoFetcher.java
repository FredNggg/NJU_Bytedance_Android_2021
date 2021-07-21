package com.example.bytedance_android_2021.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/*
* @author heuingmin
* @usage 获取视频列表，以封装的视频数据形式返回给播放器调用。
* */
public class VideoFetcher {
    public static List<VideoItem> videoItems;
    public static Map<String,Bitmap> bitmapCache = new HashMap<String,Bitmap>();
    public static void getVideos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取gson的视频信息数据构建器
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideos().enqueue(new Callback<List<VideoItem>>() {
            @Override
            public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
                if(videoItems == null) videoItems = response.body();
            }

            @Override
            public void onFailure(Call<List<VideoItem>> call, Throwable t) {
                Log.d("retrofit",t.getMessage());
            }
        });
    }

}