package com.easytools.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * package: com.easytools.tools.CloseUtil
 * author: gyc
 * description:关闭操作流相关的工具类
 * time: create at 2016/11/23 15:03
 */

public class CloseUtil {

    private CloseUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭IO
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        try{
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
