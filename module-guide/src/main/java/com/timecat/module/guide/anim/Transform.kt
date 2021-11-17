package com.timecat.module.guide.anim

import android.view.View
import com.timecat.module.guide.R

fun setParallaxTransformation(page: View, position: Float){
    page.apply {
        val parallaxView = this.findViewById<View>(R.id.parallaxView)
        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                alpha = 1f
            position <= 1 -> { // [-1,1]
                parallaxView?.translationX = -position * (width / 2) //Half the normal speed
            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                alpha = 1f
        }
    }

}