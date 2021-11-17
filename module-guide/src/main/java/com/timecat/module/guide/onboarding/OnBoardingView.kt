package com.timecat.module.guide.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import com.timecat.module.guide.R
import com.timecat.module.guide.core.setParallaxTransformation
import com.timecat.module.guide.cover.OnBoardingPageView
import com.timecat.module.guide.onboarding.entity.OnBoardingPage

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

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.guide_view_onboarding, this, true)
        setUpSlider(view)
        addingButtonsClickListeners()
    }

    private fun setUpSlider(view: View) {
        mAdapter = OnBoardingPagerAdapter(listOf(
            OnBoardingPageView(context).apply {
                bind(OnBoardingPage.ONE)
            },
            OnBoardingPageView(context).apply {
                bind(OnBoardingPage.TWO)
            },
            OnBoardingPageView(context).apply {
                bind(OnBoardingPage.THREE)
            }
        ))
        with(slider) {
            adapter = mAdapter
            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }

            addSlideChangeListener()
            pageIndicator.setViewPager2(this)
        }
    }

    private fun addSlideChangeListener() {
        slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (numberOfPages > 1) {
                    val newProgress = (position + positionOffset) / (numberOfPages - 1)
                    onboardingRoot.progress = newProgress
                }
            }
        })
    }

    private fun addingButtonsClickListeners() {
        nextBtn.setOnClickListener {
            navigateToNextSlide()
        }
        skipBtn.setOnClickListener {
        }
        startBtn.setOnClickListener {
        }
    }

    private fun navigateToNextSlide() {
        val nextSlidePos: Int = slider.currentItem.plus(1) ?: 0
        slider.setCurrentItem(nextSlidePos, true)
    }
}