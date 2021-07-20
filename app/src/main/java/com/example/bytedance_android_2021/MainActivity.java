package com.example.bytedance_android_2021;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager2)
    ViewPager2 viewPager2;

    private List<VideoItem> mList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getData();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //dataCreate.getData();
//                getData();
//            }
//        }).start();
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

    /**
     * 模拟数据
     */
    private void setData() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://beiyou.bytedance.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        apiService.getVideos().enqueue(new Callback<List<VideoItem>>() {
//            @Override
//            public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
//                if (response.body() != null) {
//                    mList = (List<VideoItem>) response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<VideoItem>> call, Throwable t) {
//                Log.d("retrofit", t.getMessage());
//            }
//        });
        VideoItem v1 = new VideoItem();
        v1.setId("5e9830b0ce330a0248e89d86");
        v1.setFeedUrl("http://8.136.101.204/v/%E9%A5%BA%E5%AD%90%E5%A5%BD%E5%A6%88%E5%A6%88.mp4");
        v1.setNickname("王火火");
        v1.setLikeCount(10000);
        v1.setAvatar("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202005%2F06%2F20200506110929_iajqi.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1629274593&t=11f8a7e63296c9e3e8e0978f253c44c3");
        v1.setThumbNails("http://8.136.101.204/v/%E9%A5%BA%E5%AD%90%E5%A5%BD%E5%A6%88%E5%A6%88.jpg");

        mList.add(v1);
        mList.add(v1);
        //TODO
        // 网络请求数据（照下面这么做都有 bug）、根据 videoItem 数据修改界面的相关参数（头像、Description）

        //        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //dataCreate.getData();
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://beiyou.bytedance.com/")
//                        .addConverterFactory(GsonConverterFactory.create()) //添加Gson
//                        .build();
//
//                ApiService apiService = retrofit.create(ApiService.class);
//                Call<List<VideoItem>> call = apiService.getVideos();
//
//                try {
//                    List<VideoItem> temp = call.execute().body();
//                    mList = temp;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

}