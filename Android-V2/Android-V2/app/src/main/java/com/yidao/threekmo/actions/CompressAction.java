package com.yidao.threekmo.actions;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;

/**
 * Created by Lee on 2017/7/19.
 */

public class CompressAction extends AsyncTask<List<String>,Integer,List<String>> {
    private CompressListener compressListener;
    private Context context;

    public CompressAction(Context context) {
        this.context = context;
    }

    @Override
    protected List<String> doInBackground(List<String>... params) {
        List<String> orialImageList = params[0];
        List<String> compImageList = new ArrayList<>();
        for (int i = 0; i < orialImageList.size(); i++) {
            String url = orialImageList.get(i);
            File file = new File(url);
            try {
                File compressFile = Luban.with(context).load(file).get();
                compImageList.add(compressFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return compImageList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(null!=compressListener){
            compressListener.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(List<String> list) {
        super.onPostExecute(list);
        if(null!=compressListener){
            compressListener.onPostExecute(list);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(null!=compressListener){
            compressListener.onUpdate(values[0]);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(null!=compressListener){
            compressListener.onCancle();
        }
    }

    public interface CompressListener{
        public void onPreExecute();

        public void onUpdate(int index);

        public void onPostExecute(List<String> imageList);

        public void onCancle();
    }

    public void setCompressListener(CompressListener compressListener) {
        this.compressListener = compressListener;
    }
}
