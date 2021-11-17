package com.same.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timecat.module.guide.R
import com.timecat.component.guide.api.OnBoardingCallback
import com.timecat.module.guide.onboarding.OnBoardingView
import com.timecat.component.guide.api.OnBoardingPage

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guide_onboarding_activity)
        val view = findViewById<OnBoardingView>(R.id.container)

        val pages = listOf(
            com.timecat.component.guide.api.OnBoardingPage(
                getString(R.string.onboarding_slide1_title),
                getString(R.string.onboarding_slide1_subtitle),
                getString(R.string.onboarding_slide1_desc),
                "R.drawable.ic_directions"
            ),
            com.timecat.component.guide.api.OnBoardingPage(
                getString(R.string.onboarding_slide2_title),
                getString(R.string.onboarding_slide2_subtitle),
                getString(R.string.onboarding_slide2_desc),
                "R.drawable.ic_hang_out"
            ),
            com.timecat.component.guide.api.OnBoardingPage(
                getString(R.string.onboarding_slide2_title),
                getString(R.string.onboarding_slide3_subtitle),
                getString(R.string.onboarding_slide1_desc),
                "R.drawable.ic_a_day_at_the_park"
            )
        )
        view.onBoardingPageList = pages
        view.callback = object : com.timecat.component.guide.api.OnBoardingCallback {
            override fun onSkip() {
                finish()
            }

            override fun onFinish() {
                finish()
            }
        }
    }
}
