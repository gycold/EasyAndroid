package com.easytools.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * package: com.easytools.tools.AssetDatabaseOpenHelper
 * author: gyc
 * description:目录资源获取，获取assets目录下数据库
 * time: create at 2017/1/7 15:39
 */

public class AssetDatabaseOpenHelper {

    private Context context;
    private String databaseName;

    public AssetDatabaseOpenHelper(Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    /**
     * 创建或打开一个数据库
     *
     * @return 返回数据库的对象
     */
    public synchronized SQLiteDatabase getWriteableDatabase() {
        File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * 创建或打开一个只读数据库
     *
     * @return 返回数据库的对象
     */
    public synchronized SQLiteDatabase getReadableDatabase() {
        File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * 返回数据库名称
     *
     * @return
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * @param dbFile
     * @throws IOException
     */
    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(databaseName);
        FileUtils.writeFile(dbFile, is);
        is.close();
    }

    /**
     * 获取asset文件下的资源文件信息
     *
     * @param fileName 文件名
     * @param context  上下文
     * @return 文件信息
     */
    public static String getFromAssets(String fileName, Context context) {
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));

            bufReader = new BufferedReader(inputReader);

            String line = "";
            StringBuffer result = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
                if (bufReader != null) {
                    bufReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
