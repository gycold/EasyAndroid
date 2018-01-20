package com.easytools.tools;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * package: com.easytools.tools.GsonUtil
 * author: gyc
 * description:
 * time: create at 2016/10/17 16:05
 */

public class GsonUtil {

    /**
     * JSON格式字符串转为实体类
     *
     * @param jsonResult JSON格式字符串
     * @param clazz      实体类
     * @param <T>        泛型
     * @return 泛型对象
     */
    public static <T> T json2Bean(String jsonResult, Class<T> clazz) {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonResult, clazz);
        return t;
    }

    /**
     * 实体类转为JSON格式的字符串
     *
     * @param object 实体对象
     * @return JSON格式字符串
     */
    public static String bean2Json(Object object) {
        return new Gson().toJson(object);

    }

    /**
     * JSON格式字符串转换为实体类数组
     *
     * @param jsonResult JSON格式字符串
     * @param clazz      实体类
     * @param <T>        泛型
     * @return           实体类数组
     */
    public static <T> List<T> json2List(String jsonResult, Class<T> clazz) {
        try {
            JSONArray array = new JSONArray(jsonResult);
            int length = array.length();
            Gson gson = new Gson();
            ArrayList<T> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                String singleJson = array.getString(i);
                list.add(gson.fromJson(singleJson, clazz));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
