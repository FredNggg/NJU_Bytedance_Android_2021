package com.example.bytedance_android_2021.video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/invoke/video/invoke/video")
    Call<List<VideoItem>> getVideos();
}
