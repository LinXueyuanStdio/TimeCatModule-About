package com.timecat.module.guide.onboarding

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OnBoardingPagerAdapter(private val onBoardingPageList: List<View>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder {
        val view = onBoardingPageList[p1]
        return PagerViewHolder(view)
    }

    override fun getItemCount() = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBindViewHolder()
    }

    override fun onViewDetachedFromWindow(holder: PagerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }

    override fun onViewAttachedToWindow(holder: PagerViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow()
    }
}

class PagerViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

    fun onBindViewHolder() {
        if (root is BoardingLifeCycleListener) {
            root.onBindViewHolder()
        }
    }

    fun onViewDetachedFromWindow() {
        if (root is BoardingLifeCycleListener) {
            root.onViewDetachedFromWindow()
        }
    }

    fun onViewAttachedToWindow() {
        if (root is BoardingLifeCycleListener) {
            root.onViewAttachedToWindow()
        }
    }
}