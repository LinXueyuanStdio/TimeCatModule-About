package com.erkutaras.showcaseview

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.view.View
import java.util.*

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
fun showInActivity(
    activity: Activity,
    showcaseModelList: ArrayList<out Parcelable>,
    key: String,
    systemUiVisibility: Boolean = getSystemUiVisibility(activity)
) {
    val intent = Intent(activity, ShowcaseActivity::class.java)
    intent.putParcelableArrayListExtra(ShowcaseActivity.EXTRAS_SHOWCASES, showcaseModelList)
    intent.putExtra(ShowcaseActivity.EXTRAS_SYSTEM_UI_VISIBILITY, systemUiVisibility)
    activity.startActivityForResult(intent, ShowcaseManager.REQUEST_CODE_SHOWCASE)
    ShowcaseUtils.ShowcaseSP.instance(activity).show(key)
}

fun getSystemUiVisibility(activity: Activity): Boolean {
    val window = activity.window
    val decorView = window.decorView
    return decorView.systemUiVisibility == View.VISIBLE
}