package com.ijustyce.fastkotlin.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.ijustyce.fastkotlin.IApplication;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yangchun on 16/6/19.
 */
class DBTools {

    private static ContentResolver contentResolver;
    private static String COMMON;

    static {
        contentResolver = IApplication.application.getContentResolver();
        COMMON = "content://" + IApplication.application.getPackageName() + ".contentprovider/common";
    }

    static void saveCommonData(String name, String value, String userId) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("value", value);
        values.put("userId", userId);
        contentResolver.insert(Uri.parse(COMMON), values);
        values.clear();
    }

    static HashMap<String, String> getCommonValue(String userId) {
        Uri uri = Uri.parse(COMMON);
        String[] projection = {"_id", "name", "value"};
        String sortOrder = "_id ASC";
        Cursor cursor = contentResolver.query(uri, projection, "userId = '" + userId + "'", null, sortOrder);
        HashMap<String, String> data = new HashMap<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex("value"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                data.put(name, value);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return data;
    }

    public static void deleteAllCommon(String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            String sql = userId == null ? null : "userId = '" + userId + "'";
            contentResolver.delete(uri, sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveCommonList(final List<CommonBean> beans) {
        if (beans == null || beans.isEmpty()) return;
        ContentValues[] array = new ContentValues[beans.size()];
        int index = 0;
        for (CommonBean bean : beans) {

            ContentValues values = new ContentValues();
            values.put("name", bean.name);
            values.put("value", bean.value);
            values.put("userId", bean.userId);
            array[index] = values;
            index++;
        }
        try {
            contentResolver.bulkInsert(Uri.parse(COMMON), array);
            beans.clear();
        }catch (Exception e) {
            beans.clear();
            e.printStackTrace();
        }
    }

    static void deleteCommonByKey(String name, String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            contentResolver.delete(uri, "name = '" + name + "' and userId = '" + userId + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
