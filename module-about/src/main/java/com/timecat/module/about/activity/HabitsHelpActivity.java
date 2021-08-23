package com.timecat.module.about.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.xiaojinzi.component.anno.RouterAnno;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.timecat.module.about.R;

import com.timecat.identity.readonly.RouterHub;

/**
 * @author dlink
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2018/11/11
 * @description null
 * @usage null
 */
@RouterAnno(hostAndPath = RouterHub.ABOUT_HabitsHelpActivity)
public class HabitsHelpActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setFullscreen(true);
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Intro);

        addSlide(new SimpleSlide.Builder().layout(R.layout.about_help_habits_1).background(R.color.schedule_help_background).backgroundDark(R.color.schedule_help_background_dark).build());

        addSlide(new SimpleSlide.Builder().layout(R.layout.about_help_habits_2).background(R.color.schedule_help_background).backgroundDark(R.color.schedule_help_background_dark).build());

        addSlide(new SimpleSlide.Builder().layout(R.layout.about_help_habits_3).background(R.color.schedule_help_background).backgroundDark(R.color.schedule_help_background_dark).build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.edit().putBoolean("PREFERENCE_ROUTINES_HELP_SHOWN", true).apply();
    }

}
