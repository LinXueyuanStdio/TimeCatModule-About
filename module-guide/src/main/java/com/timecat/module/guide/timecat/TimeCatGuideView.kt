package com.timecat.module.guide.timecat

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import com.afollestad.vvalidator.util.show
import com.timecat.component.commonsdk.utils.clipboard.ClipboardUtils
import com.timecat.element.alert.SnackBarUtil
import com.timecat.element.alert.ToastUtil
import com.timecat.layout.ui.business.timecat.TimeCatLayoutWrapper
import com.timecat.module.guide.R
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
class TimeCatGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val mIntro: AppCompatTextView by lazy { findViewById(R.id.intro) }
    private val mTimeCatWraper: CardView by lazy { findViewById(R.id.timecat_wraper) }
    private val mTimeCatLayout: TimeCatLayoutWrapper by lazy { findViewById(R.id.timecat_wrap) }
    private val mFunctionIntroTV: AppCompatTextView by lazy { findViewById(R.id.enter_timecat_intro) }
    var guideListener: GuideListener? = null
    var guideService: GuideService? = null

    private lateinit var guideView: GuideView
    var clickTimes = 0
    private val txts_cloud: Array<String> = arrayOf(
        "TimeCat", "是", "您", "的", "快捷", "助手", "。", "\n", "您", "可以", "在", "任意",
        "app", "中", "对", "文字", "进行", "编辑", "，", "包括", "分词", "，", "翻译", "，", "复制", "以及", "动态", "调整",
        "。", "\n", "希望", "您", "能", "在", "日常", "生活", "中", "获得", "便利"
    )
    private val txts_local: Array<String> = arrayOf(
        "TimeCat", "是", "您", "的", "快", "捷", "助", "手", "。", "\n", "您", "可",
        "以", "在", "任", "意", "app", "中", "对", "文", "字", "进", "行", "编", "辑", "，", "包", "括", "分", "词",
        "，", "翻", "译", "，", "复", "制", "以", "及", "动", "态", "调", "整", "。", "\n", "希", "望", "您", "能",
        "在", "日", "常", "生", "活", "中", "获", "得", "便", "利"
    )
    var timeCatActionListener: TimeCatLayoutWrapper.ActionListener = object : TimeCatLayoutWrapper.ActionListener {
        private var firstSelected = true
        private val firstSearch = true
        private val firstShare = true
        private val firstCopy = true
        private val firstTrans = true
        private var firstDrag = true
        private val firstAddTask = true
        override fun onSelected(text: String) {
            if (firstSelected) {
                guideView!!.performClick()
                firstSelected = false
            }
        }

        override fun onSearch(text: String) {
            if (firstSearch) {
                mFunctionIntroTV.setScaleY(0f)
                mFunctionIntroTV.setScaleX(0f)
                mFunctionIntroTV.setText(R.string.search_mode_help)
                mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            } else {
                try {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.baidu.com/s?wd=" + URLEncoder.encode(text, "utf-8"))
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
            }
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onShare(text: String) {
            if (firstShare) {
                mFunctionIntroTV.setScaleY(0f)
                mFunctionIntroTV.setScaleX(0f)
                mFunctionIntroTV.setText(R.string.share_mode_help)
                mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            } else {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
                context.startActivity(sharingIntent)
            }
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onCopy(text: String) {
            if (firstCopy) {
                mFunctionIntroTV.setScaleY(0f)
                mFunctionIntroTV.setScaleX(0f)
                mFunctionIntroTV.setText(R.string.copy_mode_help)
                mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            } else {
                if (!TextUtils.isEmpty(text)) {
                    ClipboardUtils.setText(context, text)
                    ToastUtil.ok(R.string.copyed)
                }
            }
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onTrans(text: String) {
            if (firstTrans) {
                mFunctionIntroTV.setScaleY(0f)
                mFunctionIntroTV.setScaleX(0f)
                mFunctionIntroTV.setText(R.string.translate_mode_help)
                mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            } else {
                if (!TextUtils.isEmpty(text)) {
                    SnackBarUtil.show(mIntro, R.string.open_timecat_for_translate)
                }
            }
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onAddTask(text: String) {
            if (firstAddTask) {
                mFunctionIntroTV.setScaleY(0f)
                mFunctionIntroTV.setScaleX(0f)
                mFunctionIntroTV.setText(R.string.add_task_mode_help)
                mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            } else {
                if (!TextUtils.isEmpty(text)) {
                    SnackBarUtil.show(mIntro, context.getString(R.string.open_timecat_for_task))
                }
            }
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onDrag() {
            if (firstDrag) {
                mFunctionIntroTV.setText(R.string.sort_mode_help)
            } else {
                mFunctionIntroTV.setText(R.string.choose_sentences_mode)
            }
            firstDrag = !firstDrag
            mFunctionIntroTV.setScaleY(0f)
            mFunctionIntroTV.setScaleX(0f)
            mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onSwitchType(isLocal: Boolean) {
            mTimeCatLayout.reset()
            if (isLocal) {
                for (text in txts_local) {
                    mTimeCatLayout.addTextItem(text)
                }
                mFunctionIntroTV.setText(R.string.word_type_local)
            } else {
                for (text in txts_cloud) {
                    mTimeCatLayout.addTextItem(text)
                }
                mFunctionIntroTV.setText(R.string.word_type_cloud)
            }
            firstDrag = !firstDrag
            mFunctionIntroTV.setScaleY(0f)
            mFunctionIntroTV.setScaleX(0f)
            mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onSwitchSymbol(isShow: Boolean) {
            mFunctionIntroTV.setText(R.string.show_symbol)
            mFunctionIntroTV.setScaleY(0f)
            mFunctionIntroTV.setScaleX(0f)
            mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onSwitchSection(isShow: Boolean) {
            mFunctionIntroTV.setText(R.string.show_section)
            mFunctionIntroTV.setScaleY(0f)
            mFunctionIntroTV.setScaleX(0f)
            mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            clickTimes++
            guideListener?.onNextEnable()
        }

        override fun onDragSelection() {
            mFunctionIntroTV.setText(R.string.show_drag_selection)
            mFunctionIntroTV.setScaleY(0f)
            mFunctionIntroTV.setScaleX(0f)
            mFunctionIntroTV.animate().scaleY(1f).scaleX(1f).start()
            clickTimes++
            guideListener?.onNextEnable()
        }
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.guide_view_timecat, this, true)
    }

    private fun showClickIntro() {
        val tv = TextView(context)
        tv.setText(R.string.try_long_click_text)
        tv.setTextColor(resources.getColor(R.color.white))
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.hand_down)
        val animation = AnimationUtils.loadAnimation(context, R.anim.click_here_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                postDelayed({ imageView.startAnimation(animation) }, 500)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        guideView = GuideView.Builder(context)
            .setTargetView(mIntro) //设置目标
            .setCustomGuideView(tv)
            .setCenterView(imageView)
            .setDirction(GuideView.Direction.BOTTOM)
            .setShape(GuideView.MyShape.CIRCULAR) // 设置圆形显示区域，
            .setOffset(0, mIntro.measuredHeight + 100)
            .setBgColor(resources.getColor(R.color.shadow))
            .setOnclickListener(object : GuideView.OnClickCallback {
                override fun onClickedGuideView() {
                    animation.cancel()
                    guideView.hide {
                        guideService?.onHide(it)
                    }
                    mIntro.visibility = GONE
                    mTimeCatWraper.setVisibility(VISIBLE)
                    mTimeCatWraper.setScaleX(0f)
                    mTimeCatWraper.setScaleY(0f)
                    mTimeCatWraper.animate().scaleY(1f).scaleX(1f)
                        .setInterpolator(AnticipateOvershootInterpolator()).setDuration(200)
                        .setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {}
                            override fun onAnimationEnd(animation: Animator) {
                                postDelayed({ showTimeCatIntro() }, 300)
                            }

                            override fun onAnimationCancel(animation: Animator) {}
                            override fun onAnimationRepeat(animation: Animator) {}
                        }).start()
                }
            })
            .setOnViewAddedListener(object : GuideView.OnViewAddedListener {
                override fun viewAdded(view: View) {
                    view.animation = animation
                    animation.start()
                }
            }).build {
                guideService?.onHide(it)
            }
        guideView.setClickable(false)
        guideView.setLongClickable(false)
        guideView.setFocusable(false)
        guideView.show()
    }

    private fun initView() {
        mTimeCatLayout.setActionListener(timeCatActionListener)

        for (t in txts_cloud) {
            mTimeCatLayout.addTextItem(t)
        }
        mIntro.setOnLongClickListener { v: View? ->
            guideView.performClick()
            true
        }
    }

    private fun showTimeCatIntro() {
        val tv = TextView(context)
        tv.setText(R.string.try_click_text)
        tv.setTextColor(resources.getColor(R.color.white))
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.hand_swipe)
        val animation = AnimationUtils.loadAnimation(context, R.anim.swipe_here_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                postDelayed({ imageView.startAnimation(animation) }, 500)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        guideView = GuideView.Builder(context)
            .setTargetView(mTimeCatWraper) //设置目标
            .setCustomGuideView(tv)
            .setCenterView(imageView)
            .setDirction(GuideView.Direction.BOTTOM)
            .setShape(GuideView.MyShape.RECTANGULAR) // 设置圆形显示区域，
            .setRadius(5)
            .setOffset(0, mTimeCatWraper.getMeasuredHeight() / 2 + 100)
            .setBgColor(resources.getColor(R.color.shadow))
            .setOnclickListener(object : GuideView.OnClickCallback {
                override fun onClickedGuideView() {
                    animation.cancel()
                    guideView.hide {
                        guideService?.onHide(it)
                    }
                    guideListener?.onNextEnable()
                    mFunctionIntroTV.visibility = VISIBLE
                }
            })
            .setOnViewAddedListener(object : GuideView.OnViewAddedListener {
                override fun viewAdded(view: View) {
                    view.animation = animation
                    animation.start()
                }
            }).build {
                guideService?.onHide(it)
            }
        guideView.setClickable(false)
        guideView.setLongClickable(false)
        guideView.setFocusable(false)
        guideView.show()
    }

}