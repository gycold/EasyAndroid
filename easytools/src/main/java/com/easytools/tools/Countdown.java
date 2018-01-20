package com.easytools.tools;

import android.os.Handler;
import android.widget.TextView;

/**
 * package: com.easytools.tools.Countdown
 * author: gyc
 * description:倒计时。备注：也可以使用Android为我们封装好的CountDownTimer
 * time: create at 2017/1/8 20:29
 */

public class Countdown implements Runnable{

    private int remainingSeconds;//倒计时秒数
    private int currentRemainingSeconds;//剩余倒计时秒数
    private boolean running;//是否正在倒计时
    private String defaultText;//默认显示文本内容
    private String countdownText;//倒计时显示的文本内容
    private TextView showTextView;
    private Handler handler;
    private CountdownListener countdownListener;
    private TextViewGetListener textViewGetListener;

    /**
     * 创建一个倒计时器
     *
     * @param showTextView 显示倒计时的文本视图
     * @param countdownText 倒计时中显示的内容，例如："%s秒后重新获取验证码"，
     *                      在倒计时的过程中会用剩余描述替换%s
     * @param remainingSeconds 倒计时秒数，例如：60，就是从60开始倒计时一直到0结束
     */
    public Countdown(TextView showTextView, String countdownText, int remainingSeconds) {
        this.showTextView = showTextView;
        this.countdownText = countdownText;
        this.remainingSeconds = remainingSeconds;
        this.handler = new Handler();
    }

    /**
     * 创建一个倒计时器
     * @param textViewGetListener 显示倒计时的文本视图获取监听器
     * @param countdownText 倒计时中显示的内容，例如："%s秒后重新获取验证码"，在倒计时的过程中会用剩余描述替换%s
     * @param remainingSeconds 倒计时秒数，例如：60，就是从60开始倒计时一直到0结束
     */
    public Countdown(TextViewGetListener textViewGetListener, String countdownText, int remainingSeconds){
        this.textViewGetListener = textViewGetListener;
        this.countdownText = countdownText;
        this.remainingSeconds = remainingSeconds;
        this.handler = new Handler();
    }

    /**
     * 创建一个倒计时器，默认60秒
     * @param showTextView 显示倒计时的文本视图
     * @param countdownText 倒计时中显示的内容，例如："%s秒后重新获取验证码"，在倒计时的过程中会用剩余描述替换%s
     */
    public Countdown(TextView showTextView, String countdownText){
        this(showTextView, countdownText, 60);
    }

    /**
     * 创建一个倒计时器，默认60秒
     * @param textViewGetListener 显示倒计时的文本视图获取监听器
     * @param countdownText 倒计时中显示的内容，例如："%s秒后重新获取验证码"，在倒计时的过程中会用剩余描述替换%s
     */
    public Countdown(TextViewGetListener textViewGetListener, String countdownText){
        this(textViewGetListener, countdownText, 60);
    }

    private TextView getShowTextView(){
        if(showTextView != null){
            return showTextView;
        }
        if(textViewGetListener != null){
            return textViewGetListener.OnGetShowTextView();
        }
        return null;
    }

    @Override
    public void run() {
        if (currentRemainingSeconds > 0) {
            getShowTextView().setEnabled(false);
            getShowTextView().setText(String.format(countdownText, currentRemainingSeconds));
            if (countdownListener != null) {
                countdownListener.onUpdate(currentRemainingSeconds);
            }
            currentRemainingSeconds--;
            handler.postDelayed(this, 1000);
        } else {
            stop();
        }
    }

    public void start() {
        defaultText = (String) getShowTextView().getText();
        currentRemainingSeconds = remainingSeconds;
        handler.removeCallbacks(this);
        handler.post(this);
        if (countdownListener != null) {
            countdownListener.onStart();
        }
        running = true;
    }

    private void stop() {
        getShowTextView().setEnabled(true);
        getShowTextView().setText(defaultText);
        handler.removeCallbacks(this);
        if(countdownListener != null){
            countdownListener.onFinish();
        }
        running = false;
    }

    /**
     * 当前倒计时线程是否正在运行
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * 取得倒计时开始时的秒数
     * @return
     */
    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * 取得倒计时中显示的内容
     * @return
     */
    public String getCountdownText() {
        return countdownText;
    }

    /**
     * 设置倒计时中显示的内容
     * @param countdownText
     */
    public void setCountdownText(String countdownText) {
        this.countdownText = countdownText;
    }

    /**
     * 设置当前还剩余的倒计时秒数
     * @param currentRemainingSeconds
     */
    public void setCurrentRemainingSeconds(int currentRemainingSeconds) {
        this.currentRemainingSeconds = currentRemainingSeconds;
    }

    /**
     * 设置倒计时的监听器
     * @param countdownListener
     */
    public void setCountdownListener(CountdownListener countdownListener) {
        this.countdownListener = countdownListener;
    }

    /**
     * 倒计时监听器
     */
    public interface CountdownListener{
        /**
         * 倒计时开始回调
         */
        public void onStart();

        /**
         * 倒计时结束回调
         */
        public void onFinish();

        /**
         * 更新回调
         * @param currentRemainingSeconds 剩余时间
         */
        public void onUpdate(int currentRemainingSeconds);
    }

    /**
     * 取得文本时的监听器
     */
    public interface TextViewGetListener{
        public TextView OnGetShowTextView();
    }
}
