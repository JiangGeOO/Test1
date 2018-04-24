package com.yidao.threekmo.utils;

import android.content.Context;
import android.content.Intent;

import com.yidao.myutils.file.SPUtils;
import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.activitys.PhoneLoginActivity;
import com.yidao.threekmo.bean.UserBean;
import com.yidao.threekmo.parameter.SPParameters;

/**
 * Created by Administrator on 2017\8\25 0025.
 */

public class LoginUtils {
    public static void saveUserBean(UserBean userBean, Context context){
        synchronized (LoginUtils.class){
            Long userId = userBean.getUserId();
            LogUtils.e("userId:" + userId);
            SPUtils.put(context, SPParameters.SP_USERID,userId);
            SPUtils.put(context, SPParameters.SP_BIRTHDAY,userBean.getBirthday());
            SPUtils.put(context, SPParameters.SP_NICKNAME,null==userBean.getNickname()?"":userBean.getNickname());
            SPUtils.put(context, SPParameters.SP_USERDESC,null==userBean.getIndividualitySignature()?"":userBean.getIndividualitySignature());
            SPUtils.put(context, SPParameters.SP_USERICON,null==userBean.getFace()?"":userBean.getFace());
            SPUtils.put(context, SPParameters.SP_USERSEX,null==userBean.getSex()? 0L :userBean.getSex());
            SPUtils.put(context, SPParameters.SP_WECHATNUM,null==userBean.getWebchatNum()?"":userBean.getWebchatNum());
            SPUtils.put(context, SPParameters.SP_OPENNOTIFY,null==userBean.getNotification()?"":userBean.getNotification());
            SPUtils.put(context, SPParameters.SP_PHONE,null==userBean.getPhone()?"":userBean.getPhone());
        }
    }

    public static UserBean getUserBean(Context context){
        synchronized (LoginUtils.class){
            UserBean userBean = new UserBean();
            userBean.setUserId(((long) SPUtils.get(context, SPParameters.SP_USERID, 0L)));
            userBean.setNickname(((String) SPUtils.get(context, SPParameters.SP_NICKNAME, "")));
            userBean.setIndividualitySignature(((String) SPUtils.get(context, SPParameters.SP_USERDESC, "")));
            userBean.setFace(((String) SPUtils.get(context, SPParameters.SP_USERICON, "")));
            userBean.setSex(((long) SPUtils.get(context, SPParameters.SP_USERSEX, 0L)));
            userBean.setWebchatNum(((String) SPUtils.get(context, SPParameters.SP_WECHATNUM, "")));
            userBean.setNotification(((long) SPUtils.get(context, SPParameters.SP_OPENNOTIFY, 0L)));
            userBean.setBirthday(((long) SPUtils.get(context, SPParameters.SP_BIRTHDAY, 0L)));
            userBean.setPhone(((String) SPUtils.get(context, SPParameters.SP_PHONE, "")));
            return userBean;
        }
    }

    public static void saveToken(String token,Context context){
        SPUtils.put(context,SPParameters.SP_TOKEN,token);
    }

    public static String getToken(Context context){
        return ((String) SPUtils.get(context, SPParameters.SP_TOKEN, ""));
    }

    public static boolean isLogin(boolean b,Context context){
        long id = (long) SPUtils.get(context, SPParameters.SP_USERID, 0L);
        if(id == 0){
            //未登陆
            if(b){
                context.startActivity(new Intent(context, PhoneLoginActivity.class));
                return false;
            }else {
                return false;
            }
        }else {
            //已登陆
            return true;
        }
    }
}
