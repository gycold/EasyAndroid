package com.easytools.tools;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * package: com.easytools.tools.MD5Utils
 * author: gyc
 * description:获取MD5相关
 * time: create at 2016/10/17 16:01
 */

public class MD5Utils {

    private static final String TAG = MD5Utils.class.getSimpleName();
    private static final int STREAM_BUFFER_LENGTH = 1024;

    private MD5Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     *
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static MessageDigest getDigest(final String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    /**
     * 获取md5
     * @param txt
     * @return
     */
    public static byte[] md5(String txt) {
        return md5(txt.getBytes());
    }

    /**
     * 获取md5
     * @param bytes
     * @return
     */
    public static byte[] md5(byte[] bytes) {
        try {
            MessageDigest digest = getDigest("MD5");
            digest.update(bytes);//使用指定的 byte 数组更新摘要
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] md5(InputStream is) throws NoSuchAlgorithmException, IOException {
        return updateDigest(getDigest("MD5"), is).digest();
    }

    private static MessageDigest updateDigest(final MessageDigest digest, final InputStream data)
            throws IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest;
    }
}
