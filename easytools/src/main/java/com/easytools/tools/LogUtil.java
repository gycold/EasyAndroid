package com.easytools.tools;

import android.text.TextUtils;
import android.util.Log;

/**
 * package: com.easytools.tools.LogUtil
 * author: gyc
 * description:1、只输出等级大于等于LEVEL的日志
 *               ,所以在开发和产品发布后通过修改LEVEL来选择性输出日志.当LEVEL=NOTHING则屏蔽了所有的日志.
 *             2、v,d,i,w,e均对应两个方法.若不设置TAG或者TAG为空则为设置默认TAG
 *             3、实际项目需要将此类拷贝到项目，而不应直接使用此类
 * time: create at 2016/10/17 15:56
 */

public class LogUtil {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;
    public static final String SEPARATOR = ",";

    public static void v(String message) {
        if (LEVEL <= VERBOSE) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement) + "——"
                    + stackTraceElement.getLineNumber();
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public static void v(String tag, String message) {
        if (LEVEL <= VERBOSE) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement) + "——"
                        + stackTraceElement.getLineNumber();
            }
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public static void d(String message) {
        if (LEVEL <= DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement) + "——"
                    + stackTraceElement.getLineNumber();
            if (message.length() > 4000) {
                for (int i = 0; i < message.length(); i += 4000) {
                    if (i + 4000 < message.length()) {
                        Log.d(tag +"---"+ i, getLogInfo(stackTraceElement) + message.substring(i, i + 4000));
                    } else {
                        Log.d(tag + "---" + i, getLogInfo(stackTraceElement) + message.substring(i, message.length()));
                    }
                }
            } else {
                Log.d(tag, getLogInfo(stackTraceElement) + message);
            }
        }
    }

    public static void d(String tag, String message) {
        if (LEVEL <= DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement) + "——"
                        + stackTraceElement.getLineNumber();
            }
            if (message.length() > 4000) {
                for (int i = 0; i < message.length(); i += 4000) {
                    if (i + 4000 < message.length()) {
                        Log.d(tag +"---"+ i, getLogInfo(stackTraceElement) + message.substring(i, i + 4000));
                    } else {
                        Log.d(tag + "---" + i, getLogInfo(stackTraceElement) + message.substring(i, message.length()));
                    }
                }
            } else {
                Log.d(tag, getLogInfo(stackTraceElement) + message);
            }
        }
    }

    public static void i(String message) {
        if (LEVEL <= INFO) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement) + "——"
                    + stackTraceElement.getLineNumber();
            if (message.length() > 4000) {
                for (int i = 0; i < message.length(); i += 4000) {
                    if (i + 4000 < message.length()) {
                        Log.i(tag +"---"+ i, getLogInfo(stackTraceElement) + message.substring(i, i + 4000));
                    } else {
                        Log.i(tag + "---" + i, getLogInfo(stackTraceElement) + message.substring(i, message.length()));
                    }
                }
            } else {
                Log.i(tag, getLogInfo(stackTraceElement) + message);
            }
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement) + "——"
                        + stackTraceElement.getLineNumber();
            }
            if (message.length() > 4000) {
                for (int i = 0; i < message.length(); i += 4000) {
                    if (i + 4000 < message.length()) {
                        Log.i(tag +"---"+ i, getLogInfo(stackTraceElement) + message.substring(i, i + 4000));
                    } else {
                        Log.i(tag + "---" + i, getLogInfo(stackTraceElement) + message.substring(i, message.length()));
                    }
                }
            } else {
                Log.i(tag, getLogInfo(stackTraceElement) + message);
            }
        }
    }

    public static void w(String message) {
        if (LEVEL <= WARN) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement) + "——"
                    + stackTraceElement.getLineNumber();
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement);
            }
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement) + "——"
                        + stackTraceElement.getLineNumber();
            }
            Log.e(tag, getLogInfo(stackTraceElement) + message);
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
