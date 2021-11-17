package com.timecat.module.guide.cover

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.timecat.layout.ui.layout.layout_height
import com.timecat.layout.ui.layout.layout_width
import com.timecat.layout.ui.layout.match_parent
import com.timecat.layout.ui.utils.IconLoader
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
    private val parallaxView: ImageView by lazy { findViewById(R.id.parallaxView) }
    private val descTV: TextView by lazy { findViewById(R.id.descTV) }

    init {
        inflate(context, R.layout.guide_onboarding_page_item, this)
        layout_width = match_parent
        layout_height = match_parent
    }

    fun bind(onBoardingPage: OnBoardingPage) {
        titleTv.text = onBoardingPage.titleResource
        subTitleTv.text = onBoardingPage.subTitleResource
        descTV.text = onBoardingPage.descriptionResource
        IconLoader.loadIcon(context, parallaxView, onBoardingPage.logoResource)
    }
}