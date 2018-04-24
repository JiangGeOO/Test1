package com.yidao.threekmo.utils;

import android.content.Context;
import android.net.Uri;

import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.parameter.WebParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Lee on 2017/6/8.
 */

public class OkHttpUtils {
    private static final int CACHE_SIZE = 10*1024*1024;//缓存大小10mb
    private static OkHttpUtils okHttpUtils;
    private static OkHttpClient mOkHttpClient;
    private static Map<String,List<Cookie>> cookeiStore = null;

    public static String getSessionString(){
        StringBuffer stringBuffer = new StringBuffer();
        if(null!=cookeiStore){
            List<Cookie> cookies = cookeiStore.get(Uri.parse(WebParameters.SERVERURL).getHost());
            if(null!=cookies&&cookies.size()>0){
                for (int i = 0; i < cookies.size(); i++) {
                    Cookie cooke = cookies.get(i);
                    String s = cooke.toString();

                    if(s.startsWith("USESSIONID")){
                        int start = s.indexOf("=");
                        int end = s.indexOf(".");
                        String substring = s.substring(start + 1, end);
                        stringBuffer.append(substring);
                    }
                }
                return stringBuffer.toString();
            }else {
                return "";
            }
        }else {
            return "";
        }

    }

    //网络请求初始化，再application中使用
    public static void init(Context context){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        cookeiStore = new HashMap<String,List<Cookie>>();
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);//读取超时
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);//连接超时
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时
        clientBuilder.cookieJar(new CookieJar() {   //添加cookie拦截器
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                String cookieStr = "";
                for (int i = 0; i < cookies.size(); i++) {
                    cookieStr+=cookies.get(i).toString();
                }
                LogUtils.i("保存cookie:  cookies=" + cookieStr);
                cookeiStore.put(url.host(),cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookeiStore.get(url.host());
                if(null!=cookies){
                    String cookieStr="";
                    for (int i = 0; i < cookies.size(); i++) {
                        cookieStr+=cookies.get(i).toString();
                    }
                    LogUtils.i("设置cookie:  cookies=" + cookieStr);
                }
                return null==cookies?new ArrayList<Cookie>():cookies;
            }
        });
        //设置get缓存
//        clientBuilder.cache(new Cache(context.getCacheDir(),CACHE_SIZE));
        mOkHttpClient = clientBuilder.build();
    }

    public static OkHttpUtils getInstance(){
        if(null == okHttpUtils){
            synchronized (OkHttpUtils.class) {
                if(null == okHttpUtils){
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return  okHttpUtils;
    }

    private Headers setHeaders(Map<String,String> headersParams){
        Headers.Builder headersBuilder = new Headers.Builder();

        if(null != headersParams){
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersBuilder.add(key, headersParams.get(key));
            }
        }
        return headersBuilder.build()   ;
    }

    private RequestBody setRequestBody(Map<String,String> bodyParams){
        RequestBody body=null;
        okhttp3.FormBody.Builder formEncodingBuilder=new okhttp3.FormBody.Builder();
        if(bodyParams != null){
            Iterator<String> iterator = bodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, bodyParams.get(key));

            }
        }
        body=formEncodingBuilder.build();
        return body;

    }

    private String setGetParams( Map<String, String> mapParams){
        String strParams = "";
        if(mapParams != null){
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                if(strParams.isEmpty()){
                    strParams += "?"+ key + "=" + mapParams.get(key);
                }else {
                    strParams += "&"+ key + "=" + mapParams.get(key);
                }
            }
        }

        return strParams;
    }

    public void getExecute(String url, Map<String,String> getParams, final RequestCallBack requestCallBack){
        Request.Builder requestBuilder = new Request.Builder();
        String etParamsgStr = setGetParams(getParams);
        final String totalUrl = url + etParamsgStr;
        requestBuilder.url(totalUrl);
        Request request = requestBuilder.build();
        requestCallBack.onRequestStart();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                requestCallBack.onFailed(call,e);
                LogUtils.e("网络请求异常:url=" + totalUrl  + "     原因:" + e.getMessage());

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String respondBody = response.body().string();
                LogUtils.e("网络请求:url=" + totalUrl  + "     response:" + response);
                requestCallBack.onResponse(call, respondBody);


            }
        });

    }

    public void postExecute(String url, Map<String,String> postParams, final RequestCallBack requestCallBack){

        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = setRequestBody(postParams);
        requestBuilder.url(url);
        requestBuilder.post(requestBody);
        Request request = requestBuilder.build();
        requestCallBack.onRequestStart();
        final String totalUrl = url + setGetParams(postParams);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                requestCallBack.onFailed(call,e);
                LogUtils.e("网络请求异常:url=" + totalUrl  + "     原因:" + e.getMessage());

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String respondBody = response.body().string();
                LogUtils.e("网络请求:url=" + totalUrl  + "     response:" + respondBody);
                requestCallBack.onResponse(call, respondBody);
            }
        });

    }

    public void upLoadFile(final String url, File file, String fileName, final RequestCallBack requestCallBack){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("file",fileName,requestBody);
        MultipartBody multipartBody = bodyBuilder.build();

        Request request = new Request.Builder().url(url).post(multipartBody).build();
        requestCallBack.onRequestStart();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                requestCallBack.onFailed(call,e);
                LogUtils.e("图片上传异常:url=" + url  + "     原因:" + e.getMessage());

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String respondBody = response.body().string();
                LogUtils.e("图片上传请求:url=" + url  + "     response:" + response);
                requestCallBack.onResponse(call, respondBody);


            }
        });
    }

    //图片上传同步
    public Response upLoadFile(final String url, File file,boolean isVideo){
        //定义MIME类型
        MediaType mediaType = MediaType.parse("application/octet-stream");

        //创建代表文件的实体
        RequestBody fileBody = RequestBody.create(mediaType,file);

        //创建表单实体，并把文件实体添加到表单实体当中
        RequestBody requestBody;
        if(isVideo){
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(Headers.of("Content-Disposition","form-data;name=\"file\";" +
                            "filename=\"test.png\""),fileBody)
                    .addFormDataPart("file_type","video")
                    .build();
        }else {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(Headers.of("Content-Disposition","form-data;name=\"file\";" +
                            "filename=\"test.png\""),fileBody)
                    .addFormDataPart("file_type","image")
                    .build();
        }
        //创建request对象，并把实体以Post方式发送给服务器
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //创建Call对象。
        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void downLoadFile(String url, final String fileName, final String fileDir, final FileRequestCallBack fileRequestCallBack){
        final Request request = new Request.Builder().url(url).build();
        final String filePath = fileDir + File.separator + fileName;
        fileRequestCallBack.onRequestStart();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fileRequestCallBack.onFailed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long totalSize = 0;
                long currentSize = 0;
                int len = 0;
                InputStream is = null;
                FileOutputStream fos = null;
                byte[] buff = new byte[1024];
                try{
                    if(null!=response && null!=response.body() && null!=response.body().byteStream()){
                        totalSize = response.body().contentLength();
                        is = response.body().byteStream();
                        fos = new FileOutputStream(filePath);

                        while ((len = is.read(buff)) != -1){
                            currentSize+=len;
                            fos.write(buff,0,len);
                            fileRequestCallBack.onProgress(totalSize,currentSize);
                        }
                        fos.flush();
                        fileRequestCallBack.onSuccessCallBack(filePath);

                    }
                }catch (IOException e){

                }finally {
                    if(null!=is){
                        is.close();
                    }

                    if(null!=fos){
                        fos.close();
                    }
                }


            }
        });

    }


    public interface RequestCallBack{
        public void onRequestStart();

        public void onFailed(Call call, IOException e);

        public void onResponse(Call call, String respBody);
    }

    public interface FileRequestCallBack{
        public void onRequestStart();

        public void onProgress(long totlaSize, long currentSize);

        public void onFailed(Call call, IOException e);

        public void onSuccessCallBack(String fileName);
    }

    public void clearCookie(){
        String host = HttpUrl.parse(WebParameters.SERVERURL).host();
        cookeiStore.remove(host);
    }
}
