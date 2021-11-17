package com.timecat.module.guide.onboarding

import android.content.Context
import android.view.View
import com.timecat.module.guide.onboarding.entity.OnBoardingPage

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
interface OnBoardingService {
    fun buildOnBoardingView(
        context: Context,
        onBoardingPageList: List<OnBoardingPage>,
        callback: OnBoardingCallback
    ): View
}

interface OnBoardingCallback {
    fun onSkip()
    fun onFinish()
}