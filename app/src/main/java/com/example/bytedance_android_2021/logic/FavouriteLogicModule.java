package com.example.bytedance_android_2021.logic;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.bytedance_android_2021.video.VideoFetcher;
import com.example.bytedance_android_2021.video.VideoItem;

import java.util.List;

public class FavouriteLogicModule {
    //单例
    private FavouriteLogicModule(){}

    private static FavouriteLogicModule instance = new FavouriteLogicModule();
    public static  FavouriteLogicModule getInstance(){
        return instance;
    }

    public boolean setFavourite(VideoItem item,Context context){
        SharedPreferences favourites = context.getSharedPreferences("Favourites",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = favourites.edit();
        if (favourites.getString(item.getId(),null)==null){
        editor.putString(item.getId(),"true");
        editor.apply();
            Toast.makeText(context,"收藏成功！",Toast.LENGTH_SHORT).show();
        }else{
            editor.remove(item.getId());
            Toast.makeText(context,"取消收藏成功！",Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public List<VideoItem> getFavourite(Context context){
        SharedPreferences favourites = context.getSharedPreferences("Favourites",Context.MODE_PRIVATE);

        List<VideoItem> favouritesItems=VideoFetcher.videoItems;
        for(VideoItem item: favouritesItems){
            if (favourites.getString(item.getId(),null)==null){
                favouritesItems.remove(item);
            }
        }
        return favouritesItems;
    }
}
