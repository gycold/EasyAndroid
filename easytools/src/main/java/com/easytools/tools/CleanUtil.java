package com.easytools.tools;

import android.content.Context;

import java.io.File;
import java.math.BigDecimal;

/**
 * package: com.easytools.tools.CleanUtil
 * author: gyc
 * description: 清除缓存/文件相关工具类
 * time: create at 2017/1/8 21:21
 */

public class CleanUtil {

    private CleanUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 清除内部缓存
     * <p>/data/data/com.xxx.xxx/cache</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalCache(Context context) {
        return FileUtil.deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean clearInternalFiles(Context context) {
        return FileUtil.deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbs(Context context) {
        return FileUtil.deleteFilesByDirectory(new File(context.getFilesDir().getPath() +
                context.getPackageName() + File.separator + "databases"));
    }

    /**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param context 上下文
     * @param dbName  数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbByName(Context context, String dbName) {
        return context.deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalSP(Context context) {
        return FileUtil.deleteFilesByDirectory(new File(context.getFilesDir().getPath() +
                context.getPackageName() + File.separator + "shared_prefs"));
    }

    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/com.xxx.xxx/cache</p>
     *
     * @param context 上下文
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanExternalCache(Context context) {
        return SDCardUtil.isSDCardEnable() && FileUtil.deleteFilesByDirectory(context.getExternalCacheDir());
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(String filePath) {
        return FileUtil.deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除自定义目录下的所有文件（包括文件夹）
     *
     * @param dirFile 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(File dirFile) {
        return FileUtil.deleteFilesByDirectory(dirFile);
    }

    /**
     * 清除本应用的所有的数据
     *
     * @param context  上下文
     * @param filePath 自定义路径
     */
    public static void cleanApplicationData(Context context, String... filePath) {
        cleanInternalCache(context);
        cleanInternalDbs(context);
        cleanInternalSP(context);
        clearInternalFiles(context);
        cleanExternalCache(context);
        for (String fp : filePath) {
            cleanCustomCache(fp);
        }
    }

    /**
     * 取得缓存大小
     *
     * @param file 缓存文件夹，例如Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/、
     *             Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/等
     * @return 缓存大小
     * @throws Exception
     */
    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }


    /**
     * 获取文件字节大小
     *
     * @param file 文件路径，例如缓存路径：
     *             Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     *             Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * @return 文件字节大小（byte）
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化值大小
     *
     * @param size 传入的大小
     * @return 格式化单位返回格式化之后的值
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
