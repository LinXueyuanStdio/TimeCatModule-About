package com.timecat.module.guide.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import com.timecat.module.guide.R
import com.timecat.module.guide.anim.setParallaxTransformation
import com.timecat.component.guide.api.OnBoardingCallback
import com.timecat.component.guide.api.OnBoardingPage

class OnBoardingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val numberOfPages by lazy { mAdapter.itemCount }
    private val onboardingRoot: MotionLayout by lazy { findViewById<MotionLayout>(R.id.onboardingRoot) }
    private val slider: ViewPager2 by lazy { findViewById<ViewPager2>(R.id.slider) }
    private val guideline: Guideline by lazy { findViewById<Guideline>(R.id.guideline) }
    private val pageIndicator: WormDotsIndicator by lazy { findViewById<WormDotsIndicator>(R.id.page_indicator) }
    private val nextBtn: MaterialButton by lazy { findViewById<MaterialButton>(R.id.nextBtn) }
    private val startBtn: MaterialButton by lazy { findViewById<MaterialButton>(R.id.startBtn) }
    private val skipBtn: MaterialButton by lazy { findViewById<MaterialButton>(R.id.skipBtn) }
    lateinit var mAdapter: RecyclerView.Adapter<*>
    val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if (numberOfPages > 1) {
                val newProgress = (position + positionOffset) / (numberOfPages - 1)
                onboardingRoot.progress = newProgress
            }
        }
    }
    var callback: com.timecat.component.guide.api.OnBoardingCallback? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.guide_view_onboarding, this, true)
        addingButtonsClickListeners()
    }

    var onBoardingPageList: List<com.timecat.component.guide.api.OnBoardingPage> = listOf()
        set(value) {
            mAdapter = OnBoardingPagerAdapter(value)
            slider.adapter = mAdapter
            setUpSlider()
            field = value
        }

    private fun setUpSlider() {
        with(slider) {
            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }
            slider.unregisterOnPageChangeCallback(pagerCallback)
            slider.registerOnPageChangeCallback(pagerCallback)
            pageIndicator.setViewPager2(this)
        }
    }

    private fun addingButtonsClickListeners() {
        nextBtn.setOnClickListener {
            navigateToNextSlide()
        }
        skipBtn.setOnClickListener {
            callback?.onSkip()
        }
        startBtn.setOnClickListener {
            callback?.onFinish()
        }
    }

    private fun navigateToNextSlide() {
        val nextSlidePos: Int = slider.currentItem.plus(1) ?: 0
        slider.setCurrentItem(nextSlidePos, true)
    }
}