package com.timecat.module.guide.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timecat.module.guide.R
import com.timecat.module.guide.onboarding.entity.OnBoardingPage

class OnBoardingPagerAdapter(private val onBoardingPageList: Array<OnBoardingPage> = OnBoardingPage.values()) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder {
        return PagerViewHolder.create(parent)
    }

    override fun getItemCount() = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])
    }
}

class PagerViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    private val titleTv: TextView by lazy { root.findViewById<TextView>(R.id.titleTv) }
    private val subTitleTv: TextView by lazy { root.findViewById<TextView>(R.id.subTitleTv) }
    private val img: ImageView by lazy { root.findViewById<ImageView>(R.id.img) }
    private val descTV: TextView by lazy { root.findViewById<TextView>(R.id.descTV) }

    fun bind(onBoardingPage: OnBoardingPage) {
        val res = root.context.resources
        titleTv.text = res.getString(onBoardingPage.titleResource)
        subTitleTv.text = res.getString(onBoardingPage.subTitleResource)
        descTV.text = res.getString(onBoardingPage.descriptionResource)
        img.setImageResource(onBoardingPage.logoResource)
    }

    companion object {
        fun create(parent: ViewGroup): PagerViewHolder {
            return LayoutInflater.from(parent.context).inflate(
                R.layout.guide_onboarding_page_item, parent, false
            ).let { PagerViewHolder(it) }
        }
    }
}