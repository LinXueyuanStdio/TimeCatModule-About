package com.timecat.module.guide.cover

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.timecat.module.guide.R
import com.timecat.module.guide.onboarding.entity.OnBoardingPage

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
class OnBoardingPageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val titleTv: TextView by lazy { findViewById(R.id.titleTv) }
    private val subTitleTv: TextView by lazy { findViewById(R.id.subTitleTv) }
    private val img: ImageView by lazy { findViewById(R.id.img) }
    private val descTV: TextView by lazy { findViewById(R.id.descTV) }

    init {
        inflate(context, R.layout.guide_onboarding_page_item, this)
    }

    fun bind(onBoardingPage: OnBoardingPage) {
        val res = context.resources
        titleTv.text = res.getString(onBoardingPage.titleResource)
        subTitleTv.text = res.getString(onBoardingPage.subTitleResource)
        descTV.text = res.getString(onBoardingPage.descriptionResource)
        img.setImageResource(onBoardingPage.logoResource)
    }
}