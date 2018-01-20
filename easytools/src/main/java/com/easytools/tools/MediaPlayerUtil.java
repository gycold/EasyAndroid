package com.easytools.tools;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * package: com.easytools.tools.MediaPlayerUtil
 * author: gyc
 * description:播放资源目录下音频文件
 * time: create at 2017/1/31 23:15
 */

public class MediaPlayerUtil {

    static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {
        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    /**
     * 播放音频文件
     *
     * @param context  上下文
     * @param fileName 音频文件名称
     */
    public static void playAudio(Context context, String fileName) {
        try {
            stopAudio();//如果正在播放就停止
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor afd = assetManager.openFd(fileName);
            final MediaPlayer mediaPlayer = getMediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(false);//循环播放
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("播放音频失败", "");
        }
    }

    /**
     * 停止播放音频文件
     */
    public static void stopAudio() {
        try {
            if (null != mediaPlayer) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.reset();
                    mediaPlayer.stop();
                }
            }
        } catch (Exception e) {
            Log.e("stopAudio", e.getMessage());
        }
    }
}
