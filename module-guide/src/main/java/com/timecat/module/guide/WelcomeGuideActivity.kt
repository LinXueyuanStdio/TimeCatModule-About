package com.timecat.module.guide

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Button
import android.widget.TextView
import com.timecat.component.commonsdk.extension.beVisibleIf
import com.timecat.component.router.app.NAV
import com.timecat.component.setting.DEF
import com.timecat.element.alert.ToastUtil
import com.timecat.identity.readonly.RouterHub
import com.timecat.identity.service.UserService
import com.timecat.layout.ui.layout.setShakelessClickListener
import com.timecat.module.guide.timecat.*
import com.timecat.page.base.base.simple.BaseSimpleRxActivity
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.anno.ServiceAutowiredAnno

/**
 * 引导页面
 */
@RouterAnno(hostAndPath = RouterHub.WELCOME_WelcomeGuideActivity)
class WelcomeGuideActivity : BaseSimpleRxActivity(), GuideService, GuideListener {
    lateinit var mJumpBtn: TextView
    lateinit var mEnterBtn: Button
    lateinit var timecatGuideView: TimeCatGuideView

    @ServiceAutowiredAnno
    var userService: UserService? = null

    override fun layout(): Int = R.layout.guide_activity_guide

    override fun bindView() {
        mJumpBtn = findViewById(R.id.jump)
        timecatGuideView = findViewById(R.id.guideView)
        mEnterBtn = findViewById(R.id.enter_timecat)
        initView()
    }

    private fun initView() {
        timecatGuideView.guideService = this
        timecatGuideView.guideListener = this
        mEnterBtn.setShakelessClickListener {
            DEF.config().save(HAVE_READ_INTRODUCED, true)
            NAV.goAndFinish(this, RouterHub.WELCOME_PreSettingActivity)
        }
        mJumpBtn.setShakelessClickListener {
            ToastUtil.i(R.string.jump_toast)
            DEF.config().save(HAVE_READ_INTRODUCED, true)
            if (userService != null && userService!!.isLogin) {
                NAV.goAndFinish(this, RouterHub.MASTER_MainActivity)
            } else {
                NAV.goAndFinish(this, RouterHub.LOGIN_LoginActivity)
            }
        }
        mJumpBtn.beVisibleIf(DEF.config().getBoolean(HAVE_READ_INTRODUCED, false))
        timecatGuideView.onViewAttachedToWindow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NAV.inject(this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
    }

    public override fun onPause() {
        super.onPause()
        // 如果切换到后台，就设置下次不进入功能引导页
        DEF.config().save(HAVE_READ_INTRODUCED, true)
        finish()
    }

    private fun showEnterBtn() {
        if (mEnterBtn.visibility != View.VISIBLE) {
            mEnterBtn.visibility = View.VISIBLE
            mEnterBtn.scaleY = 0f
            mEnterBtn.scaleX = 0f
            mEnterBtn.alpha = 0f
            mEnterBtn.animate().scaleX(1f).scaleY(1f).alpha(1f)
                .setInterpolator(AnticipateOvershootInterpolator())
                .setStartDelay(500)
                .start()
        }
    }

    override fun onBackPressed() {
        timecatGuideView.onViewDetachedFromWindow()
        super.onBackPressed()
    }

    override fun onDestroy() {
        timecatGuideView.onViewDetachedFromWindow()
        super.onDestroy()
    }

    companion object {
        private const val HAVE_READ_INTRODUCED = "introduced"
    }

    override fun onHide(view: GuideView) {
        hideInActivity(this, view)
    }

    override fun onShow(view: GuideView) {
        showInActivity(this, view)
    }

    override fun onNextEnable(enable: Boolean) {
        if (enable) {
            showEnterBtn()
        }
    }

    override fun onNext() {
    }

    override fun onPrevEnable(enable: Boolean) {
    }

    override fun onPrev() {
    }
}