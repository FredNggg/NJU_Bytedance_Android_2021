package com.example.bytedance_android_2021;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bytedance_android_2021.video.RecyclerItemNormalHolder;
import com.example.bytedance_android_2021.video.VideoFetcher;
import com.example.bytedance_android_2021.video.VideoItem;
import com.example.bytedance_android_2021.video.ViewPagerAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager2)
    ViewPager2 viewPager2;

    private List<VideoItem> mList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private LoadThread loadThread = new LoadThread(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        VideoFetcher.getVideos();
        ButterKnife.bind(this);
        loadThread.start();
    }
    Handler videoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    getData();
            }
        }
    };

    class LoadThread extends Thread{
        private Context current;
        public LoadThread(Context context){
            super();
            current = context;
        }

        @Override
        public void run(){
            super.run();
            while(VideoFetcher.videoItems == null){
                try {
                    sleep(10);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
            Message message = new Message();
            message.what = 1;
            videoHandler.sendMessage(message);

        }
    }

    private void getData() {

        setData();
        viewPagerAdapter = new ViewPagerAdapter(this, mList);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(viewPagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 大于0说明有播放
                int playPosition = GSYVideoManager.instance().getPlayPosition();
                if (playPosition >= 0) {
                    // 对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                            && (position != playPosition)) {
                        playPosition(position);
                    }
                }
            }
        });
        viewPager2.post(new Runnable() {
            @Override
            public void run() {
                playPosition(0);
            }
        });

    }

    private void playPosition(int position) {
        RecyclerView.ViewHolder viewHolder = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            RecyclerItemNormalHolder recyclerItemNormalHolder = (RecyclerItemNormalHolder) viewHolder;
            recyclerItemNormalHolder.getPlayer().startPlayLogic();
        }
    }

    private void setData() {
        VideoFetcher.getVideos();
        mList= VideoFetcher.videoItems;
    }

}