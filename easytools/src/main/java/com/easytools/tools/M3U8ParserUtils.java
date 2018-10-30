package com.easytools.tools;

import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * package: com.easytools.tools.M3U8ParserUtils
 * author: gyc
 * description:m3u8文件解析类
 * time: create at 2017/1/31 22:52
 */

public class M3U8ParserUtils {

    public M3U8ParserUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 解析m3u8的ts下载地址
     *
     * @param fis 文件的输入流
     * @return 解析后的下载地址
     */
    public static List<String> parseString(FileInputStream fis) {
        List<String> resultList = null;
        try {
            if (fis != null) {
                resultList = new ArrayList<String>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("#")) {
                        //解析出播放视频的iv的key
                        if (line.startsWith("#EXT-X-KEY")) {
                            resultList.add(line);
                        }
                    } else if (line.length() > 0 && line.startsWith("http://")) {
                        resultList.add(line);
                    }
                }
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception","解析m3u8错误"+e.getMessage());
        }
        return resultList;
    }

    /**
     * 获取m3u8文件中的uri对应的key
     *
     * @param fis 文件的输入流
     * @return uri对应的key
     */
    public static String getM3u8Key(FileInputStream fis) {
        try {
            List<String> strings = M3U8ParserUtils.parseString(fis);
            String[] split = strings.get(0).split(",");
            for (String s : split) {
                if (s.contains("URI")) {
                    Log.e("解析出的内容getM3u8Key", s);
                    return s.split("=")[1];
                }
            }
        } catch (Exception e) {
            Log.e("解析内容错误",e.getMessage());
        }
        return "";
    }

}
