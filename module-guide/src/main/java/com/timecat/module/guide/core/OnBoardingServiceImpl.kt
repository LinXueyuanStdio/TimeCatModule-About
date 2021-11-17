package com.timecat.module.guide.core

import android.content.Context
import android.view.View
import com.timecat.module.guide.onboarding.OnBoardingView
import com.xiaojinzi.component.anno.ServiceAnno

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
@ServiceAnno
class OnBoardingServiceImpl : OnBoardingService {
    override fun buildOnBoardingView(context: Context, onBoardingPageList: List<OnBoardingPage>, callback: OnBoardingCallback): View {
        val view = OnBoardingView(context)
        view.onBoardingPageList = onBoardingPageList
        view.callback = callback
        return view
    }
}