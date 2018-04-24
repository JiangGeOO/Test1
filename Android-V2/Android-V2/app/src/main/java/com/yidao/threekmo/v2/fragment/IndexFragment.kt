package com.yidao.threekmo.v2.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.AbsListView
import android.widget.ImageView
import com.ijustyce.fastkotlin.base.BaseListFragment
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IAdapter
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.ijustyce.fastkotlin.utils.ILog
import com.ijustyce.fastkotlin.utils.getWidth
import com.yidao.threekmo.BR
import com.yidao.threekmo.R
import com.yidao.threekmo.application.MyApplication
import com.yidao.threekmo.bean.AchieveBinnerResult
import com.yidao.threekmo.bean.AchieveBinnerResult.DataBean
import com.yidao.threekmo.bean.FirstListResult
import com.yidao.threekmo.bean.MainHomeListItem
import com.yidao.threekmo.databinding.IndexFragmentTopView
import com.yidao.threekmo.databinding.IndexFragmentView
import com.yidao.threekmo.databinding.ItemFragmentIndexTopView
import com.yidao.threekmo.parameter.WebParameters
import com.yidao.threekmo.retrofit_server.SubjectServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.IndexNewModel
import com.yidao.threekmo.v2.ResponseModel
import com.yidao.threekmo.v2.viewmodel.IndexFragmentVm
import retrofit2.Call
import retrofit2.Response

/**
 * Created by deepin on 18-1-8.
 */
class IndexFragment : BaseListFragment<IndexFragmentView, MainHomeListItem, FirstListResult>() {

    private var topView: IndexFragmentTopView? = null
    private var topAdapter: IAdapter<DataBean>? = null
    private var topData: ArrayList<DataBean>? = null
    private var indexNews: IndexNewModel? = null
    private var indexFragmentVm: IndexFragmentVm? = null
    private var iconTransWidth = 0f
    private var isIconHide = false

    override fun bindingInfo(): BindingInfo? {
        return BindingInfo.fromLayoutIdAndBindName(R.layout.item_fragment_index, BR.item).add(BR.event, indexFragmentVm)
    }

    override fun viewModel(): IBaseEvent? {
        indexFragmentVm = indexFragmentVm ?: IndexFragmentVm(activity)
        return indexFragmentVm
    }

    override fun afterCreate() {
        val showHowToWin = arguments?.getBoolean("drogue", false)
        contentView?.iconWin?.visibility = if (showHowToWin == true) View.VISIBLE else View.GONE
        iconTransWidth = getWidth(107).toFloat()
        contentView?.recyclerView?.list?.mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    hideWinIcon()
                }else {
                    showWinIcon()
                }
            }
        })
    }

    private fun hideWinIcon() {
        if (isIconHide) return
        val translate = TranslateAnimation(Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, iconTransWidth, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f)
        translate.fillAfter = true
        translate.duration = 300
        contentView?.iconWin?.startAnimation(translate)
        isIconHide = true
    }

    private fun showWinIcon() {
        if (!isIconHide) return
        val translate = TranslateAnimation(Animation.ABSOLUTE, iconTransWidth,
                Animation.ABSOLUTE, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f)
        translate.fillAfter = true
        translate.duration = 300
        contentView?.iconWin?.startAnimation(translate)
        isIconHide = false
    }

    override fun getListCall(pageNo: Int): Call<FirstListResult>? {
        initTopView()
        initNews()
        val from = if (pageNo == 1) 0 else iDataInterface?.data?.size?: 0
        return HttpManager.service(SubjectServer::class.java).getActivityList("0", "", "",
                "", "", 0, 0,
                MyApplication.getInstance().longitude.toString(), MyApplication.getInstance().latitude.toString(),
                from, WebParameters.PAGE_SIZE)
    }

    private fun initNews() {
        indexNews ?: kotlin.run {
            getNetData(object : HttpManager.HttpResult<ResponseModel<IndexNewModel>> {
                override fun success(call: Call<ResponseModel<IndexNewModel>>?,
                                     response: Response<ResponseModel<IndexNewModel>>?) {
                    indexNews = response?.body()?.data
                    showNews()
                }
            }, HttpManager.service(SubjectServer::class.java).indexNews, false)
        }
    }

    private fun showNews() {
        indexNews ?: return
        indexFragmentVm?.setNewsData(indexNews)
        indexFragmentVm?.handler = Handler()
        newsSize = indexNews?.list?.size ?: 0
        topView?.newContent?.text = getNextText()
        indexFragmentVm?.handler?.postDelayed(runnable, 5000)
    }

    private val runnable = Runnable {
        startAnim()
    }

    private fun getNextText(): String {
        if (index > newsSize - 1) return ""
        if (index == newsSize - 1) index = 0
        else index++
        val bean = indexNews?.list?.get(index)
        bean ?: return ""
        return "用户" + bean.phone + " 兑换了iphone8"
    }

    private var index = 0
    private var newsSize = 0

    private fun startAnim() {
        val set = AnimationSet(true)
        val translate = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1.5f)
        translate.duration = 1000
        set.addAnimation(translate)
        val alpha = AlphaAnimation(1.0f, 0.0f)
        alpha.duration = 1000
        set.addAnimation(alpha)
        translate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                ILog.e("===end===")
                indexFragmentVm?.handler?.postDelayed(runnable, 3000)
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        topView?.newContent?.startAnimation(set)
    }

    private fun initTopView() {
        contentView?.recyclerView?.list ?: return
        topView ?: kotlin.run {
            topView = DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.view_fragment_index_top, null, false)
            topView?.event = indexFragmentVm
            topView?.list?.layoutManager = IRecyclerView.buildHorizontalLinearLayout(activity)
            topData = ArrayList()
            val bindInfo = BindingInfo.fromLayoutIdAndBindName(R.layout.item_fragment_index_top, BR.item)
                    .add(BR.event, indexFragmentVm).setAfterCreateCallBack(object : BindingInfo.ItemCreate {
                override fun <T> onCreated(item: T, position: Int, binding: ViewDataBinding?) {
                    if (binding is ItemFragmentIndexTopView) {
                        setColorFilter(binding)
                    }
                }
            })
            topAdapter = IAdapter(context, topData, bindInfo)
            topView?.list?.adapter = topAdapter
            contentView?.recyclerView?.list?.addHeaderView(topView?.root)
        }
        requestBannerData()
    }

    private fun setColorFilter(bannerView: ItemFragmentIndexTopView) {
        val drawable = bannerView.bannerImg.drawable
        val bitmap = if(drawable is BitmapDrawable) drawable.bitmap else null
        bitmap ?: return
        Palette.from(bitmap).generate { palette ->
            addFilterToView(bannerView.bannerImg, palette)
        }
    }

    private fun addFilterToView(imgView: ImageView?, palette: Palette?) {
        imgView ?: return
        palette ?: return
        val vibrant = palette.getVibrantColor(0x000000)
        val vibrantLight = palette.getLightVibrantColor(0x000000)
        val vibrantDark = palette.getDarkVibrantColor(0x000000)
        val muted = palette.getMutedColor(0x000000)
        val mutedLight = palette.getLightMutedColor(0x000000)
        val mutedDark = palette.getDarkMutedColor(0x000000)

        ILog.e("===color===", "vibrant is $vibrant")
        ILog.e("===color===", "vibrantLight is $vibrantLight")
        ILog.e("===color===", "vibrantDark is $vibrantDark")
        ILog.e("===color===", "muted is $muted")
        ILog.e("===color===", "mutedLight is $mutedLight")
        ILog.e("===color===", "mutedDark is $mutedDark")
    }

    private fun requestBannerData() {
        if (topData?.isEmpty() == true) getNetData(object : HttpManager.HttpResult<AchieveBinnerResult> {
            override fun success(call: Call<AchieveBinnerResult>?, response: Response<AchieveBinnerResult>?) {
                val result = response?.body()?.data
                result ?: return
                topData?.addAll(result)
                topAdapter?.dataChanged()
            }
        }, HttpManager.service(SubjectServer::class.java).getBinnerImage(LoginUtils.getToken(getActivity())), false)
    }

    override fun recyclerView(): IRecyclerView? {
        return contentView?.recyclerView?.list
    }

    override val layoutId: Int get() = R.layout.fragment_index_mvvm
}