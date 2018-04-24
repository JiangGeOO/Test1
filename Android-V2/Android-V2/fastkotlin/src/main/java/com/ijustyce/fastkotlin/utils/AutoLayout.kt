package com.ijustyce.fastkotlin.utils

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ijustyce.fastkotlin.IApplication
import com.ijustyce.fastkotlin.glide.ImageLoader
import com.ijustyce.fastkotlin.ui.heartView.HeartLayout

/**
 * Created by yangchun on 2016/11/12.
 */

var scaleWidth = -1.0
var scaleHeight = -1.0
private var useAutoLayout = true

private val MIN_HEIGHT = 1
private val MIN_WIDTH = 1
private var statusBarHeight = -1

@BindingAdapter("fitWindows")
fun fitWindow(view: View, adjustHeight: Int) {
    val params = view.layoutParams
    if (params is ViewGroup.MarginLayoutParams) {
        params.topMargin = params.topMargin + getStatusHeight(view.context)
    }
    if (adjustHeight == 1 && params != null && params.height > 0) {
        params.height = params.height + getStatusHeight(view.context)
    }
}

fun getStatusHeight(context: Context): Int {
    if (statusBarHeight > 0) return statusBarHeight
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
    return statusBarHeight
}

/**
 * 加载图片
 */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView?, imageUrl: String?) {
    //Glide.with(view.getContext()).load(imageUrl).into(view);
    ImageLoader.load(view, imageUrl)
}

/**
 * 加载图片，指定宽高
 */
@BindingAdapter("imageUrl", "width", "height")
fun loadImageWithSize(view: ImageView?, imageUrl: String?, width: Int, height: Int) {
    ImageLoader.load(view, imageUrl, width, height)
}

/**
 * 加载圆形图片
 */
@BindingAdapter("imageUrl", "circle")
fun loadCircle(view: ImageView?, imageUrl: String?, circle: Int) {
    if (circle == 1) {
        ImageLoader.loadCircle(view, imageUrl)
    } else {
        ImageLoader.load(view, imageUrl)
    }
}

/**
 * 加载圆形图片，指定宽高
 */
@BindingAdapter("imageUrl", "width", "height", "circle")
fun loadCircle(view: ImageView?, imageUrl: String?, width: Int, height: Int, circle: Int) {
    if (circle == 1) {
        ImageLoader.loadCircle(view, imageUrl, width, height)
    } else {
        ImageLoader.load(view, imageUrl, width, height)
    }
}

/**
 * 加载圆形图片
 */
@BindingAdapter("imageUrl", "corner", "type")
fun loadWithCorner(view: ImageView?, imageUrl: String?, corner: Int, type: Int) {
    if (corner > 0) {
        ImageLoader.loadWidthCorner(view, imageUrl, corner, type)
    } else {
        ImageLoader.load(view, imageUrl)
    }
}

/**
 * 加载圆形图片，指定宽高
 */
@BindingAdapter("imageUrl", "width", "height", "corner", "type")
fun loadWithCorner(view: ImageView?, imageUrl: String?, width: Int, height: Int, corner: Int, type: Int) {
    if (corner > 0) {
        ImageLoader.loadWidthCorner(view, imageUrl, width, height, corner, type)
    } else {
        ImageLoader.load(view, imageUrl, width, height)
    }
}

//  fix the bug since android studio 3.1 canary 6  for more detail please visit https://issuetracker.google.com/issues/71436846
@BindingAdapter("android:onClick")
fun setOnClick(view: View, clickListener: View.OnClickListener) {
    view.setOnClickListener(clickListener)
}

@BindingAdapter("android:onClick")
fun setOnClick(view: HeartLayout, clickListener: View.OnClickListener) {
    view.setOnClickListener(clickListener)
}

/**
 * 加载圆形图片
 */
@BindingAdapter("imageUrl", "corner")
fun loadWithCorner(view: ImageView, imageUrl: String, corner: Int) {
    if (corner > 0) {
        ImageLoader.loadWidthCorner(view, imageUrl, corner, ImageLoader.TYPE_ALL)
    } else {
        ImageLoader.load(view, imageUrl)
    }
}

@BindingAdapter("colorFilter")
fun setColorFilter(view: ImageView, color: Int) {
    if (color != -1) {
        view.setColorFilter(color)
    }
    else view.clearColorFilter()
}

/**
 * 加载圆形图片，指定宽高
 */
@BindingAdapter("imageUrl", "width", "height", "corner")
fun loadWithCorner(view: ImageView, imageUrl: String?, width: Int, height: Int, corner: Int) {
    if (corner > 0) {
        ImageLoader.loadWidthCorner(view, imageUrl, width, height, corner, ImageLoader.TYPE_ALL)
    } else {
        ImageLoader.load(view, imageUrl, width, height)
    }
}

//  实现其他功能

@BindingAdapter("android:background")
fun setBackground(view: View?, resId: Int) {
    if (resId == -100 || view == null) return
    try {
        view.setBackgroundResource(resId)
    } catch (ignore: Exception) {
        try {
            view.setBackgroundColor(resId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}

@BindingAdapter("android:src")
fun setSrc(view: ImageView, bitmap: Bitmap?) {
    if (bitmap == null) return
    view.setImageBitmap(bitmap)
}

@BindingAdapter("android:src")
fun setSrc(view: ImageView, resId: Int) {
    if (resId == -100) return
    view.setImageResource(resId)
}

@BindingAdapter("android:textColor")
fun setTextColor(view: TextView?, color: Int) {
    if (view == null || color == -100) return
    view.setTextColor(color)
}

//  基于MVVM实现的自动布局

private fun initView(): Boolean {
    if (scaleWidth == 1.0 && scaleHeight == 1.0) return false
    if (scaleHeight > 0 || scaleWidth > 0) return true
    if (!useAutoLayout) return false
    val screenWidth = CommonTools.getScreenWidth(IApplication.application)
    val screenHeight = CommonTools.getScreenHeight(IApplication.application)
    val targetWidth: Int
    val targetHeight: Int
    try {
        targetWidth = StringUtils.getInt(CommonTools.getMetaData(IApplication.application,
                "design_width"))
        targetHeight = StringUtils.getInt(CommonTools.getMetaData(IApplication.application,
                "design_height"))
    } catch (e: Exception) {
        e.printStackTrace()
        useAutoLayout = false
        return false
    }

    if (targetWidth < 1 || targetHeight < 1) {
        useAutoLayout = false
        ILog.e("===autoLayout===", "target width or height is too low, make sure you have " + "set  design_width and design_height in your AndroidManifest")
        return false
    }
    if (screenHeight < 1 || screenWidth < 1) {
        useAutoLayout = false
        ILog.e("===autoLayout===", "not get your screen width or height , value less than 1")
        return false
    }
    scaleWidth = screenWidth.toDouble() / targetWidth.toDouble()
    scaleHeight = screenHeight.toDouble() / targetHeight.toDouble()
    return true
}

fun getHeight(height: Int): Int {
    initView()
    val value = (scaleHeight * height).toInt()
    return if (value < MIN_HEIGHT) MIN_HEIGHT else value
}

fun getWidth(width: Int): Int {
    val value = (scaleWidth * width).toInt()
    return if (value < MIN_WIDTH) MIN_WIDTH else value
}

fun getWidthNoMin(width: Int): Int {
    val value = (scaleWidth * width).toInt()
    return value
}

//  设置宽高
@BindingAdapter("layout_width")
fun setWidth(view: View?, width: Int) {
    if (view == null || width < 0 || !initView()) return
    val params = view.layoutParams
    if (params != null) {
        params.width = getWidth(width)
    }
}

@BindingAdapter("layout_height")
fun setHeight(view: View?, height: Int) {
    if (view == null || height < 0 || !initView()) return
    val params = view.layoutParams
    if (params != null) {
        params.height = getHeight(height)
    }
}

@BindingAdapter("minHeight")
fun setMinHeight(view: View, height: Int) {
    view.minimumHeight = (height * scaleHeight).toInt()
}

@BindingAdapter("minWidth")
fun setMinWidth(view: View, height: Int) {
    view.minimumWidth = (height * scaleWidth).toInt()
}

//  设置上下左右距离

@BindingAdapter("layout_margin")
fun setMargin(view: View?, margin: Int) {
    if (view == null || !initView()) return
    val params = view.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    val widthValue = (margin * scaleWidth).toInt()
    val heightValue = (margin * scaleHeight).toInt()
    params.leftMargin = widthValue
    params.topMargin = heightValue
    params.rightMargin = widthValue
    params.bottomMargin = heightValue
}

@BindingAdapter("layout_marginTop")
fun setMarginTop(view: View?, marginTop: Int) {
    if (view == null || !initView()) return
    val params = view.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.topMargin = (marginTop * scaleHeight).toInt()
}

@BindingAdapter("layout_marginBottom")
fun setMarginBottom(view: View?, marginBottom: Int) {
    if (view == null || !initView()) return
    val params = view.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.bottomMargin = (marginBottom * scaleHeight).toInt()
}

@BindingAdapter("layout_marginLeft")
fun setMarginLeft(view: View?, marginLeft: Int) {
    if (view == null || !initView()) return
    val params = view.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.leftMargin = (marginLeft * scaleWidth).toInt()
}

@BindingAdapter("layout_marginRight")
fun setMarginRight(view: View?, marginRight: Int) {
    if (view == null || !initView()) return
    val params = view.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.rightMargin = (marginRight * scaleWidth).toInt()
}

//  设置字体大小
@BindingAdapter("textSize")
fun setTextSize(view: View?, textSize: Int) {
    if (view == null || !initView()) return
    if (view !is TextView) return
    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, (textSize * scaleWidth).toInt().toFloat())
}

//  设置padding

@BindingAdapter("padding")
fun setPadding(view: View?, padding: Int) {
    if (view == null || !initView()) return
    //  int left, int top, int right, int bottom
    val widthValue = (padding * scaleWidth).toInt()
    val heightValue = (padding * scaleHeight).toInt()
    view.setPadding(widthValue, heightValue, widthValue, heightValue)
}

@BindingAdapter("paddingLeft")
fun setPaddingLeft(view: View?, paddingLeft: Int) {
    if (view == null || !initView()) return
    //  int left, int top, int right, int bottom
    view.setPadding((paddingLeft * scaleWidth).toInt(), view.paddingTop,
            view.paddingRight, view.paddingBottom)
}

@BindingAdapter("paddingRight")
fun setPaddingRight(view: View?, paddingRight: Int) {
    if (view == null || !initView()) return
    //  int left, int top, int right, int bottom
    view.setPadding(view.paddingLeft, view.paddingTop,
            (paddingRight * scaleWidth).toInt(), view.paddingBottom)
}

@BindingAdapter("paddingTop")
fun setPaddingTop(view: View?, paddingTop: Int) {
    if (view == null || !initView()) return
    //  int left, int top, int right, int bottom
    view.setPadding(view.paddingLeft, (paddingTop * scaleHeight).toInt(),
            view.paddingRight, view.paddingBottom)
}

@BindingAdapter("paddingBottom")
fun setPaddingBottom(view: View?, paddingBottom: Int) {
    if (view == null || !initView()) return
    //  int left, int top, int right, int bottom
    view.setPadding(view.paddingLeft, view.paddingTop,
            view.paddingRight, (paddingBottom * scaleHeight).toInt())
}