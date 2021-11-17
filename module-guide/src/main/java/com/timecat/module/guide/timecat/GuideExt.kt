package com.timecat.module.guide.timecat

import android.app.Activity
import android.view.Window
import android.widget.FrameLayout

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
fun hideInActivity(activity: Activity, guideView: GuideView) {
    val window: Window = activity.window
    val frameLayout = window.decorView
    if (frameLayout is FrameLayout) {
        frameLayout.removeView(guideView)
    }
}

fun showInActivity(activity: Activity, guideView: GuideView) {
    (activity.window.decorView as FrameLayout).addView(guideView)
}
