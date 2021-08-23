package com.timecat.module.about.activity;

import android.graphics.Color;
import android.widget.RelativeLayout;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.xiaojinzi.component.anno.RouterAnno;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.timecat.page.base.friend.toolbar.BaseToolbarActivity;
import com.timecat.identity.readonly.RouterHub;
import com.timecat.layout.ui.utils.ScreenUtil;
import com.timecat.module.about.R;

/**
 * @author dlink
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2018/11/10
 * @description null
 * @usage null
 */
@Keep
@RouterAnno(hostAndPath = RouterHub.ABOUT_LicenseActivity)
public class LicenseActivity extends BaseToolbarActivity {

    protected Toolbar toolbar;

    @NonNull
    @Override
    protected String title() {
        return "开源许可";
    }

    @Override
    protected void initView() {
        Fragment fragment = new LibsBuilder()
                .withAboutAppName(getResources().getString(R.string.app_name))
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withLicenseShown(true)
                .withLicenseDialog(true)
                .withAboutDescription(getString(R.string.about_description0))
                .fragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragment).commit();
    }

    protected void setupToolbar(String title, int color, int iconColor) {
        // set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) return;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, ScreenUtil.getStatusBarHeight(this), 0, 0);
        toolbar.setLayoutParams(layoutParams);
//        toolbar.setContentInsetsAbsolute(0,0);
        toolbar.setContentInsetEndWithActions(0);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setBackgroundColor(color);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (title == null) {
            //set the back arrow in the toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setupToolbar(String title, int color) {
        setupToolbar(title, color, Color.WHITE);
    }

    protected void setupStatusBar(int color) {
        ScreenUtil.setStatusBarColor(this, color);

    }
}
