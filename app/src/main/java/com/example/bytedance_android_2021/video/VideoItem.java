package com.example.bytedance_android_2021.video;

import com.google.gson.annotations.SerializedName;

public class VideoItem {
    @SerializedName("_id")
    private String id;

    @SerializedName("feedurl")
    private String feedUrl;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("description")
    private String description;

    @SerializedName("likecount")
    private int likeCount;

    @SerializedName("avatar")
    private String avatar;


    @SerializedName("thumbnails")
    private String thumbNails;//缩略图

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getThumbNails() {
        return thumbNails;
    }

    public void setThumbNails(String thumbNails) {
        this.thumbNails = thumbNails;
    }
}
