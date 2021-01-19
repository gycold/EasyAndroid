package com.easytools.tools;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import java.io.IOException;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * package: com.easytools.tools.FlashlightUtils
 * author: gyc
 * description:闪光灯相关
 * time: create at 2021-01-19 0019 15:04
 */
public class FlashlightUtils {

    private static Camera mCamera;
    private static SurfaceTexture mSurfaceTexture;

    private FlashlightUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设备是否支持闪光灯.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFlashlightEnable() {
        return Utils.getApp()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 闪光灯是否开启.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFlashlightOn() {
        if (!init()) return false;
        Camera.Parameters parameters = mCamera.getParameters();
        return FLASH_MODE_TORCH.equals(parameters.getFlashMode());
    }

    /**
     * 设置闪光灯状态.
     *
     * @param isOn True to turn on the flashlight, false otherwise.
     */
    public static void setFlashlightStatus(final boolean isOn) {
        if (!init()) return;
        final Camera.Parameters parameters = mCamera.getParameters();
        if (isOn) {
            if (!FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                try {
                    mCamera.setPreviewTexture(mSurfaceTexture);
                    mCamera.startPreview();
                    parameters.setFlashMode(FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (!FLASH_MODE_OFF.equals(parameters.getFlashMode())) {
                parameters.setFlashMode(FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }

    /**
     * 闪光灯销毁.
     */
    public static void destroy() {
        if (mCamera == null) return;
        mCamera.release();
        mSurfaceTexture = null;
        mCamera = null;
    }

    private static boolean init() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open(0);
                mSurfaceTexture = new SurfaceTexture(0);
            } catch (Throwable t) {
                Log.e("FlashlightUtils", "init failed: ", t);
                return false;
            }
        }
        if (mCamera == null) {
            Log.e("FlashlightUtils", "init failed.");
            return false;
        }
        return true;
    }
}