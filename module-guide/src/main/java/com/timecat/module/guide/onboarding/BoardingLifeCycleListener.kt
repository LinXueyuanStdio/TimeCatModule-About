package com.timecat.module.guide.onboarding

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
interface BoardingLifeCycleListener {
    /**
     * 选中
     */
    fun onSelected()
    /**
     * 该页滑走
     */
    fun onDeselected()

    fun onViewAttachedToWindow()
    fun onViewDetachedFromWindow()
    fun onBindViewHolder()
}