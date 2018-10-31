package com.easytools.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * package: com.easytools.tools.SpUtils
 * author: gyc
 * description:SharedPreferences相关的工具类
 * <p>
 * getInstance    : 获取单例
 * putString      : 保存String，这是一个重载方法
 * putInt         : 保存int值，这是一个重载方法
 * putLong        : 保存long值，这是一个重载方法
 * putFloat       : 保存float值，这是一个重载方法
 * putBoolean     : 保存boolean值，这是一个重载方法
 * putStringSet   : 保存类型为String的集合值
 * <p>
 * getString      : 获取String值，这是一个重载方法
 * getInt         : 获取int值，这是一个重载方法
 * getLong        : 获取long值，这是一个重载方法
 * getFloat       : 获取float值，这是一个重载方法
 * getBoolean     : 获取boolean值，这是一个重载方法
 * getStringSet   : 获取类型为String的集合值，这是一个重载方法
 * <p>
 * getAll         : 获取所有缓存数据，以偶对象返回
 * contains       : 指定数据是否被缓存
 * remove         : 删除一条缓存，这是一个重载方法
 * clear          : 清空缓存，这是一个重载方法
 * <p>
 * time: create at 2017/2/15 22:30
 */
public class SpUtils {

    private static String SP_NAME = "";

    //默认操作模式，代表该文件是私有数据，只能被应用本身访问。一般sp只是用该模式。
    //在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中，可以使用Context.MODE_APPEND
    private static int SP_MODE = Context.MODE_PRIVATE;
    private SharedPreferences sp;

    //单例模式
    private static class LazyLoader {
        private static final SpUtils INSTANCE = new SpUtils(SP_NAME, SP_MODE);
    }

    /**
     * 获取单例 {@link SpUtils}
     *
     * @return 返回单例
     */
    public static SpUtils getInstance() {
        return getInstance(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取单例 {@link SpUtils}
     *
     * @param mode 操作模式
     * @return 返回单例 {@link SpUtils}
     */
    public static SpUtils getInstance(final int mode) {
        return getInstance(SP_NAME, mode);
    }

    /**
     * 获取单例 {@link SpUtils}
     *
     * @param spName sp名称
     * @return 返回单例 {@link SpUtils}
     */
    public static SpUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    /**
     * 获取单例 {@link SpUtils}
     *
     * @param spName sp名称
     * @param mode   操作模式
     * @return 返回单例 {@link SpUtils}
     */
    public static SpUtils getInstance(String spName, final int mode) {
        SP_NAME = spName;
        SP_MODE = mode;
        if (StringUtils.isEmpty(SP_NAME)) SP_NAME = "SpUtils";
        return LazyLoader.INSTANCE;
    }

    private SpUtils(final String spName) {
        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private SpUtils(final String spName, final int mode) {
        sp = Utils.getApp().getSharedPreferences(spName, mode);
    }

    /**
     * 保存String
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void putString(@NonNull final String key, final String value) {
        putString(key, value, false);
    }

    /**
     * 保存String值
     *
     * @param key      键
     * @param value    值
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void putString(@NonNull final String key, final String value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * 获取String值
     *
     * @param key 键
     * @return 返回缓存或默认值 {@code ""}
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    /**
     * 获取String值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 返回值，不存在则返回默认值{@code defaultValue}
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * 保存int值
     *
     * @param key   键
     * @param value 值
     */
    public void putInt(@NonNull final String key, final int value) {
        putInt(key, value, false);
    }

    /**
     * 保存int值
     *
     * @param key      键
     * @param value    值
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void putInt(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * 获取int值
     *
     * @param key 键
     * @return 返回缓存或默认值 {@code -1}
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    /**
     * 获取int值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 返回缓存或默认值 {@code defaultValue}
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * 保存long值
     *
     * @param key   键
     * @param value 值
     */
    public void putLong(@NonNull final String key, final long value) {
        putLong(key, value, false);
    }

    /**
     * 保存long值
     *
     * @param key      键
     * @param value    值
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void putLong(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    /**
     * 获取long值
     *
     * @param key 键
     * @return 返回缓存或默认值 {@code -1}
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * 获取long值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 返回缓存或默认值 {@code defaultValue}
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * 保存float值
     *
     * @param key   键
     * @param value 值
     */
    public void putFloat(@NonNull final String key, final float value) {
        putFloat(key, value, false);
    }

    /**
     * 保存float值
     *
     * @param key      键
     * @param value    值
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void putFloat(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * 获取float值
     *
     * @param key 键
     * @return 返回缓存或默认值 {@code -1f}
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * 获取float值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 返回缓存或默认值 {@code defaultValue}
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * 保存boolean值
     *
     * @param key   键
     * @param value 值
     */
    public void putBoolean(@NonNull final String key, final boolean value) {
        putBoolean(key, value, false);
    }

    /**
     * 保存boolean值
     *
     * @param key      键
     * @param value    值
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void putBoolean(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * 获取boolean值
     *
     * @param key 键
     * @return 返回缓存或默认值 {@code false}
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * 获取boolean值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 返回缓存或默认值 {@code defaultValue}
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 保存类型为String的集合值
     *
     * @param key   键
     * @param value 值
     */
    public void putStringSet(@NonNull final String key, final Set<String> value) {
        putStringSet(key, value, false);
    }

    /**
     * 保存类型为String的集合值
     *
     * @param key      键
     * @param value    值
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void putStringSet(@NonNull final String key,
                             final Set<String> value,
                             final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit();
        } else {
            sp.edit().putStringSet(key, value).apply();
        }
    }

    /**
     * 获取类型为String的集合值
     *
     * @param key 键
     * @return 返回缓存或默认值 {@code Collections.<String>emptySet()}
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * 获取类型为String的集合值
     *
     * @param key          键
     * @param defaultValue 默认集合
     * @return 返回缓存或默认值 {@code defaultValue}
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * 返回该缓存中所有的值
     *
     * @return 该缓存中所有值
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * 是否存在缓存值
     *
     * @param key 键
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    /**
     * 删除一条缓存
     *
     * @param key The key of sp.
     */
    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    /**
     * 删除一条缓存
     *
     * @param key      键
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * 清空该缓存
     */
    public void clear() {
        clear(false);
    }

    /**
     * 清空该缓存
     *
     * @param isCommit True  使用 {@link SharedPreferences.Editor#commit()},
     *                 false 使用 {@link SharedPreferences.Editor#apply()}
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

}
