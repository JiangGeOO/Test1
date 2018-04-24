package com.yidao.threekmo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2017\8\24 0024.
 */

public class RetrofitManager {
    private List<Call> callList;

    public RetrofitManager() {
        this.callList = new ArrayList<Call>();
    }

    public void addCall(Call call){
        synchronized (callList){
            callList.add(call);
        }
    }

    public void cancleAllCall(){
        synchronized (callList){
            Iterator<Call> iterator = callList.iterator();
            while (iterator.hasNext()){
                Call call = iterator.next();
                if(call == null || call.isCanceled() ||call.isExecuted()){
                    iterator.remove();
                    continue;
                }
                call.cancel();
                iterator.remove();
            }
        }
    }
}
