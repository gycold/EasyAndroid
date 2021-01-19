package com.easytools.tools;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Stack;

/**
 * package: com.easytools.tools.ActivityUtil
 * author: gyc
 * description: 管理Activity生命周期的类，使用：
 * 在自己的Application注册：registerActivityLifecycleCallbacks(ActivityManager.getActivityLifecycleCallbacks());
 * 可以指定报名.类打开或者关闭Activity
 * time: create at 2016/10/14 13:25
 */

public class ActivityManager {

    private static Stack<Activity> activityStack = new Stack<>();

    private static final JActivityLifecycleCallbacks instance = new JActivityLifecycleCallbacks();

    public ActivityManager() {
    }

    public static Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return instance;
    }

    /**
     * 获得当前栈顶Activity
     *
     * @return
     */
    public static Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.isEmpty())
            activity = activityStack.peek();
        return activity;
    }

    /**
     * 主动退出Activity
     *
     * @param activity
     */
    public static void closeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public static void closeAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (null == activity) {
                break;
            }
            closeActivity(activity);
        }
    }

    /**
     * close a specific activity by its complete name.
     *
     * @param name For example: com.jude.ui.Activity_B
     */
    public static void closeActivityByName(String name) throws ClassNotFoundException{
        if (activityStack.isEmpty()){
            throw new ClassNotFoundException("Activity栈为空");
        }

        int index = activityStack.size() - 1;

        while (true) {
            Activity activity = activityStack.get(index);

            String activityName = activity.getComponentName().getClassName();
            if (!TextUtils.equals(name, activityName)) {
                index--;
                if (index < 0) {//avoid index out of bound.
                    throw new ClassNotFoundException(name + "不存在");
                }
                continue;
            }
            closeActivity(activity);
            break;
        }
    }

    /**
     * close all activities but except the specified activity by name
     *
     * @param name For example: com.jude.ui.Activity_B
     */
    public static void closeOtherActivities(String name) throws ClassNotFoundException{
        if (activityStack.isEmpty()){
            throw new ClassNotFoundException("Activity栈为空");
        }

        int index = activityStack.size() - 1;
        while (true) {
            Activity activity = activityStack.get(index);
            String activityName = activity.getComponentName().getClassName();

            index--;
            if (TextUtils.equals(name, activityName)) {
                if (index < 0) {
                    break;
                }
                continue;
            }
            closeActivity(activity);
            if (index < 0) {
                break;
            }
        }
    }

    /**
     * 获得当前ACTIVITY 名字
     */
    public static String getCurrentActivityName() {
        Activity activity = currentActivity();
        String name = "";
        if (activity != null) {
            name = activity.getComponentName().getClassName();
        }
        return name;
    }

    public static Stack<Activity> getActivityStack() {
        Stack<Activity> stack = new Stack<>();
        stack.addAll(activityStack);
        return stack;
    }

    private static class JActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            activityStack.remove(activity);
            activityStack.push(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityStack.remove(activity);
        }
    }

}
