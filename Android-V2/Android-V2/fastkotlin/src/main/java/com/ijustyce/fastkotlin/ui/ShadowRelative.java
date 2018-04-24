package com.ijustyce.fastkotlin.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ijustyce.fastkotlin.R;
import com.ijustyce.fastkotlin.task.BaseTask;
import com.ijustyce.fastkotlin.utils.AutoLayoutKt;
import com.ijustyce.fastkotlin.utils.ILog;

import java.lang.ref.WeakReference;

public class ShadowRelative extends RelativeLayout {

    private int mShadowColor;
    private int mShadowRadius;
    private int mCornerRadius;
    private int mDx;
    private int mDy;

    private boolean mInvalidateShadowOnSizeChanged = true;
    private boolean mForceInvalidateShadow = false;

    public ShadowRelative(Context context) {
        super(context);
        initView(context, null);
    }

    public ShadowRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ShadowRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ILog.INSTANCE.e("===ShadowRelative===", "size changed...");
        if(w > 0 && h > 0 && (getBackground() == null || mInvalidateShadowOnSizeChanged || mForceInvalidateShadow)) {
            ILog.INSTANCE.e("===ShadowRelative===", "size changed so setBackground...");
            mForceInvalidateShadow = false;
            setBackgroundCompat(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ILog.INSTANCE.e("===ShadowRelative===", "onLayout...");
        if (mForceInvalidateShadow) {
            ILog.INSTANCE.e("===ShadowRelative===", "onLayout so setBackground ...");
            mForceInvalidateShadow = false;
            setBackgroundCompat(right - left, bottom - top);
        }
    }

    public void setInvalidateShadowOnSizeChanged(boolean invalidateShadowOnSizeChanged) {
        mInvalidateShadowOnSizeChanged = invalidateShadowOnSizeChanged;
    }

    public void invalidateShadow() {
        mForceInvalidateShadow = true;
        requestLayout();
        invalidate();
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);

        int xPadding = (int) (mShadowRadius + Math.abs(mDx));
        int yPadding = (int) (mShadowRadius + Math.abs(mDy));
        setPadding(xPadding, yPadding, xPadding, yPadding);
    }

    private void setBackgroundCompat(int w, int h) {
        new ITask(this, w, h).execute();
    }

    private static class ITask extends BaseTask<BitmapDrawable> {
        private WeakReference<ShadowRelative> weakReference;
        private int width, height;
        ITask(ShadowRelative shadowRelative, int width, int height){
            weakReference = new WeakReference<>(shadowRelative);
            this.width = width;
            this.height = height;
        }
        @Override
        protected BitmapDrawable doInBackground(Integer... integers) {
            ShadowRelative shadowRelative = weakReference.get();
            if (shadowRelative == null) return null;
            Bitmap bitmap = shadowRelative.createShadowBitmap(width, height, shadowRelative.mCornerRadius,
                    shadowRelative.mShadowRadius, shadowRelative.mDx, shadowRelative.mDy,
                    shadowRelative.mShadowColor, Color.TRANSPARENT);
            return new BitmapDrawable(shadowRelative.getResources(), bitmap);
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            ShadowRelative shadowRelative = weakReference.get();
            if (shadowRelative == null || bitmapDrawable == null) return;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                shadowRelative.setBackgroundDrawable(bitmapDrawable);
            } else {
                shadowRelative.setBackground(bitmapDrawable);
            }
        }
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }
        try {
            mCornerRadius = attr.getInt(R.styleable.ShadowLayout_sl_cornerRadius, 4);
            mShadowRadius = attr.getInt(R.styleable.ShadowLayout_sl_shadowRadius, 4);
            mDx = attr.getInt(R.styleable.ShadowLayout_sl_dx, 0);
            mDy = attr.getInt(R.styleable.ShadowLayout_sl_dy, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_sl_shadowColor, getResources().getColor(R.color.default_shadow_color));

            mCornerRadius = AutoLayoutKt.getWidth(mCornerRadius);
            mShadowRadius = AutoLayoutKt.getWidth(mShadowRadius);
            mDx = AutoLayoutKt.getWidth(mDx);
            mDy = AutoLayoutKt.getHeight(mDy);
        } finally {
            attr.recycle();
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);

        if (dy > 0) {
            shadowRect.top += dy;
            shadowRect.bottom -= dy;
        } else if (dy < 0) {
            shadowRect.top += Math.abs(dy);
            shadowRect.bottom -= Math.abs(dy);
        }

        if (dx > 0) {
            shadowRect.left += dx;
            shadowRect.right -= dx;
        } else if (dx < 0) {
            shadowRect.left += Math.abs(dx);
            shadowRect.right -= Math.abs(dx);
        }

        Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(fillColor);
        shadowPaint.setStyle(Paint.Style.FILL);

        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }
        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }
}
