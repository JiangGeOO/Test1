package com.ijustyce.fastkotlin.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ijustyce.fastkotlin.IApplication;
import com.ijustyce.fastkotlin.R;
import com.ijustyce.fastkotlin.utils.CommonTools;
import com.ijustyce.fastkotlin.utils.ILog;
import com.ijustyce.fastkotlin.utils.RegularUtils;
import com.ijustyce.fastkotlin.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangchun on 16/6/14.
 */
public class ImageLoader {

    private static double wBili, hBili;
    private static final int MIN_WIDTH = 200;
    private static final int MIN_HEIGHT = 200;
    private static NetworkDisablingLoader networkDisablingLoader;

    static {
        int target_width = StringUtils.INSTANCE.getInt(CommonTools.INSTANCE.getMetaData(IApplication.application,
                "design_width"));
        int target_height = StringUtils.INSTANCE.getInt(CommonTools.INSTANCE.getMetaData(IApplication.application,
                "design_height"));
        int mWidth = CommonTools.INSTANCE.getScreenWidth(IApplication.application);
        int mHeight = CommonTools.INSTANCE.getScreenHeight(IApplication.application);

        wBili = ((double) mWidth) / ((double) target_width);
        hBili = ((double) mHeight / ((double) target_height));

        double max = 0.85;

        if (wBili > max) wBili = max;
        if (hBili > max) hBili = max;
    }

    /**
     * @param img
     * @param url
     * @param type
     */
    public static final int TYPE_TOP = 1;
    public static final int TYPE_BOTTOM = 2;
    public static final int TYPE_LEFT = 3;
    public static final int TYPE_RIGHT = 4;
    public static final int TYPE_ALL = 5;

    public static <T> void loadWidthCorner(ImageView img, T url, int width, int height,
                                       int radius, int type) {
        RoundedCornersTransformation transformation = null;
        if (type < TYPE_TOP || type > TYPE_RIGHT) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.ALL);
        } else if (type == 1) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.TOP);
        } else if (type == 2) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.BOTTOM);
        } else if (type == 3) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.LEFT);
        } else {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.RIGHT);
        }
        load(img, url, width, height, transformation);
    }

    public static <T> void loadWidthCorner(ImageView img, T url, int radius, int type) {
        loadWidthCorner(img, url, -1, -1, radius, type);
    }

    public static void loadCircle(ImageView img, String url) {
        loadCircle(img, url, -1, -1);
    }

    public static void loadCircle(ImageView img, String url, int width, int height) {
        Transformation transformation = new CropCircleTransformation(img.getContext());
        load(img, url, width, height, transformation);
    }

    public static ImageLoadType type;

    public abstract static class ImageLoadType {
        public abstract boolean isAliYunServer(String url);

        public abstract boolean isSlowNetWork();
    }

    private static class NetworkDisablingLoader implements StreamModelLoader<String> {
        @Override
        public DataFetcher<InputStream> getResourceFetcher(final String model, int width, int height) {
            return new DataFetcher<InputStream>() {
                @Override
                public InputStream loadData(Priority priority) throws Exception {
                    throw new IOException("Forces Glide network failure");
                }

                @Override
                public void cleanup() {
                }

                @Override
                public String getId() {
                    return model;
                }

                @Override
                public void cancel() {
                }
            };
        }
    }

    private static NetworkDisablingLoader getNetWorkDisablingLoader() {
        NetworkDisablingLoader loader = networkDisablingLoader;
        if (loader == null) {
            synchronized (ImageLoader.class) {
                loader = networkDisablingLoader;
                if (loader == null) {
                    networkDisablingLoader = loader = new NetworkDisablingLoader();
                }
            }
        }
        return loader;
    }

    private static void doAliYunLoad(final ImageView img, String defaultUrl, String wifiUrl,
                                     int width, int height, boolean slowNetWork
            , Transformation transformation) {
        ILog.INSTANCE.e("===ImageLoader===", "wifi url is " + wifiUrl + " default url is " + defaultUrl);
        if (slowNetWork) {
            BitmapRequestBuilder<String, Bitmap> offlineBuild =
                    getBitmapBuilder(img, wifiUrl, width, height, transformation, getNetWorkDisablingLoader());
            BitmapRequestBuilder<String, Bitmap> netBuild =
                    getBitmapBuilder(img, defaultUrl, width, height, transformation, null);
            if (netBuild == null || offlineBuild == null) return;
            netBuild.thumbnail(offlineBuild).into(simpleTarget(img));
        } else {
            BitmapRequestBuilder wifi = getBitmapBuilder(img, wifiUrl, width, height, transformation, null);
            if (wifi != null) {
                wifi.into(simpleTarget(img));
            }
        }
    }

    private static SimpleTarget<Bitmap> simpleTarget(final ImageView img) {
        return new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (img.getContext() != null) img.setImageBitmap(resource);
            }
        };
    }

    private static boolean preLoad(ImageView img, String url, int width, int height,
                                   Transformation transformation) {
        if (type == null || !type.isAliYunServer(url)) return false;

        if (width == -1 && height == -1) {
            doAliYunLoad(img, url, url, width, height, type.isSlowNetWork(), transformation);
        } else {
            String defaultUrl = "x-oss-process=image/quality,q_85/resize,";
            String wifiUrl = "x-oss-process=image/quality,q_85/resize,";
            if (width > 1 && height > 1) {
                String sizeUrl = "h_" + height + ",w_" + width;
                defaultUrl += sizeUrl;
                wifiUrl += sizeUrl;
            }
            if (url.contains("?")) {
                String[] array = url.split("\\?");
                if (array.length > 1) {
                    wifiUrl = array[0] + "?" + wifiUrl + "," + array[1];
                    defaultUrl = array[0] + "?" + wifiUrl + "," + array[1];
                }
            } else {
                defaultUrl = url + "?" + defaultUrl;
                wifiUrl = url + "?" + wifiUrl;
            }
            doAliYunLoad(img, defaultUrl, wifiUrl, width, height, type.isSlowNetWork(), transformation);
        }
        return true;
    }

    public static <T> void load(ImageView img, T url, int width, int height, Transformation transformation) {
        if (url instanceof String && StringUtils.INSTANCE.isEmpty((String) url) || img == null) {
            return;
        }
        if (width > MIN_WIDTH && height > MIN_HEIGHT) {
            height *= hBili;
            width *= wBili;
        }

        if (url instanceof String) {
            String[] urls = ((String) url).split("\\?");

            //  this is gif
            if (urls[0].endsWith(".gif")) {
                DrawableRequestBuilder builder = getDrawableBuilder(img, (String) url, width, height);
                if (builder != null) {
                    builder.into(img);
                }
                return;
            }
            //  preload 用于加载其他aliyun等图片资源,以下是正常逻辑!aliyun不支持gif
            if (preLoad(img, (String) url, width, height, transformation)) return;
        }
        BitmapRequestBuilder builder = getBitmapBuilder(img, url, width, height, transformation, null);
        if (builder != null) {
            builder.into(img);
        }
    }

    private static DrawableRequestBuilder getDrawableBuilder(ImageView img, String url, int width, int height) {
        if (img == null || !canLoad(img.getContext())) return null;
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        DrawableRequestBuilder<String> builder;
        try {
            builder = Glide.with(img.getContext()).load(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (width > 0 && height > 0) builder.override(width, height);
        if (placeholder != null) {
            builder.placeholder((int) placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int) placeholder : (int) error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int) placeholder : (int) nullImg);
        }
        return builder;
    }

    private static <T> BitmapRequestBuilder<T, Bitmap> getBitmapBuilder(ImageView img, T url, int width, int height,
                                                                         Transformation transformation, StreamModelLoader loader) {

        if (img == null || !canLoad(img.getContext())) return null;
        BitmapRequestBuilder<T, Bitmap> builder;
        try {
            RequestManager manager = Glide.with(img.getContext());
            if (loader != null) manager.using(loader);
            builder = manager.load(url).asBitmap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        boolean hasSize = false;
        if (width > 1 && height > 1) {
            builder.override(width, height);
            hasSize = true;
        }
        if (transformation != null) {
            builder.format(DecodeFormat.PREFER_ARGB_8888).transform(new CenterCrop(img.getContext()), transformation);
        }if (hasSize && width <= MIN_WIDTH && height <= MIN_HEIGHT) {
            builder.format(DecodeFormat.PREFER_ARGB_8888);
        }
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        if (placeholder != null) {
            builder.placeholder((int) placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int) placeholder : (int) error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int) placeholder : (int) nullImg);
        }
        return builder.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    private static RequestListener<String, GlideDrawable> mRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //显示错误信息
            Log.w("li", "onException: ", e);
            //打印请求URL
            Log.d("li", "onException: " + model);
            //打印请求是否还在进行
            Log.d("li", "onException: " + target.getRequest().isRunning());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable>
                target, boolean isFromMemoryCache, boolean isFirstResource) {
            //打印请求URL
            Log.d("li", "onException: " + model);
            //打印请求是否还在进行
            Log.d("li", "onException: " + target.getRequest().isRunning());
            return false;
        }
    };

    private static boolean canLoad(Context context) {
        if (context == null) return false;
        if (context instanceof Activity && ((Activity) context).isFinishing()) return false;
        return true;
    }

    public static void load(ImageView img, String url, int width, int height) {
        load(img, url, width, height, null);
    }

    public static void load(ImageView img, String url) {
        load(img, url, -1, -1, null);
    }

    public static void getFromUrl(Activity activity, String url, int width, int height, final GetBitmapCall call) {
        if (!RegularUtils.INSTANCE.isCommonUrl(url)) {
            call.onGetBitmap(null);
            return;
        }
        Log.e("===Share===", "begin load image...");
        BitmapRequestBuilder<String, Bitmap> builder = Glide.with(activity).load(url).asBitmap().centerCrop();
        if (width > 0 && height > 0) {
            builder = builder.override(width, height);
        }
        builder.diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
            boolean callBackCalled = false;

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (callBackCalled) return;
                callBackCalled = true;
                call.onGetBitmap(resource);
                Log.e("===Share===", "onResourceReady...");
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                if (callBackCalled) return;
                callBackCalled = true;
                call.onGetBitmap(null);
                Log.e("===Share===", "onLoadFailed...");
            }
        });
    }
}
