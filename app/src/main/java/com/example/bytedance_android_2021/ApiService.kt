package com.example.bytedance_android_2021

import retrofit2.Call

import retrofit2.http.GET

interface ApiService {
    @GET("api/invoke/video/invoke/video")
    fun getVideos():Call<List<VideoItem>>
}