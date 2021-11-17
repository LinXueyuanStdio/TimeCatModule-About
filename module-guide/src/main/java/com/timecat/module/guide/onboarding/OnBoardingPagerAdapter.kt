package com.timecat.module.guide.onboarding

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timecat.module.guide.cover.OnBoardingPageView
import com.timecat.module.guide.onboarding.entity.OnBoardingPage

class OnBoardingPagerAdapter(private val onBoardingPageList: List<OnBoardingPage>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder {
        return PagerViewHolder(OnBoardingPageView(parent.context))
    }

    override fun getItemCount() = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])
    }
}

class PagerViewHolder(private val root: OnBoardingPageView) : RecyclerView.ViewHolder(root) {
    fun bind(onBoardingPage: OnBoardingPage) {
        root.bind(onBoardingPage)
    }
}