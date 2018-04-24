package com.ijustyce.fastkotlin.contentprovider;

import com.ijustyce.fastkotlin.utils.ILog;
import com.ijustyce.fastkotlin.utils.ThreadUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yangchun on 16/7/7.
 */
public class CommonData {

    private volatile static HashMap<String, HashMap<String, String>> userCaches = new HashMap<>();
    private static String userId = "0";

    public static String get(String name, String userId){
        HashMap<String, String> map = getMap(userId);
        return map.get(name);
    }

    public static String get(String name){
        return get(name, userId);
    }

    private static HashMap<String, String> getMap(String userId) {
        HashMap<String, String> map = userCaches.get(userId);
        if (map == null) {
            synchronized (CommonData.class) {
                map = userCaches.get(userId);
                if (map == null) {
                    map = DBTools.getCommonValue(userId);
                    userCaches.put(userId, map);
                }
            }
        }
        return map;
    }

    private static void updateCaches(String name, String value, String userId) {
        HashMap<String, String> map = getMap(userId);
        map.put(name, value);
    }

    private static void updateCaches(String name, String value) {
        updateCaches(name, value, userId);
    }

    private static void removeCaches(String name, String userId) {
        HashMap<String, String> map = getMap(userId);
        map.remove(name);
    }

    private static void removeCaches(String name) {
        removeCaches(name, userId);
    }

    public static void put(final String name, final Object value) {
        if (value == null) {
            ILog.INSTANCE.e("value is null , not save...");
            return;
        }
        ThreadUtils.INSTANCE.execute(new Runnable() {
           @Override
           public void run() {
               updateCaches(name, String.valueOf(value));
               DBTools.deleteCommonByKey(name, userId);
               DBTools.saveCommonData(name, String.valueOf(value), userId);
           }
       });
    }

    public static void put(final String name, final Object value, final String userId){
        if (value == null) {
            ILog.INSTANCE.e("value is null , not save...");
            return;
        }
        ThreadUtils.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                updateCaches(name, String.valueOf(value), userId);
                DBTools.deleteCommonByKey(name, userId);
                DBTools.saveCommonData(name, String.valueOf(value), userId);
            }
        });
    }

    public static void saveCommonList(List<CommonBean> list) {
        DBTools.saveCommonList(list);
    }

    public static void remove(final String name){
        ThreadUtils.INSTANCE.execute(new Runnable() {
           @Override
           public void run() {
               removeCaches(name);
               DBTools.deleteCommonByKey(name, userId);
           }
       });
    }

    public static void deleteAll(String userId) {
        DBTools.deleteAllCommon(userId);
    }

    public static void deleteAll() {
        deleteAll(userId);
    }

    public static void remove(final String name, final String userId){
        ThreadUtils.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                remove(name, userId);
                DBTools.deleteCommonByKey(name, userId);
            }
        });
    }
}
