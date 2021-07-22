package com.example.bytedance_android_2021.logic;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.bytedance_android_2021.video.VideoFetcher;
import com.example.bytedance_android_2021.video.VideoItem;

import java.util.ArrayList;
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
            Toast.makeText(context,"点赞成功！",Toast.LENGTH_SHORT).show();
            item.setLikeCount(item.getLikeCount()+1);
            return true;
        }else{
            editor.remove(item.getId());
            Toast.makeText(context,"取消点赞成功！",Toast.LENGTH_SHORT).show();

            item.setLikeCount(item.getLikeCount()-1);
            editor.apply();
            return false;
        }

    }


    public boolean isFavourite(Context context,VideoItem videoItem){
        SharedPreferences favourites = context.getSharedPreferences("Favourites",Context.MODE_PRIVATE);
        return favourites.getString(videoItem.getId(),null)!=null;

    }
}
