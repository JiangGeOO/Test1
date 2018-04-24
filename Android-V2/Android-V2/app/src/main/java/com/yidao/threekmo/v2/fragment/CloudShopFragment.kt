package com.yidao.threekmo.v2.fragment

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.android.databinding.library.baseAdapters.BR
import com.ijustyce.fastkotlin.base.BaseListFragment
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IAdapter
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.ijustyce.fastkotlin.utils.ILog
import com.ijustyce.fastkotlin.utils.getHeight
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.BaseResultModel
import com.yidao.threekmo.databinding.CloudShopFragmentView
import com.yidao.threekmo.databinding.DefaultBannerView
import com.yidao.threekmo.databinding.FineSelectGoods
import com.yidao.threekmo.databinding.StarSuggestView
import com.yidao.threekmo.retrofit_server.SubjectServer
import com.yidao.threekmo.v2.model.*
import com.yidao.threekmo.v2.viewmodel.CloudShopVm
import retrofit2.Call
import retrofit2.Response

/**
 * Created by deepin on 18-1-15.
 */
class CloudShopFragment: BaseListFragment <CloudShopFragmentView, ListHotItem, CloudShopList>() {

    private var bannerList: ArrayList<ListBannerItem>? = null
    private var topView: DefaultBannerView? = null

    private var starSuggestList: ArrayList<ListHotItem>? = null
    private var starSuggestAdapter: IAdapter<ListHotItem>? = null

    private var fineSelectList: ArrayList<ListHandpickItem>? = null
    private var fineSelectAdapter: IAdapter<ListHandpickItem>? = null
    private val maxScrollHeight = getHeight(340).toFloat()
    private var viewModel: CloudShopVm? = null

    override val layoutId: Int get() = R.layout.fragment_shop_mvvm

    override fun bindingInfo(): BindingInfo? {
        recyclerView()?.setGirdLayout(2)
        return BindingInfo.fromLayoutIdAndBindName(R.layout.item_new_shop_mvvm, BR.viewModel)
    }

    override fun viewModel(): IBaseEvent? {
        viewModel = CloudShopVm(activity)
        return viewModel
    }

    override fun afterCreate() {
        setUpBanner()
        setUpStarSuggest()
        setUpFineGoods()
        setUpSearchAlpha()
    }

    private fun setUpBanner() {
        topView = DataBindingUtil.inflate(LayoutInflater.from(activity),
                R.layout.view_banner_default, null, false)
        topView?.scrollPager?.init()?.setImgWidthAndHeight(750, 445)?.
                setPointsMarginAndSize(24, 16)
        topView?.viewModel = viewModel
        contentView?.recyclerView?.list?.addHeaderView(topView?.root)
    }

    private fun setUpStarSuggest() {
        val starSuggestView = DataBindingUtil.inflate<StarSuggestView>(LayoutInflater.from(activity),
                R.layout.view_star_suggest, null, false)
        contentView?.recyclerView?.list?.addHeaderView(starSuggestView?.root)

        starSuggestView.starSuggestList.layoutManager = IRecyclerView.buildLinearLayout(activity)
        starSuggestList = ArrayList()
        val bindInfo = BindingInfo.fromLayoutIdAndBindName(R.layout.item_star_suggest, BR.viewModel)
        starSuggestAdapter = IAdapter(activity, starSuggestList, bindInfo)
        starSuggestView.starSuggestList.adapter = starSuggestAdapter
    }

    private fun setUpFineGoods() {
        val fineSelectGoods = DataBindingUtil.inflate<FineSelectGoods>(LayoutInflater.from(activity),
                R.layout.view_fine_select_goods, null, false)
        contentView?.recyclerView?.list?.addHeaderView(fineSelectGoods?.root)

        fineSelectGoods.starSuggestList.layoutManager = IRecyclerView.buildHorizontalLinearLayout(activity)
        fineSelectList = ArrayList()
        val bindInfo = BindingInfo.fromLayoutIdAndBindName(R.layout.item_fine_select, BR.viewModel)
        fineSelectAdapter = IAdapter(activity, fineSelectList, bindInfo)
        fineSelectGoods.starSuggestList.adapter = fineSelectAdapter
    }

    private fun setUpSearchAlpha() {
        var totalHeight = 0
        contentView?.recyclerView?.list?.mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                totalHeight += dy
                updateAlpha(totalHeight)
            }
        })
    }

    private fun updateAlpha(value: Int) {
        val searchBarAlpha = value.toFloat() / maxScrollHeight
        contentView?.topSearch?.alpha = searchBarAlpha
        if (Build.VERSION.SDK_INT >= 21) {
            val color = "#${alphaValue(searchBarAlpha)}ffffff"
            getActivity()?.window?.statusBarColor = Color.parseColor(color)
        }
    }

    private fun alphaValue(value: Float): String {
        if (value >= 1) return ""
        if (value <= 0) return "00"
        val decimalValue = value * 256 - 1
        val hexValue = Integer.toHexString(decimalValue.toInt())
        return when{
            hexValue.length < 2 -> "0$hexValue"
            hexValue.length != 2 -> "00"
            else -> hexValue
        }
    }

    override fun getListCall(pageNo: Int): Call<CloudShopList>? {
        if (bannerList == null) {
            getNetData(object: HttpManager.HttpResult<BaseResultModel<CloudShopTopData>> {
                override fun success(call: Call<BaseResultModel<CloudShopTopData>>?, response: Response<BaseResultModel<CloudShopTopData>>?) {
                    val data = response?.body()?.data
                    data ?: return
                    showTopData(data)
                }
            }, HttpManager.service(SubjectServer::class.java).shopTop, false)
        }
        return HttpManager.service(SubjectServer::class.java).shopListMvvm("", (pageNo -1) * 10, 10)
    }

    override fun recyclerView(): IRecyclerView? {
        return contentView?.recyclerView?.list
    }

    private fun showBanner(data: CloudShopTopData) {
        val urls = ArrayList<String>()
        bannerList = data.listBanner
        bannerList?: return
        bannerList?.forEach({banner -> urls.add(banner.photo) })
        topView?.scrollPager?.setUrl(urls)

        topView?.scrollPager?.setTouchUpListener {
            contentView?.recyclerView?.list?.onTouchUp()
        }
    }

    private fun showStarSuggest(data: CloudShopTopData) {
        val list = data.listHot
        list ?: return
        starSuggestList?.addAll(list)
        starSuggestAdapter?.dataChanged()
    }

    private fun showFineSelect(data: CloudShopTopData) {
        val list = data.listHandpick
        list ?: return
        fineSelectList?.addAll(list)
        fineSelectAdapter?.dataChanged()
    }

    private fun showTopData(data: CloudShopTopData) {
        showBanner(data)
        showStarSuggest(data)
        showFineSelect(data)
    }
}