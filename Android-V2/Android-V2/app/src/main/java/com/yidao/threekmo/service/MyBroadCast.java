package com.yidao.threekmo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2017\9\8 0008.
 */

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//            Toast.makeText(context, "安装完成", Toast.LENGTH_SHORT).show();
            String path = Environment.getExternalStorageDirectory() + File.separator + "YiDaoCompany/3kmo20171018";
            File file = new File(path);
            if(file.exists()){
                clear(file);
            }else{
                Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            }

        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//            Toast.makeText(context, "删除完成", Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
//            Toast.makeText(context, "替换完成", Toast.LENGTH_SHORT).show();
        }

    }

    private void clear(File file) {
        if (file.exists()) { //指定文件是否存在
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                file.delete(); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    clear(f); //递归删除
                    //Log.d("fileName", f.getName()); //打印文件名
                }
            }
            file.delete(); //删除文件夹（song,art,lyric）
        }
    }
}
