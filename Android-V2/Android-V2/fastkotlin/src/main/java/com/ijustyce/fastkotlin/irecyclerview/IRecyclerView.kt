package com.ijustyce.fastkotlin.irecyclerview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Handler
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.utils.CommonTools
import com.ijustyce.fastkotlin.utils.ILog
import com.ijustyce.fastkotlin.utils.ScrollUtils
import com.ijustyce.fastkotlin.utils.getWidth

/**
 * Created by yangchun on 16/4/15.  封装的 RecyclerView
 */
class IRecyclerView : RelativeLayout {

    var mRecyclerView: RecyclerView? = null
    private var mFooter: LinearLayout? = null
    private var mFooterLoading: LinearLayout? = null
    private var mHeader: RelativeLayout? = null
    private var mContext: Context? = null
    private var mHandler: Handler? = null
    private var marginTop = 0
    var isCanPullDown = true //是否能下拉
    var adapter: IAdapter<*>? = null
    private var hasMore = true //  是否还有更多数据
    var refreshing = false

    private var refreshResult: TextView? = null
    private var loadMoreResult: TextView? = null
    private var refreshHeader: ImageView? = null
    private var repeatAnim: ImageView? = null
    private var loadImg: ImageView? = null
    private var loadMoreImg: ImageView? = null
    private var backTop: ImageView? = null
    private var canRefresh = true
    var pageSize = 10
    private var noLoadWhileScroll = false

    private var showRefreshEndAnim = true

    private val autoRefresh = Runnable {
        marginTop = 3 * headerHeight
        onTouchUp()
    }

    //  刷新结束
    private val doOnRefreshEnd = Runnable { doOnRefreshEnd() }

    private val resetRefresh = Runnable {
        if (refreshHeader != null && refreshResult != null) {
            refreshResult!!.visibility = View.GONE
            refreshHeader!!.visibility = View.VISIBLE
            if (refreshResult != null) {
                refreshResult!!.text = "刷新中・・・"
            }
        }
    }

    private val loadMoreFailed = Runnable {
        if (loadMoreResult == null || loadMoreImg == null) return@Runnable
        loadMoreImg!!.visibility = View.GONE
        loadMoreResult!!.visibility = View.VISIBLE
        if (handler != null) handler!!.postDelayed(resetFooter, 3000)
    }

    private val resetFooter = Runnable {
        if (loadMoreResult == null || loadMoreImg == null) return@Runnable
        loadMoreImg!!.visibility = View.VISIBLE
        loadMoreResult!!.visibility = View.GONE
        showLoadFinishAnim()
    }

    val isFirstVisible: Boolean
        get() {
            if (mRecyclerView == null) return false
            val layoutManager = mRecyclerView?.layoutManager
            if (layoutManager is GridLayoutManager) {
                val firstCompleteVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                return firstCompleteVisibleItem == 0 || layoutManager.findLastVisibleItemPosition() == 0
            }
            if (layoutManager is LinearLayoutManager) {
                return layoutManager.findFirstCompletelyVisibleItemPosition() <= 0 || layoutManager.findLastVisibleItemPosition() == 0

            } else if (layoutManager is StaggeredGridLayoutManager) {
                var firstVisibleItems: IntArray? = null
                var lastVisibleItems: IntArray? = null
                firstVisibleItems = layoutManager.findFirstCompletelyVisibleItemPositions(firstVisibleItems)
                lastVisibleItems = layoutManager.findLastVisibleItemPositions(lastVisibleItems)
                if (firstVisibleItems != null && firstVisibleItems.size > 0 && lastVisibleItems != null && lastVisibleItems.size > 0) {
                    return firstVisibleItems[0] == 0 || lastVisibleItems[0] == 0
                }
            }
            return false
        }

    private val endRefresh = Runnable {
        showRefreshEndAnim = true
        if (refreshResult != null) {
            refreshResult!!.text = "刷新超时"
        }
        onRefreshEnd()
    }

    private val scrollListener = object : ScrollUtils.ScrollListener {
        override fun scrollUp(value: Float) {
            //  下拉到底的逻辑
            if (value > 0 && canPullUp()) {
                if (mFooter == null) return
                mFooter!!.setPadding(0, mFooter!!.paddingTop, 0, mFooter!!.paddingBottom + value.toInt() / 3)
                return
            }
            if (value < 0 && canPullUp()) {
                showReturnToBottomAnim()
            }
            //  刷新部分的逻辑
            if (!canRefresh) {
                return
            }
            if (marginTop > 0 && value <= 0) {
                if (!isCanPullDown) {
                    return
                }
                onTouchUp()
            }
        }

        override fun scrollDown(value: Float) {
            if (!canRefresh || !isFirstVisible) {
                return
            }
            if (value >= 0 && marginTop > 0) {
                onTouchUp()
                return
            }
            //  已经在刷新或者第一条不可见的时候不padding
            if (mRecyclerView == null || refreshing) {
                return
            }
            //改了
            if (isNullHead) {
                return
            }
            if (!isCanPullDown) {
                return
            }
            updateMarginTop(value)
            paddingAndAnim()
        }
    }
    private var refreshAnimDrawable: AnimationDrawable? = null

    private val isNullHead: Boolean
        get() = mHeader == null

    @JvmOverloads
    fun onRefresh(showAnim: Boolean = false) {
        if (!isFirstVisible || !showAnim) {
            showRefreshEndAnim = false
            doRefresh()
            return
        }
        showRefreshAnim()
        if (handler == null) return
        handler!!.removeCallbacksAndMessages(null)
        handler!!.postDelayed(autoRefresh, 1000)
    }

    fun enAbleNoLoadWhileScroll(): IRecyclerView {
        this.noLoadWhileScroll = true
        if (adapter != null) {
            adapter!!.scrollFinished = true
            adapter!!.dataChanged()
        }
        return this
    }

    fun disAbleNoLoadWhileScroll(): IRecyclerView {
        this.noLoadWhileScroll = false
        if (adapter != null) {
            adapter!!.scrollFinished = true
            adapter!!.dataChanged()
        }
        return this
    }

    private fun showRefreshAnim() {
        showRefreshEndAnim = true
        val tmp = 2 * headerHeight
        val ani = ValueAnimator.ofInt(0, tmp)
        ani.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            val value = animation.animatedValue as Int
            if (mRecyclerView != null) {
                //修改了
                if (isNullHead) return@AnimatorUpdateListener
                mHeader!!.setPadding(0, value, 0, mHeader!!.paddingBottom)
            }
        })
        ani.duration = 1000
        ani.start()
    }

    private fun doRefresh() {
        if (dataInterface != null && !refreshing && canRefresh) {
            refreshing = true
            dataInterface!!.refresh()
            resetGoTop()
        }
    }

    fun resetGoTop() {
        scrollDy = 0
        checkGoTopVisible(0)
    }

    fun setBackGround(@ColorRes color: Int) {
        if (mRecyclerView != null) mRecyclerView!!.setBackgroundColor(resources.getColor(color))
    }

    fun setBackGroundDrawable(@DrawableRes drawable: Int) {
        if (mRecyclerView != null) mRecyclerView!!.setBackgroundResource(drawable)
    }

    fun setCanRefresh(canRefresh: Boolean) {
        this.canRefresh = canRefresh
    }

    @Deprecated("")
    fun showFooterWhenNoMoreData(showFooterWhenNoMoreData: Boolean) {
    }

    @Deprecated("")
    fun showFooterLabel(show: Boolean) {
        //        initFooter();
        //        if (footerLabel != null) {
        //            footerLabel.setVisibility(show ? VISIBLE : GONE);
        //        }
    }

    fun stopScroll(): Boolean {
        if (mRecyclerView == null) return false
        mRecyclerView!!.stopScroll()
        return true
    }

    fun showFooter(showFooter: Boolean) {
        if (mFooterLoading == null) return
        mFooterLoading!!.visibility = if (showFooter) View.VISIBLE else View.GONE
    }

    @Deprecated("")
    fun setFooterLabel(text: String) {

        //        initFooter();
        //        if (footerLabel == null) return;
        //        footerLabel.setText(text);
    }

    private fun createRecyclerView() {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.fastandroiddev3_view_irecyclerview_recycler, this)
        if (mView == null) {
            ILog.e(TAG, "mView is null, it is a fastandroiddev error, please contact developer... ")
            return
        }
        val view = mView.findViewById<RecyclerView>(R.id.irecyclerview_list)
        if (view is RecyclerView) {
            mRecyclerView = view
        } else
            throw RuntimeException("can not get RecyclerView, is your xml file " + "already have a view and it's id is R.id.irecyclerview_list ? ")

        backTop = mView.findViewById(R.id.goTop)
        goTopMargin(32, 32)
        minShowGoTop = CommonTools.getScreenHeight(context) / 2
        addGoTopListener()

        loadImg = mView.findViewById(R.id.loadImg)
        Glide.with(mContext).load(R.mipmap.loading_three).into(loadImg)

        mRecyclerView?.visibility = View.INVISIBLE
        mHandler = Handler()
    }

    fun goTopMargin(bottom: Int, right: Int) {
        val params = backTop?.layoutParams as RelativeLayout.LayoutParams?
        params?.bottomMargin = getWidth(bottom)
        params?.rightMargin = getWidth(right)
    }

    fun addGoTopListener() {
        backTop?.setOnClickListener { mRecyclerView?.smoothScrollToPosition(0) }
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        if (mRecyclerView == null) return
        mRecyclerView!!.layoutManager = layoutManager
    }

    var dataInterface: IDataInterface<*, *>? = null
    fun <Bean, Model : IResponseData<Bean>> dataInterface(dataInterface: IDataInterface<Bean, Model>) {
        mRecyclerView?.layoutManager = mRecyclerView?.layoutManager?: LinearLayoutManager(mContext)
        this.dataInterface = dataInterface
        this.adapter = dataInterface.adapter
        this.adapter!!.setRecyclerView(mRecyclerView!!)
        dataInterface.setIRecyclerView(this)
        addListener()
    }

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    fun loadFinish(isRefresh: Boolean, hasMore: Boolean, success: Boolean) {
        if (isRefresh) {
            if (refreshResult != null) {
                showRefreshEndAnim = true
                refreshResult!!.text = if (success) "刷新成功" else "刷新失败"
            }
            onRefreshEnd()
        }
        setHasMore(hasMore)
        showLoad(false)
    }

    fun hasFooter(): Boolean {
        return adapter!!.footerSize > 1
    }

    fun hasHeader(): Boolean {
        return adapter!!.headerSize > 1
    }

    //  加载更多结束的动画
    private fun showLoadFinishAnim() {
        val tmp = headerHeight
        val ani = ValueAnimator.ofInt(0, tmp)
        ani.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var value = animation.animatedValue as Int
            value = tmp - value
            if (mRecyclerView != null) {
                if (mFooter == null) return@AnimatorUpdateListener
                mFooter!!.setPadding(0, mFooter!!.paddingTop, 0, value - headerHeight)
            }
        })
        ani.duration = 200
        ani.start()
        showFooter(false)
    }

    private fun doOnRefreshEnd() {
        if (marginTop < 1) {
            cleanAfterRefresh()
            return
        }
        val tmp = headerHeight + marginTop
        val ani = ValueAnimator.ofInt(0, tmp)
        ani.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var value = animation.animatedValue as Int
            value = tmp - value
            if (mRecyclerView != null) {
                if (isNullHead) return@AnimatorUpdateListener
                mHeader!!.setPadding(0, value, 0, mHeader!!.paddingBottom)
            }
        })
        ani.duration = 200
        ani.start()
        cleanAfterRefresh()
    }

    private fun cleanAfterRefresh() {
        if (handler != null) handler!!.postDelayed(resetRefresh, 200)
        marginTop = 0
        if (refreshHeader != null) refreshHeader!!.setImageResource(pullAnimImgs[0])
    }

    private fun cancelAnim() {
        if (refreshAnimDrawable != null) {
            refreshAnimDrawable!!.stop()
        }
        repeatAnim!!.visibility = View.GONE
    }

    private fun onRefreshEnd() {
        refreshing = false
        if (mRecyclerView != null) mRecyclerView!!.visibility = View.VISIBLE
        if (loadImg != null) loadImg!!.visibility = View.GONE
        if (handler == null || !showRefreshEndAnim) return
        if (refreshHeader != null && refreshResult != null) {
            cancelAnim()
            refreshHeader!!.visibility = View.INVISIBLE
            refreshResult!!.visibility = View.VISIBLE
        }
        handler!!.removeCallbacksAndMessages(null)
        handler!!.postDelayed(doOnRefreshEnd, 1000)
    }

    /**
     * 瀑布流式 布局
     *
     * @param num      列数或行数（竖直即true为列数，false为行数）
     * @param vertical 是否为竖直布局
     */
    fun setStaggeredGridLayout(num: Int, vertical: Boolean = true) {
        var gridNum = num
        if (gridNum <= 0) {
            gridNum = 1
        }
        mRecyclerView?.layoutManager = StaggeredGridLayoutManager(gridNum, if (vertical)
            StaggeredGridLayoutManager.VERTICAL
        else
            StaggeredGridLayoutManager.HORIZONTAL)
    }

    @JvmOverloads
    fun setGirdLayout(num: Int, spanSizeLookup: GridLayoutManager.SpanSizeLookup? = null) {
        var gridNum = num
        if (gridNum <= 0) {
            gridNum = 1
        }
        if (gridNum > 3) {
            ILog.e("===IRecyclerView===", "you set girdlayout with " + "more than 3 span count ...")
        }
        val layoutManager = GridLayoutManager(context, gridNum)
        mRecyclerView!!.layoutManager = layoutManager
        if (spanSizeLookup != null) {
            layoutManager.spanSizeLookup = spanSizeLookup
            return
        }
        //  如果没有lookup
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter?.getItemViewType(position) == IAdapter.TYPE_NORMAL) {
                    1
                } else gridNum
            }
        }
    }

    private fun initHeader() {
        if (mHeader != null) return
        mHeader = LayoutInflater.from(mContext).inflate(R.layout.fastandroiddev3_view_irecyclerview_header, null) as RelativeLayout
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        mHeader!!.layoutParams = params
        val refresh = mHeader!!.findViewById<ImageView>(R.id.refreshImg)
        val textView = mHeader!!.findViewById<TextView>(R.id.refreshResult)
        repeatAnim = mHeader!!.findViewById(R.id.repeatAnim)
        if (refresh is ImageView) {
            refreshHeader = refresh
        }
        if (textView is TextView) {
            refreshResult = textView
        }

        if (headerHeight > 0) {
            setPadding(0, -headerHeight, 0, paddingBottom)
            return
        }

        val viewTreeObserver = mHeader!!.viewTreeObserver
        val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (headerHeight < 0) {
                    headerHeight = mHeader!!.height
                    setPadding(0, -headerHeight, 0, paddingBottom)
                }
                if (!viewTreeObserver.isAlive) return
                if (Build.VERSION.SDK_INT >= 16) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    fun addHeaderView(view: View?) {
        if (adapter != null && view != null) {
            adapter!!.addHeaderView(view)
        }
    }

    private fun initFooter() {
        mFooter ?: return

        mFooter = LayoutInflater.from(mContext).inflate(R.layout.fastandroiddev3_view_irecyclerview_footer, null)
                .findViewById<LinearLayout>(R.id.container) as LinearLayout
        val params1 = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        mFooter?.layoutParams = params1

        mFooterLoading = mFooter?.findViewById<LinearLayout>(R.id.footerLoading) as LinearLayout

        loadMoreImg = mFooter?.findViewById<ImageView>(R.id.loadImg) as ImageView
        loadMoreResult = mFooter?.findViewById<TextView>(R.id.loadResult) as TextView
        Glide.with(mContext).load(R.mipmap.loading_three).into(loadMoreImg)
    }

    fun addFooterView(view: View?) {
        if (adapter != null && view != null) {
            adapter!!.addFooterView(view)
        }
    }

    fun isLastVisible(position: Int): Boolean {
        if (mRecyclerView == null) return false
        val layoutManager = mRecyclerView!!.layoutManager ?: return false
        if (layoutManager is GridLayoutManager) {
            val lastCompleteVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
            return (lastCompleteVisibleItem == position || layoutManager.findLastVisibleItemPosition() == position
                    || layoutManager.findLastVisibleItemPosition() == position + 1)
        }
        if (layoutManager is LinearLayoutManager) {
            return (layoutManager.findFirstCompletelyVisibleItemPosition() == position || layoutManager.findLastVisibleItemPosition() == position
                    || layoutManager.findLastVisibleItemPosition() == position + 1)
        } else if (layoutManager is StaggeredGridLayoutManager) {
            var firstVisibleItems: IntArray? = null
            var lastVisibleItems: IntArray? = null
            firstVisibleItems = layoutManager.findFirstCompletelyVisibleItemPositions(firstVisibleItems)
            lastVisibleItems = layoutManager.findLastVisibleItemPositions(lastVisibleItems)
            if (firstVisibleItems != null && firstVisibleItems.size > 0 && lastVisibleItems != null && lastVisibleItems.size > 0) {
                return firstVisibleItems[0] == position || lastVisibleItems[0] == position
            }
        }
        return false
    }

    fun setFooterHintHeight(height: Int) {
        setPadding(0, -headerHeight, 0, -height)
    }

    private fun showReturnToBottomAnim() {
        if (mRecyclerView == null) return
        if (mFooter == null) return
        val totalPadding = mFooter!!.paddingBottom
        val ani = ValueAnimator.ofInt(0, mFooter!!.paddingBottom)
        ani.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            val value = animation.animatedValue as Int
            if (mRecyclerView != null) {
                if (mFooter == null) return@AnimatorUpdateListener
                mFooter!!.setPadding(0, mFooter!!.paddingTop, 0, totalPadding - value)
            }
        })

        ani.duration = 500
        ani.start()
    }

    fun onTouchUp() {
        if (marginTop < headerHeight) {
            ILog.e("===onTouchUp===", marginTop.toString() + " less than header height...")
            doOnRefreshEnd()
            return
        }
        if (refreshing || !canRefresh) {
            ILog.e("===scroll===", "onTouchUp can not reresh return ...")
            return
        }
        doRefresh()
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)
            handler!!.postDelayed(endRefresh, 10000)
        }
    }

    private fun updateMarginTop(value: Float) {
        ILog.e("===value===", "value is " + value)
        if (screenHeight < 0) screenHeight = CommonTools.getScreenHeight(mContext)
        val newValue = -value.toInt() * (screenHeight - marginTop - 2 * headerHeight) / screenHeight
        ILog.e("===value===", "newValue is " + newValue)
        marginTop += newValue
    }

    private fun paddingAndAnim() {
        mHeader!!.setPadding(0, marginTop, 0, mHeader!!.paddingBottom)
        var value = pullAnimImgsSize * marginTop / headerHeight
        value = if (value < 0) 0 else value
        if (value < pullAnimImgsSize) {
            refreshHeader!!.setImageResource(pullAnimImgs[value])
            return
        }
        ILog.e("===padding===", value)
        repeatAnim!!.visibility = View.VISIBLE
        refreshResult!!.visibility = View.GONE
        refreshHeader!!.visibility = View.INVISIBLE
        if (refreshAnimDrawable == null) {
            val drawable = repeatAnim!!.drawable
            if (drawable is AnimationDrawable) {
                refreshAnimDrawable = drawable
            }
        }
        if (refreshAnimDrawable != null && !refreshAnimDrawable!!.isRunning) {
            refreshAnimDrawable!!.start()
        }
    }

    //  判断是否是 没有更多数据且数据源不为空的场景。用于判断目前是否可以上滑
    private fun canPullUp(): Boolean {
        return !hasMore && dataInterface != null && !dataInterface!!.hasNoData() && isLastVisible(dataInterface!!.adapter!!.itemCount - 2)
    }

    private fun addTouchEvent() {
        val scrollUtils = ScrollUtils(mContext, scrollListener)
        mRecyclerView!!.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
                if (marginTop > 0 || isFirstVisible) {
                    scrollUtils.onScroll(event)
                } else if (canPullUp()) { //
                    //没数据且上拉的场景。
                    scrollUtils.onScroll(event)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
    }

    private fun showLoad(showAnim: Boolean) {
        if (mFooterLoading != null) {
            showFooter(showAnim)
        }
        if (showAnim && handler != null) {
            handler!!.postDelayed(loadMoreFailed, 10000)
        }
    }

    private var scrollDy = 0
    private var minShowGoTop = 0
    private fun checkGoTopVisible(dy: Int) {
        scrollDy += dy
        ILog.e("===scrollDy===", scrollDy)
        backTop?.visibility = if (minShowGoTop > scrollDy) View.GONE else View.VISIBLE
    }

    private fun addListener() {
        initHeader()
        initFooter()
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?,
                                              newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (dataInterface == null || adapter == null) return
                if (noLoadWhileScroll) {
                    adapter!!.scrollFinished = newState == RecyclerView.SCROLL_STATE_IDLE
                    if (adapter!!.scrollFinished) {
                        adapter!!.dataChanged()
                    }
                }
                if (hasMore && newState == RecyclerView.SCROLL_STATE_IDLE && adapter!!.isFooterVisible) {
                    showLoad(true)
                    dataInterface!!.loadMore()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                checkGoTopVisible(dy)
            }
        })
        addFooterView(mFooter)
        addHeaderView(mHeader)
        dataInterface?.addFooter()
        dataInterface?.addHeader()
        adapter!!.setRecyclerView(mRecyclerView!!)
        mRecyclerView!!.adapter = adapter
        addTouchEvent()
    }

    fun onDestroy() {
        if (dataInterface != null) {
            dataInterface!!.destroy()
            dataInterface = null
        }
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)
        }
        mHandler = null
        mContext = null
        mHeader = null
        mFooter = null
        mFooterLoading = null
        mRecyclerView = null
    }

    /**
     * 修改当前分页数，
     *
     * @param value 修改的数值，正为增加，负为减少
     */
    fun addPage(value: Int) {
        dataInterface!!.pageNo = dataInterface!!.pageNo + value
    }

    /**
     * 初始化入口
     *
     * @param context context
     * @param attrs   参数
     */
    private fun doInit(context: Context, attrs: AttributeSet?) {
        if (this.mContext != null) return
        this.mContext = context
        createRecyclerView()
    }

    constructor(context: Context) : super(context) {
        doInit(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        doInit(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        doInit(context, attrs)
    }

    companion object {
        private var headerHeight = -1
        private var screenHeight = -1
        private val TAG = "IRecyclerView"

        fun buildGridLayout(context: Context, num: Int, scrollHorizontally: Boolean, scrollVertically: Boolean): GridLayoutManager {
            return object : GridLayoutManager(context, num) {
                override fun canScrollHorizontally(): Boolean {
                    return scrollHorizontally
                }

                override fun canScrollVertically(): Boolean {
                    return scrollVertically
                }
            }
        }

        fun buildGridLayout(context: Context, num: Int): GridLayoutManager {
            return GridLayoutManager(context, num)
        }

        fun buildLinearLayout(context: Context?): LinearLayoutManager? {
            context ?: return null
            return LinearLayoutManager(context)
        }

        fun buildHorizontalLinearLayout(context: Context?): LinearLayoutManager {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            return layoutManager
        }

        private var pullAnimImgsSize = 21

        fun setPullAnimImgs(pullAnimImgs: IntArray) {
            var pullAnimImgs = pullAnimImgs
            pullAnimImgs = pullAnimImgs
            pullAnimImgsSize = pullAnimImgs.size
        }

        private val pullAnimImgs = intArrayOf(R.drawable.fast_android_dev_pull_00,
                R.drawable.fast_android_dev_pull_01, R.drawable.fast_android_dev_pull_02,
                R.drawable.fast_android_dev_pull_03, R.drawable.fast_android_dev_pull_04,
                R.drawable.fast_android_dev_pull_05, R.drawable.fast_android_dev_pull_06,
                R.drawable.fast_android_dev_pull_07, R.drawable.fast_android_dev_pull_08,
                R.drawable.fast_android_dev_pull_09, R.drawable.fast_android_dev_pull_10,
                R.drawable.fast_android_dev_pull_11, R.drawable.fast_android_dev_pull_12,
                R.drawable.fast_android_dev_pull_13, R.drawable.fast_android_dev_pull_14,
                R.drawable.fast_android_dev_pull_15, R.drawable.fast_android_dev_pull_16,
                R.drawable.fast_android_dev_pull_17, R.drawable.fast_android_dev_pull_18,
                R.drawable.fast_android_dev_pull_19, R.drawable.fast_android_dev_pull_20)
    }
}