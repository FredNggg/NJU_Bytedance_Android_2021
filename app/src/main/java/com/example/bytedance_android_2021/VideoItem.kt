package com.example.bytedance_android_2021

class VideoItem : ArrayList<VideoItemItem>()

data class VideoItemItem(
    val _id: String,
    val avatar: String,
    val description: String,
    val feedurl: String,
    val likecount: Int,
    val nickname: String
)