package com.same.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timecat.component.guide.api.OnBoardingCallback
import com.timecat.component.guide.api.OnBoardingPage
import com.timecat.component.guide.api.OnBoardingService
import com.timecat.component.router.app.NAV
import com.timecat.fake.welcome.R

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = NAV.service(OnBoardingService::class.java)
        val pages = listOf(
            OnBoardingPage(
                getString(R.string.onboarding_slide1_title),
                getString(R.string.onboarding_slide1_subtitle),
                getString(R.string.onboarding_slide1_desc),
                "R.drawable.ic_directions"
            ),
            OnBoardingPage(
                getString(R.string.onboarding_slide2_title),
                getString(R.string.onboarding_slide2_subtitle),
                getString(R.string.onboarding_slide2_desc),
                "R.drawable.ic_hang_out"
            ),
            OnBoardingPage(
                getString(R.string.onboarding_slide2_title),
                getString(R.string.onboarding_slide3_subtitle),
                getString(R.string.onboarding_slide1_desc),
                "R.drawable.ic_a_day_at_the_park"
            )
        )
        service?.buildOnBoardingView(
            this,
            pages,
            object : OnBoardingCallback {
                override fun onSkip() {
                    finish()
                }

                override fun onFinish() {
                    finish()
                }
            }
        )?.let {
            setContentView(it)
        }
    }
}
