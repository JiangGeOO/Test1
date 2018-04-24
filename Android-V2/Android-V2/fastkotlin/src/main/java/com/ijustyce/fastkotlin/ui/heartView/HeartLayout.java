package com.ijustyce.fastkotlin.ui.heartView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ijustyce.fastkotlin.R;

import java.lang.ref.WeakReference;
import java.util.Random;

public class HeartLayout  extends RelativeLayout implements View.OnClickListener {

    private AbstractPathAnimator mAnimator;
    private AttributeSet attrs = null;
    private int defStyleAttr = 0;
    private OnHearLayoutListener onHearLayoutListener;
    private static HeartHandler heartHandler;
    private static HeartThread heartThread;

    public void setOnHearLayoutListener(OnHearLayoutListener onHearLayoutListener) {
        this.onHearLayoutListener = onHearLayoutListener;
    }

    public interface OnHearLayoutListener {
        boolean onAddFavor();
    }

    public HeartLayout(Context context) {
        super(context);
        findViewById(context);
    }

    public HeartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        findViewById(context);
    }

    public HeartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
        findViewById(context);
    }

    private Bitmap bitmap;

    private void findViewById(Context context) {
        LayoutInflater.from(context).inflate(R.layout.fastandroiddev3_heartview_periscope, this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_like_liked);
        dWidth = com.ijustyce.fastkotlin.utils.AutoLayoutKt.getWidth(61);
        dHeight = com.ijustyce.fastkotlin.utils.AutoLayoutKt.getHeight(52);
        textHight = sp2px(getContext(), 20) + dHeight / 2;

        pointx = dWidth;//随机上浮方向的x坐标

        bitmap.recycle();
        setClipChildren(false);
    }

    private int mHeight;
    private int mWidth;
    private int textHight;
    private int dHeight;
    private int dWidth;
    private int initX;
    private int pointx;

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static class HeartHandler extends Handler {
        public final static int MSG_SHOW = 1;
        WeakReference<HeartLayout> wf;

        HeartHandler(HeartLayout layout) {
            wf = new WeakReference<>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HeartLayout layout = wf.get();
            if (layout == null) return;
            switch (msg.what) {
                case MSG_SHOW:
                    layout.addFavor();
                    break;
            }
        }
    }

    public class HeartThread implements Runnable {

        private long time = 0;
        private int allSize = 0;

        public void addTask(long time, int size) {
            this.time = time;
            allSize += size;
        }

        public void clean() {
            allSize = 0;
        }

        @Override
        public void run() {
            if (heartHandler == null) return;

            if (allSize > 0) {
                heartHandler.sendEmptyMessage(HeartHandler.MSG_SHOW);
                allSize--;
            }
            postDelayed(this, time);
        }
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HeartLayout, defStyleAttr, 0);

        if (pointx <= initX && pointx >= 0) {
            pointx -= 10;
        } else if (pointx >= -initX && pointx <= 0) {
            pointx += 10;
        } else pointx = initX;

        mAnimator = new PathAnimator(AbstractPathAnimator.Config.fromTypeArray(a, initX, textHight, pointx, dWidth, dHeight));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取本身的宽高 这里要注意,测量之后才有宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        initX = mWidth / 2 - dWidth / 2;
    }

    public AbstractPathAnimator getAnimator() {
        return mAnimator;
    }

    public void setAnimator(AbstractPathAnimator animator) {
        clearAnimation();
        mAnimator = animator;
    }

    public void clearAnimation() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
        }
        removeAllViews();
    }

    private static int[] drawableIds = new int[]{R.drawable.emoji_1, R.drawable.emoji_2,
            R.drawable.emoji_3, R.drawable.emoji_4, R.drawable.emoji_5, R.drawable.emoji_6,
            R.drawable.emoji_7, R.drawable.emoji_8, R.drawable.emoji_9, R.drawable.emoji_10,
            R.drawable.emoji_11, R.drawable.emoji_12, R.drawable.emoji_13, R.drawable.emoji_14,
            R.drawable.emoji_15, R.drawable.emoji_16, R.drawable.emoji_17, R.drawable.emoji_18,
            R.drawable.emoji_19, R.drawable.emoji_20, R.drawable.emoji_21, R.drawable.emoji_22,
            R.drawable.emoji_23, R.drawable.emoji_24, R.drawable.emoji_25, R.drawable.emoji_26,
            R.drawable.emoji_27, R.drawable.emoji_28, R.drawable.emoji_29, R.drawable.emoji_30,
            R.drawable.emoji_31, R.drawable.emoji_32, R.drawable.emoji_33, R.drawable.emoji_34,
            R.drawable.emoji_35, R.drawable.emoji_36, R.drawable.emoji_37, R.drawable.emoji_38,
            R.drawable.emoji_39, R.drawable.emoji_40, R.drawable.emoji_41, R.drawable.emoji_42,
            R.drawable.emoji_43, R.drawable.emoji_44, R.drawable.emoji_45, R.drawable.emoji_46,
            R.drawable.emoji_47, R.drawable.emoji_48, R.drawable.emoji_49, R.drawable.emoji_50,
            R.drawable.emoji_51, R.drawable.emoji_52, R.drawable.emoji_53, R.drawable.emoji_54,
            R.drawable.emoji_55, R.drawable.emoji_56, R.drawable.emoji_57, R.drawable.emoji_58,
            R.drawable.emoji_59, R.drawable.emoji_60, R.drawable.emoji_61, R.drawable.emoji_62,
            R.drawable.emoji_63, R.drawable.emoji_65, R.drawable.emoji_66, R.drawable.emoji_67,
            R.drawable.emoji_68, R.drawable.emoji_69, R.drawable.emoji_70, R.drawable.emoji_71,
            R.drawable.emoji_72, R.drawable.emoji_73, R.drawable.emoji_74, R.drawable.emoji_75,
            R.drawable.emoji_76, R.drawable.emoji_77, R.drawable.emoji_78};
    private Random random = new Random();

    public void addFavor() {
        HeartView heartView = new HeartView(getContext());
        heartView.setDrawable(drawableIds[random.nextInt(77)]);
        init(attrs, defStyleAttr);
        mAnimator.start(heartView, this);
    }

    private long nowTime, lastTime;
    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    public static int sizeOfInt(int x) {
        for (int i = 0; ; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

    public void addFavor(int size) {
        switch (sizeOfInt(size)) {
            case 1:
                size = size % 10;
                break;
            default:
                size = size % 100;
        }
        if (size == 0) return;
        nowTime = System.currentTimeMillis();
        long time = nowTime - lastTime;
        if (lastTime == 0)
            time = 2 * 1000;//第一次分为2秒显示完

        time = time / (size + 15);
        if (heartThread == null) {
            heartThread = new HeartThread();
        }
        if (heartHandler == null) {
            heartHandler = new HeartHandler(this);
            heartHandler.post(heartThread);
        }
        heartThread.addTask(time, size);
        lastTime = nowTime;
    }

    public void addHeart(int color) {
        HeartView heartView = new HeartView(getContext());
        heartView.setColor(color);
        init(attrs, defStyleAttr);
        mAnimator.start(heartView, this);
    }

    public void addHeart(int color, int heartResId, int heartBorderResId) {
        HeartView heartView = new HeartView(getContext());
        heartView.setColorAndDrawables(color, heartResId, heartBorderResId);
        init(attrs, defStyleAttr);
        mAnimator.start(heartView, this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img) {
            if (onHearLayoutListener != null) {
                boolean isAdd = onHearLayoutListener.onAddFavor();
                if (isAdd) addFavor();
            }
        }
    }

    public void clean() {
        if (heartThread != null) {
            heartThread.clean();
        }
    }

    public void release() {
        if (heartHandler != null) {
            heartHandler.removeCallbacks(heartThread);
            heartThread = null;
            heartHandler = null;
        }
    }
}