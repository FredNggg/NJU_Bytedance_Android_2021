package com.example.bytedance_android_2021.video;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bytedance_android_2021.R;
import com.example.bytedance_android_2021.logic.FavouriteLogicModule;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerItemNormalHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerItemNormalHolder";

    protected Context context = null;

    @BindView(R.id.video_item_player)
    SampleCoverVideo gsyVideoPlayer;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.user_photo_view)
    CircleImageView avatar;
    @BindView(R.id.like_count)
    TextView likeCount;
    @BindView(R.id.nickname)
    TextView nickname;
    ImageView imageView;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public RecyclerItemNormalHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void onBind(final int position, VideoItem videoModel) {

        String videoUrl = videoModel.getFeedUrl();
        String textContent = videoModel.getDescription();
        String coverUrl = videoModel.getThumbNails();
        String avatarUrl = videoModel.getAvatar();
        String nickName = videoModel.getNickname();
        int likeCountNum = videoModel.getLikeCount();
        Glide.with(context).load(coverUrl).into(imageView);
        Glide.with(context).load(avatarUrl).into(avatar);
        tvContent.setText(" " + textContent);
        likeCount.setText(numberFilter(likeCountNum));
        nickname.setText("@" + nickName);
        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");
        // 防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(videoUrl)
                .setVideoTitle(textContent)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(gsyVideoPlayer);


        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        GestureDetector gestureDetector;

        gestureDetector = new GestureDetector(gsyVideoPlayer.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                resolveFullBtn(gsyVideoPlayer);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //收藏逻辑
                FavouriteLogicModule instance = FavouriteLogicModule.getInstance();
                instance.setFavourite(videoModel, getPlayer().getContext());
                return true;
            }

        });

        gsyVideoPlayer.getFullscreenButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    /**
     * 实现点赞数的转化
     *
     * @param number
     * @return
     */
    public String numberFilter(int number) {
        if (number > 9999 && number <= 999999) {  //数字上万，小于百万，保留一位小数点
            DecimalFormat df2 = new DecimalFormat("#0.0");
            String format = df2.format((float) number / 10000);
            return format + "w";
        } else if (number > 999999 && number < 99999999) {  //百万到千万不保留小数点
            return number / 10000 + "w";
        } else if (number > 99999999) { //上亿
            DecimalFormat df2 = new DecimalFormat("00.0");
            String format = df2.format((float) number / 100000000);
            return format + "e+";
        } else {
            return number + "";
        }
    }

    public SampleCoverVideo getPlayer() {
        return gsyVideoPlayer;
    }

}