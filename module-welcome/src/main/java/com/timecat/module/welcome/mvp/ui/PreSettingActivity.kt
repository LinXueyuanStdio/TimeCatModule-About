package com.timecat.module.welcome.mvp.ui

import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.CheckBox
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.timecat.component.commonsdk.utils.LetMeKnow
import com.timecat.component.commonsdk.utils.NotificationCheckUtil
import com.timecat.component.commonsdk.utils.clipboard.ClipboardUtils
import com.timecat.component.commonsdk.utils.override.LogUtil
import com.timecat.component.identity.Attr
import com.timecat.component.router.app.NAV
import com.timecat.component.setting.DEF
import com.timecat.element.alert.SnackBarUtil
import com.timecat.identity.anim.AnimatorUtils
import com.timecat.identity.readonly.Constants
import com.timecat.identity.readonly.RouterHub
import com.timecat.layout.ui.layout.setShakelessClickListener
import com.timecat.layout.ui.standard.ColorTextView
import com.timecat.module.welcome.R
import com.timecat.page.base.base.simple.BaseSimpleRxActivity
import com.xiaojinzi.component.anno.RouterAnno

/**
 * 欢迎页面，启动页
 */
@RouterAnno(hostAndPath = RouterHub.WELCOME_PreSettingActivity)
class PreSettingActivity : BaseSimpleRxActivity() {

    private lateinit var controlByFloat: CheckBox
    private lateinit var controlByNotify: CheckBox
    private lateinit var triggerByFloat: CheckBox
    private lateinit var confirmSetting: TextView

    private lateinit var _title: ColorTextView
    private lateinit var control_setting_title: ColorTextView
    private lateinit var introduction: ColorTextView

    private var isClickFloat = false
    private var isClickNotify = false

    override fun layout(): Int {
        return R.layout.welcome_activity_presetting
    }

    override fun bindView() {
        initView()
        refresh()
    }

    /**
     * 屏蔽物理返回按钮
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    private fun refresh() {
        controlByFloat.isChecked = DEF.floatview().getBoolean(Constants.SHOW_FLOAT_VIEW, true)
        controlByNotify.isChecked = DEF.floatview().getBoolean(Constants.IS_SHOW_NOTIFY, true)
        triggerByFloat.isChecked = DEF.floatview().getBoolean(Constants.USE_FLOAT_VIEW_TRIGGER, true)
        isClickFloat = true
        isClickNotify = true
    }

    private fun initView() {
        introduction = findViewById(R.id.introduction)
        control_setting_title = findViewById(R.id.control_setting_title)
        _title = findViewById(R.id._title)
        controlByNotify = findViewById(R.id.contron_by_notify)
        triggerByFloat = findViewById(R.id.trigger_by_float)
        controlByFloat = findViewById(R.id.contron_by_float)
        confirmSetting = findViewById(R.id.confirm)

        val color = Attr.getPrimaryColor(this)
        _title.setColorTextColor(color)
        _title.setColorText(resources.getString(R.string.pre_setting_title))

        control_setting_title.setColorTextColor(color)
        control_setting_title.setColorText(resources.getString(R.string.pre_setting_intro1))
        control_setting_title.setShakelessClickListener {
            ClipboardUtils.setText(this,
                resources.getString(R.string.pre_setting_intro2)
            )
        }

        introduction.setColorTextColor(color)
        introduction.setColorText(resources.getString(R.string.pre_setting_intro2))

        controlByFloat.setOnCheckedChangeListener { buttonView, isChecked ->
            LetMeKnow.report(LetMeKnow.PRE__FLOATVIEW, isChecked)
            DEF.floatview().putBoolean(Constants.SHOW_FLOAT_VIEW, isChecked)
            LogUtil.se("BROADCAST_CLIPBOARD_LISTEN_SERVICE_MODIFIED")
            sendBroadcast(Intent(Constants.BROADCAST_CLIPBOARD_LISTEN_SERVICE_MODIFIED))
            LogUtil.se("BROADCAST_TIMECAT_MONITOR_SERVICE_MODIFIED")
            sendBroadcast(Intent(Constants.BROADCAST_TIMECAT_MONITOR_SERVICE_MODIFIED))
            if (isClickFloat && isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    !Settings.canDrawOverlays(applicationContext)
                ) {
                    SnackBarUtil.show(
                        buttonView,
                        getString(R.string.punish_float_problem),
                        getString(R.string.punish_float_action)
                    ) {
                        try {
                            val intent = Intent(
                                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:$packageName")
                            )
                            startActivity(intent)
                        } catch (e: Throwable) {
                            SnackBarUtil.show(
                                buttonView,
                                R.string.open_setting_failed_diy
                            )
                        }
                    }
                } else {
                    SnackBarUtil.show(
                        buttonView,
                        getString(R.string.punish_float_problem)
                    )
                }
            }
        }
        controlByNotify.setOnCheckedChangeListener { buttonView, isChecked ->
            DEF.floatview().putBoolean(Constants.IS_SHOW_NOTIFY, isChecked)
            LetMeKnow.report(LetMeKnow.PRE__NOTIFY, isChecked)
            LogUtil.se("BROADCAST_CLIPBOARD_LISTEN_SERVICE_MODIFIED")
            sendBroadcast(Intent(Constants.BROADCAST_CLIPBOARD_LISTEN_SERVICE_MODIFIED))
            if (isClickNotify && isChecked) {
                if (!NotificationCheckUtil.areNotificationsEnabled(applicationContext)) {
                    SnackBarUtil.show(
                        buttonView,
                        getString(R.string.notify_enable),
                        getString(R.string.notify_disabled_title)
                    ) {
                        try {
                            val intent = Intent()
                            intent.setClassName(
                                "com.android.settings",
                                "com.android.settings.Settings\$AppNotificationSettingsActivity"
                            )
                            intent.putExtra("app_package", packageName)
                            intent.putExtra("app_uid", applicationInfo.uid)
                            startActivity(intent)
                        } catch (e: Throwable) {
                            SnackBarUtil.show(
                                buttonView,
                                R.string.open_setting_failed_diy
                            )
                        }
                    }
                } else {
                    SnackBarUtil.show(buttonView, getString(R.string.notify_enable))
                }
            }
        }
        triggerByFloat.setOnCheckedChangeListener { buttonView, isChecked ->
            DEF.floatview().putBoolean(Constants.USE_FLOAT_VIEW_TRIGGER, isChecked)
            LetMeKnow.report(LetMeKnow.PRE__TRIGGER, isChecked)
        }
        confirmSetting.setShakelessClickListener {
            LetMeKnow.report(LetMeKnow.CLICK_PRE_CONFIRM)
            showConfirmDialog()
        }
        initAnimation()
    }

    private fun initAnimation() {
        introduction.visibility = View.GONE
        controlByFloat.visibility = View.GONE
        controlByNotify.visibility = View.GONE
        triggerByFloat.visibility = View.GONE
        confirmSetting.visibility = View.GONE
        control_setting_title.scaleX = 0.8f
        control_setting_title.scaleY = 0.8f
        control_setting_title.alpha = 0.5f
        control_setting_title.animate().alpha(1f).scaleX(1f).scaleY(1f)
            .setInterpolator(AnticipateOvershootInterpolator()).setDuration(500)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    animationTwo()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }).start()
    }

    private fun animationTwo() {
        Handler().postDelayed({
            introduction.visibility = View.VISIBLE
            introduction.scaleX = 0.5f
            introduction.scaleY = 0.5f
            introduction.alpha = 0f
            introduction.animate().alpha(1f).scaleX(1f).scaleY(1f)
                .setInterpolator(AnticipateOvershootInterpolator()).setDuration(500)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        animationThree()
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                }).start()
        }, 1500)
    }

    private fun animationThree() {
        controlByFloat.visibility = View.VISIBLE
        controlByNotify.visibility = View.VISIBLE
        triggerByFloat.visibility = View.VISIBLE
        confirmSetting.visibility = View.VISIBLE
        controlByFloat.scaleX = 0.5f
        controlByFloat.scaleY = 0.5f
        controlByNotify.scaleX = 0.5f
        controlByNotify.scaleY = 0.5f
        triggerByFloat.scaleX = 0.5f
        triggerByFloat.scaleY = 0.5f
        confirmSetting.scaleX = 0.5f
        confirmSetting.scaleY = 0.5f
        controlByFloat.animate().scaleX(1f).scaleY(1f).setDuration(500)
            .setInterpolator(AnticipateOvershootInterpolator()).start()
        controlByNotify.animate().scaleX(1f).scaleY(1f).setDuration(500)
            .setInterpolator(AnticipateOvershootInterpolator()).start()
        triggerByFloat.animate().scaleX(1f).scaleY(1f).setDuration(500)
            .setInterpolator(AnticipateOvershootInterpolator()).start()
        confirmSetting.animate().scaleX(1f).scaleY(1f).setDuration(500)
            .setInterpolator(AnticipateOvershootInterpolator()).start()
    }

    private fun showConfirmDialog() {
        MaterialDialog(this).show {
            message(R.string.pre_setting_intro3)
            positiveButton(R.string.confirm_setting) {
                LetMeKnow.report(LetMeKnow.PRE__FLOATVIEW, false)
                LetMeKnow.report(LetMeKnow.CLICK_PRE_CONFIRM_IN_DIALOG)
                DEF.config().save(Constants.PRESETTING_ACTIVITY_SHOW, false)
                NAV.goAndFinish(this@PreSettingActivity, RouterHub.MASTER_MainActivity)
            }
            negativeButton(R.string.pre_setting_cancel) {
                LetMeKnow.report(LetMeKnow.CLICK_PRE_CANCEL_IN_DIALOG)
            }
        }
    }
}