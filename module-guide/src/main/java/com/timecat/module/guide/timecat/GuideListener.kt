package com.timecat.module.guide.timecat

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
interface GuideListener {
    fun onNextEnable(enable: Boolean = true)
    fun onNext()
    fun onPrevEnable(enable: Boolean = true)
    fun onPrev()
}