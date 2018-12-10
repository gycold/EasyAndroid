package com.easytools.tools;

import android.util.Log;

/**
 * package: com.easytools.tools.LongLogUtils
 * author: gyc
 * description:打印长日志
 * 1、只输出等级大于等于LEVEL的日志,所以在开发和产品发布后通过修改LEVEL来选择性输出日志.当LEVEL=NOTHING则屏蔽了所有的日志.
 * 2、v,d,i,w,e均对应两个方法.若不设置TAG或者TAG为空则为设置默认TAG
 * time: create at 2016/10/17 15:56
 */

public class LongLogUtils {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;
    public static final String SEPARATOR = ",";
    public static final String TAG = "LongLogUtils";
    public static final int MAX_LENGTH = 1024 * 3;

    public static void v(String msg) {
        if (LEVEL <= VERBOSE) {
            msg = StringUtils.replaceBlank(msg);
            Log.v(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            msg = StringUtils.replaceBlank(msg);
            Log.v(tag, msg);
        }
    }

    public static void d(String msg) {
        if (LEVEL <= DEBUG) {
            msg = StringUtils.replaceBlank(msg);
            long length = msg.length();
            if (length <= MAX_LENGTH) {
                Log.d(TAG, msg);
            } else {
                for (int i = 0; i < msg.length(); i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < msg.length()) {
                        Log.d(TAG + "---" + i, msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.d(TAG + "---" + i, msg.substring(i, msg.length()));
                    }
                }
            }
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            msg = StringUtils.replaceBlank(msg);
            long length = msg.length();
            if (length <= MAX_LENGTH) {
                Log.d(tag, msg);
            } else {
                for (int i = 0; i < msg.length(); i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < msg.length()) {
                        Log.d(tag + "---" + i, msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.d(tag + "---" + i, msg.substring(i, msg.length()));
                    }
                }
            }
        }
    }

    public static void i(String msg) {
        if (LEVEL <= INFO) {
            msg = StringUtils.replaceBlank(msg);
            long length = msg.length();
            if (length <= MAX_LENGTH) {
                Log.i(TAG, msg);
            } else {
                for (int i = 0; i < msg.length(); i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < msg.length()) {
                        Log.i(TAG + "---" + i, msg.substring(i, i + 4000));
                    } else {
                        Log.i(TAG + "---" + i, msg.substring(i, msg.length()));
                    }
                }
            }
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            msg = StringUtils.replaceBlank(msg);
            long length = msg.length();
            if (length <= MAX_LENGTH) {
                Log.i(tag, msg);
            } else {
                for (int i = 0; i < msg.length(); i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < msg.length()) {
                        Log.i(tag + "---" + i, msg.substring(i, i + 4000));
                    } else {
                        Log.i(tag + "---" + i, msg.substring(i, msg.length()));
                    }
                }
            }
        }
    }

    public static void w(String msg) {
        if (LEVEL <= WARN) {
            msg = StringUtils.replaceBlank(msg);
            Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            msg = StringUtils.replaceBlank(msg);
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            msg = StringUtils.replaceBlank(msg);
            Log.e(tag, msg);
        }
    }

    /**
     * 获取默认的TAG名称. 比如在MainActivity.java中调用了日志输出. 则TAG为MainActivity
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return tag;
    }

    /**
     * 输出日志所包含的信息
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取生日输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("threadID=" + threadID).append(SEPARATOR);
        logInfoStringBuilder.append("threadName=" + threadName).append(
                SEPARATOR);
        logInfoStringBuilder.append("fileName=" + fileName).append(SEPARATOR);
        logInfoStringBuilder.append("className=" + className).append(SEPARATOR);
        logInfoStringBuilder.append("methodName=" + methodName).append(
                SEPARATOR);
        logInfoStringBuilder.append("lineNumber=" + lineNumber);
        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }
}
