package com.timecat.module.guide.core

import android.content.Context
import android.view.View
import com.timecat.module.guide.onboarding.OnBoardingView

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
class OnBoardingServiceImpl : com.timecat.component.guide.api.OnBoardingService {
    override fun buildOnBoardingView(context: Context, onBoardingPageList: List<com.timecat.component.guide.api.OnBoardingPage>, callback: com.timecat.component.guide.api.OnBoardingCallback): View {
        val view = OnBoardingView(context)
        view.onBoardingPageList = onBoardingPageList
        view.callback = callback
        return view
    }
}