package com.yidao.threekmo.utils;

import android.content.Context;

import com.yidao.threekmo.db_bean.DaoMaster;
import com.yidao.threekmo.db_bean.DaoSession;
import com.yidao.threekmo.db_bean.SearchHistroyBean;
import com.yidao.threekmo.db_bean.SearchHistroyBeanDao;

import java.util.List;

/**
 * Created by Administrator on 2017\8\28 0028.
 */

public class HistroyUtils {
    private static HistroyUtils histroyUtils;
    private final SearchHistroyBeanDao searchHistroyBeanDao;

    public static HistroyUtils getInstance(Context context){
        if(null == histroyUtils){
            histroyUtils = new HistroyUtils(context);
        }
        return histroyUtils;
    }

    public HistroyUtils(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "search.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        searchHistroyBeanDao = daoSession.getSearchHistroyBeanDao();
    }

    public void insertData(String nama){
        SearchHistroyBean searchHistroyBean = new SearchHistroyBean(null, nama);
        searchHistroyBeanDao.insert(searchHistroyBean);

    }

    public List<SearchHistroyBean> getDatas(){
        return searchHistroyBeanDao.queryBuilder().build().list();
    }

    public void deleteData(SearchHistroyBean searchHistroyBean){
        searchHistroyBeanDao.delete(searchHistroyBean);
    }
}
