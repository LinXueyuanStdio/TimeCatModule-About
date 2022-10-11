package com.timecat.module.guide.timecat

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.RelativeLayout

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
class GuideView(private var mContent: Context?) : RelativeLayout(mContent), OnGlobalLayoutListener {
    private val TAG = javaClass.simpleName

    /**
     * 相对于targetView的位置.在target的那个方向
     */
    private var direction: Direction? = null

    /**
     * 形状
     */
    var myShape: MyShape? = null
    var needDraw = true
    private val mViews: List<View>? = null
    private var first = true

    /**
     * GuideView 偏移量
     */
    private var offsetX = 0
    private var offsetY = 0

    /**
     * targetView 的外切圆半径
     */
    var radius = 0

    /**
     * 需要显示提示信息的View
     */
    private var targetView: View? = null

    /**
     * 自定义View
     */
    private var customGuideView: View? = null

    /**
     * 透明圆形画笔
     */
    private var mCirclePaint: Paint? = null

    /**
     * 背景色画笔
     */
    private var mBackgroundPaint: Paint? = null

    /**
     * targetView是否已测量
     */
    private var isMeasured = false

    /**
     * targetView圆心
     */
    var center: IntArray = IntArray(2)

    /**
     * 绘图层叠模式
     */
    private var porterDuffXfermode: PorterDuffXfermode? = null

    /**
     * 绘制前景bitmap
     */
    private lateinit var bitmap: Bitmap

    /**
     * 背景色和透明度，格式 #aarrggbb
     */
    private var bgColor = 0

    /**
     * Canvas,绘制bitmap
     */
    private var temp: Canvas? = null
    private var centerView: View? = null

    /**
     * targetView左上角坐标
     */
    lateinit var location: IntArray
    private var onClickExit = false
    private var onLongClickExit = false
    private var onclickListener: OnClickCallback? = null
    private var onLongclickListener: OnLongClickCallback? = null
    private var onViewAddedListener: OnViewAddedListener? = null
    private val guideViewLayout: RelativeLayout? = null
    private var builder: Builder? = null
    fun restoreState() {
        Log.v(TAG, "restoreState")
        offsetY = 0
        offsetX = offsetY
        radius = 0
        mCirclePaint = null
        mBackgroundPaint = null
        isMeasured = false
        porterDuffXfermode = null
        needDraw = true
//        backgroundColor = Color.parseColor("#00000000");
        temp = null
//        direction = null;
    }

    fun setOffsetX(offsetX: Int) {
        this.offsetX = offsetX
    }

    fun setOffsetY(offsetY: Int) {
        this.offsetY = offsetY
    }

    fun setShape(shape: MyShape?) {
        myShape = shape
    }

    fun setCustomGuideView(customGuideView: View?) {
        this.customGuideView = customGuideView
        if (!first) {
            restoreState()
        }
    }

    fun setDirection(dir: Direction) {
        direction = dir
    }

    fun setBgColor(background_color: Int) {
        bgColor = background_color
    }

    fun getTargetView(): View? {
        return targetView
    }

    fun setTargetView(targetView: View) {
        this.targetView = targetView
        //        restoreState();
        if (!first) {
            //            guideViewLayout.removeAllViews();
        }
    }

    private fun init() {}
    fun showOnce() {
        if (targetView != null) {
            mContent!!.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putBoolean(generateUniqId(targetView!!), true).apply()
        }
    }

    private fun hasShown(): Boolean {
        return if (targetView == null) {
            true
        } else mContent!!.getSharedPreferences(TAG, Context.MODE_PRIVATE).getBoolean(generateUniqId(targetView!!), false)
    }

    private fun generateUniqId(v: View): String {
        return SHOW_GUIDE_PREFIX + v.id
    }

    fun hide(onHide: (GuideView) -> Unit) {
        Log.v(TAG, "hide")
        if (customGuideView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                targetView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
            removeAllViews()
            try {
                onHide(this)
                restoreState()
                builder = null
                mContent = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun show(onShow: (GuideView) -> Unit) {
        Log.v(TAG, "show")
        if (hasShown()) {
            return
        }
        if (targetView != null) {
            targetView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
        }
        setBackgroundColor(Color.TRANSPARENT)
        onShow(this)
        first = false
    }

    /**
     * 添加提示文字，位置在targetView的下边
     * 在屏幕窗口，添加蒙层，蒙层绘制总背景和透明圆形，圆形下边绘制说明文字
     */
    private fun createGuideView() {
        Log.v(TAG, "createGuideView")

        // 添加到蒙层
        //        if (guideViewLayout == null) {
        //            guideViewLayout = new RelativeLayout(mContent);
        //        }

        // Tips布局参数
        var guideViewParams: LayoutParams
        guideViewParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        guideViewParams.setMargins(0, center[1] + radius + 10, 0, 0)
        if (customGuideView != null) {
            //            LayoutParams guideViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (direction != null) {
                val width = this.width
                val height = this.height
                val left = center[0] - radius
                val right = center[0] + radius
                val top = center[1] - radius
                val bottom = center[1] + radius
                when (direction) {
                    Direction.TOP -> {
                        this.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                        guideViewParams.setMargins(offsetX, offsetY - height + top, -offsetX, height - top - offsetY)
                    }
                    Direction.LEFT -> {
                        this.gravity = Gravity.RIGHT
                        guideViewParams.setMargins(offsetX - width + left, top + offsetY, width - left - offsetX, -top - offsetY)
                    }
                    Direction.BOTTOM -> {
                        this.gravity = Gravity.CENTER_HORIZONTAL
                        guideViewParams.setMargins(offsetX, bottom + offsetY, -offsetX, -bottom - offsetY)
                    }
                    Direction.RIGHT -> guideViewParams.setMargins(right + offsetX, top + offsetY, -right - offsetX, -top - offsetY)
                    Direction.LEFT_TOP -> {
                        this.gravity = Gravity.RIGHT or Gravity.BOTTOM
                        guideViewParams.setMargins(offsetX - width + left, offsetY - height + top, width - left - offsetX, height - top - offsetY)
                    }
                    Direction.LEFT_BOTTOM -> {
                        this.gravity = Gravity.RIGHT
                        guideViewParams.setMargins(offsetX - width + left, bottom + offsetY, width - left - offsetX, -bottom - offsetY)
                    }
                    Direction.RIGHT_TOP -> {
                        this.gravity = Gravity.BOTTOM
                        guideViewParams.setMargins(right + offsetX, offsetY - height + top, -right - offsetX, height - top - offsetY)
                    }
                    Direction.RIGHT_BOTTOM -> guideViewParams.setMargins(right + offsetX, bottom + offsetY, -right - offsetX, -top - offsetY)
                    null -> {}
                }
            } else {
                guideViewParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                guideViewParams.setMargins(offsetX, offsetY, -offsetX, -offsetY)
            }
            //            guideViewLayout.addView(customGuideView);
            this.addView(customGuideView, guideViewParams)
        }
        if (centerView != null) {
            val centerViewParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            centerViewParams.setMargins(0, center[1], 0, 0)
            if (centerView!!.parent != null) {
                (centerView!!.parent as ViewGroup).removeView(centerView)
            }
            this.addView(centerView, centerViewParams)
            centerView?.let{
                onViewAddedListener?.viewAdded(it)
            }
        }
    }

    /**
     * 获得targetView 的宽高，如果未测量，返回｛-1， -1｝
     *
     * @return
     */
    private val targetViewSize: IntArray
        private get() {
            val location = intArrayOf(-1, -1)
            if (isMeasured) {
                location[0] = targetView!!.width
                location[1] = targetView!!.height
            }
            return location
        }

    /**
     * 获得targetView 的半径
     *
     * @return
     */
    private val targetViewRadius: Int
        private get() {
            if (isMeasured) {
                val size = targetViewSize
                val x = size[0]
                val y = size[1]
                return (Math.sqrt((x * x + y * y).toDouble()) / 2).toInt()
            }
            return -1
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.v(TAG, "onDraw")
        if (!isMeasured) {
            return
        }
        if (targetView == null) {
            return
        }

        //        if (!needDraw) return;
        drawBackground(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        Log.v(TAG, "drawBackground")
        needDraw = false
        // 先绘制bitmap，再将bitmap绘制到屏幕
        bitmap = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        temp = Canvas(bitmap)

        // 背景画笔
        val bgPaint = Paint()
        if (bgColor != 0) {
            bgPaint.color = bgColor
        } else {
            bgPaint.color = Color.parseColor("#aa000000")
        }

        // 绘制屏幕背景
        temp!!.drawRect(0f, 0f, temp!!.width.toFloat(), temp!!.height.toFloat(), bgPaint)

        // targetView 的透明圆形画笔
        if (mCirclePaint == null) {
            mCirclePaint = Paint()
        }
        porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT) // 或者CLEAR
        mCirclePaint!!.xfermode = porterDuffXfermode
        mCirclePaint!!.isAntiAlias = true
        if (myShape != null) {
            val oval = RectF()
            when (myShape) {
                MyShape.CIRCULAR -> temp!!.drawCircle(center[0].toFloat(), center[1].toFloat(), radius.toFloat(), mCirclePaint!!) //绘制圆形
                MyShape.ELLIPSE -> {
                    //RectF对象
                    oval.left = (center[0] - 150).toFloat() //左边
                    oval.top = (center[1] - 50).toFloat() //上边
                    oval.right = (center[0] + 150).toFloat() //右边
                    oval.bottom = (center[1] + 50).toFloat() //下边
                    temp!!.drawOval(oval, mCirclePaint!!) //绘制椭圆
                }
                MyShape.RECTANGULAR -> {
                    val target = targetViewSize
                    //RectF对象
                    oval.left = (center[0] - target[0] / 2).toFloat() //左边
                    oval.top = (center[1] - target[1] / 2).toFloat() //上边
                    oval.right = (center[0] + target[0] / 2).toFloat() //右边
                    oval.bottom = (center[1] + target[1] / 2).toFloat() //下边
                    temp!!.drawRoundRect(oval, radius.toFloat(), radius.toFloat(), mCirclePaint!!) //绘制圆角矩形
                }
                null -> {}
            }
        } else {
            temp!!.drawCircle(center[0].toFloat(), center[1].toFloat(), radius.toFloat(), mCirclePaint!!) //绘制圆形
        }

        // 绘制到屏幕
        canvas.drawBitmap(bitmap, 0f, 0f, bgPaint)
        bitmap.recycle()
    }

    fun setOnClickExit(onClickExit: Boolean) {
        this.onClickExit = onClickExit
    }

    fun setOnLongClickExit(onLongClickExit: Boolean) {
        this.onLongClickExit = onLongClickExit
    }

    fun setOnclickListener(onclickListener: OnClickCallback?) {
        this.onclickListener = onclickListener
    }

    fun setOnLongclickListener(onLongclickListener: OnLongClickCallback?) {
        this.onLongclickListener = onLongclickListener
    }

    fun setOnViewAddedListener(onViewAddedListener: OnViewAddedListener?) {
        this.onViewAddedListener = onViewAddedListener
    }

    private fun setClickInfo(onHide: (GuideView) -> Unit) {
        val exit = onClickExit
        setOnClickListener {
            if (onclickListener != null) {
                onclickListener!!.onClickedGuideView()
            }
            if (exit) {
                hide(onHide)
            }
        }
        val exit1 = onLongClickExit
        setOnLongClickListener {
            var t = false
            if (onLongclickListener != null) {
                onLongclickListener!!.onLongClickedGuideView()
                t = true
            }
            if (exit1) {
                hide(onHide)
            }
            t
        }
    }

    override fun onGlobalLayout() {
        if (isMeasured) {
            return
        }
        if (targetView!!.height > 0 && targetView!!.width > 0) {
            isMeasured = true
        }

        // 获取targetView的中心坐标
        // 获取右上角坐标
        location = IntArray(2)
        targetView!!.getLocationInWindow(location)
        center = IntArray(2)
        // 获取中心坐标
        center[0] = location[0] + targetView!!.width / 2
        center[1] = location[1] + targetView!!.height / 2
        // 获取targetView外切圆半径
        if (radius == 0) {
            radius = targetViewRadius
        }
        // 添加GuideView
        createGuideView()
    }

    fun setCenterView(conterView: ImageView) {
        centerView = conterView
    }

    fun destory() {}
    fun setBuilder(builder: Builder?) {
        this.builder = builder
    }

    /**
     * 定义GuideView相对于targetView的方位，共八种。不设置则默认在targetView下方
     */
    enum class Direction {
        LEFT, TOP, RIGHT, BOTTOM, LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 定义目标控件的形状，共3种。圆形，椭圆，带圆角的矩形（可以设置圆角大小），不设置则默认是圆形
     */
    enum class MyShape {
        CIRCULAR, ELLIPSE, RECTANGULAR
    }

    /**
     * GuideView点击Callback
     */
    interface OnClickCallback {
        fun onClickedGuideView()
    }

    /**
     * GuideView长按Callback
     */
    interface OnLongClickCallback {
        fun onLongClickedGuideView()
    }

    interface OnViewAddedListener {
        fun viewAdded(view: View)
    }

    class Builder(context: Context) {
        var guiderView: GuideView = GuideView(context)
        fun setTargetView(target: View): Builder {
            guiderView.setTargetView(target)
            return this
        }

        fun setBgColor(color: Int): Builder {
            guiderView.setBgColor(color)
            return this
        }

        fun setDirction(dir: Direction): Builder {
            guiderView.setDirection(dir)
            return this
        }

        fun setShape(shape: MyShape?): Builder {
            guiderView.setShape(shape)
            return this
        }

        fun setOffset(x: Int, y: Int): Builder {
            guiderView.setOffsetX(x)
            guiderView.setOffsetY(y)
            return this
        }

        fun setRadius(radius: Int): Builder {
            guiderView.radius = radius
            return this
        }

        fun setCustomGuideView(view: View?): Builder {
            guiderView.setCustomGuideView(view)
            return this
        }

        fun setCenter(X: Int, Y: Int): Builder {
            guiderView.center = intArrayOf(X, Y)
            return this
        }

        fun showOnce(): Builder {
            guiderView.showOnce()
            return this
        }

        fun build(onHide: (GuideView) -> Unit): GuideView {
            guiderView.setClickInfo(onHide)
            return guiderView
        }

        fun setOnclickExit(onclickExit: Boolean): Builder {
            guiderView.setOnClickExit(onclickExit)
            return this
        }

        fun setOnLongClickExit(onLongClickExit: Boolean): Builder {
            guiderView.setOnLongClickExit(onLongClickExit)
            return this
        }

        fun setOnclickListener(callback: OnClickCallback?): Builder {
            guiderView.setOnclickListener(callback)
            return this
        }

        fun setOnLongclickListener(callback: OnLongClickCallback?): Builder {
            guiderView.setOnLongclickListener(callback)
            return this
        }

        fun setOnViewAddedListener(listener: OnViewAddedListener?): Builder {
            guiderView.setOnViewAddedListener(listener)
            return this
        }

        fun setCenterView(centerView: ImageView): Builder {
            guiderView.setCenterView(centerView)
            return this
        }

        init {
            guiderView.setBuilder(this)
        }
    }

    companion object {
        /**
         * targetView前缀。SHOW_GUIDE_PREFIX + targetView.getId()作为保存在SP文件的key。
         */
        private const val SHOW_GUIDE_PREFIX = "show_guide_on_view_"
    }

    init {
        init()
    }
}