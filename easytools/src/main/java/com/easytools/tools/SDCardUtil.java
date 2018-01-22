package com.easytools.tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * package: com.easytools.tools.SDCardUtil
 * author: gyc
 * description:SD卡（外部存储）相关的工具类
 * time: create at 2017/1/8 21:33
 */

public class SDCardUtil {

    private SDCardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡Data路径
     *
     * @return SD卡Data路径，即："/data/"
     */
    public static String getDataPath() {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        return Environment.getDataDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 在缓存路径下创建指定图片名称的文件
     * tips:通过Context获取的路径都带有包名
     *
     * @param context
     * @param fileName
     */
    public static void savePic(Context context, String fileName){
        if (!isSDCardEnable()) {
            throw new RuntimeException("sdcard disabled!");
        }
        String path = context.getExternalCacheDir().getAbsolutePath();
        String filePath = path + fileName;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过cmd指令获取SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPathByCmd() {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    return " 命令执行失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取外部存储Music路径
     * /storage/emulated/0/Android/data/包名/files/Music/
     * @param context
     * @return /storage/emulated/0/Android/data/包名/files/Music/
     */
    public static String getExternalMusicDir(Context context) {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator;
    }

    /**
     * 获取外部存储Movies路径
     * /storage/emulated/0/Android/data/包名/files/Movies/
     * @param context
     * @return /storage/emulated/0/Android/data/包名/files/Movies/
     */
    public static String getExternalMovieDir(Context context) {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator;
    }

    /**
     * 获取外部存储Download路径
     * /storage/emulated/0/Android/data/包名/files/Download/
     * @param context
     * @return /storage/emulated/0/Android/data/包名/files/Download/
     */
    public static String getExternalDownloadDir(Context context) {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;
    }


    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()){
            throw new RuntimeException("sdcard disabled!");
        }
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        return ConvertUtil.byte2FitSize(availableBlocks * blockSize);
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sd.isExist = true;
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            sd.totalBlocks = sf.getBlockCountLong();
            sd.blockByteSize = sf.getBlockSizeLong();
            sd.availableBlocks = sf.getAvailableBlocksLong();
            sd.availableBytes = sf.getAvailableBytes();
            sd.freeBlocks = sf.getFreeBlocksLong();
            sd.freeBytes = sf.getFreeBytes();
            sd.totalBytes = sf.getTotalBytes();
        }
        return sd;
    }

    private static class SDCardInfo {
        boolean isExist;
        long    totalBlocks;
        long    freeBlocks;
        long    availableBlocks;

        long blockByteSize;

        long totalBytes;
        long freeBytes;
        long availableBytes;

        @Override
        public String toString() {
            return "SDCardInfo{" +
                    "isExist=" + isExist +
                    ", totalBlocks=" + totalBlocks +
                    ", freeBlocks=" + freeBlocks +
                    ", availableBlocks=" + availableBlocks +
                    ", blockByteSize=" + blockByteSize +
                    ", totalBytes=" + totalBytes +
                    ", freeBytes=" + freeBytes +
                    ", availableBytes=" + availableBytes +
                    '}';
        }
    }
}
