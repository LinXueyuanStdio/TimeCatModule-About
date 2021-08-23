package com.timecat.module.about.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.timecat.page.base.friend.toolbar.BaseToolbarActivity;
import com.timecat.identity.readonly.Constants;
import com.timecat.identity.readonly.RouterHub;
import com.timecat.component.router.app.NAV;
import com.timecat.component.setting.DEF;
import com.timecat.module.about.R;
import com.xiaojinzi.component.anno.AttrValueAutowiredAnno;
import com.xiaojinzi.component.anno.RouterAnno;

/**
 * @author dlink
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2018/11/10
 * @description null
 * @usage null
 */
@RouterAnno(hostAndPath = RouterHub.ABOUT_HowToUseActivity)
public class HowToUseActivity extends BaseToolbarActivity {

    private View introMenu;
    private LinearLayout introContent;

    private TextView introTitle;
    private TextView introMsg;

    @AttrValueAutowiredAnno("goToOpenFromOuter")
    Boolean goToOpenFromOuter = false;

    @Override
    protected int layout() {
        return R.layout.about_activity_how_to_use;
    }

    @Override
    protected void initView() {
        NAV.inject(this);
        DEF.config().save(Constants.HAD_ENTER_INTRO, true);
        introMenu = findViewById(R.id.intro_menu);
        introContent = findViewById(R.id.intro_content);

        introTitle = findViewById(R.id.intro_title);
        introMsg = findViewById(R.id.intro_msg);

        findViewById(R.id.introduction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntroActivity();
            }
        });


        findViewById(R.id.overall_intro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.overall_intro);
                introMsg.setText(R.string.overall_intro_msg);
            }
        });

        findViewById(R.id.problems).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.problems);
                introMsg.setText(R.string.problem_content);
            }
        });

        findViewById(R.id.how_to_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.how_to_set_title);
                introMsg.setText(R.string.how_to_set_msg);
            }
        });


        findViewById(R.id.about_control).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.about_control);
                introMsg.setText(R.string.about_control_msg);
            }
        });

        findViewById(R.id.about_accessibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.about_accessibility);
                introMsg.setText(R.string.about_accessibility_msg);
            }
        });

        findViewById(R.id.about_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.about_click);
                introMsg.setText(R.string.about_click_msg);
            }
        });

        findViewById(R.id.how_to_use_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.how_to_use_copy);
                introMsg.setText(R.string.how_to_use_copy_msg);
            }
        });

        findViewById(R.id.about_ocr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.about_ocr);
                introMsg.setText(R.string.about_ocr_msg);
            }
        });

        findViewById(R.id.about_universal_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.about_universal_copy);
                introMsg.setText(R.string.about_universal_copy_msg);
            }
        });

        findViewById(R.id.open_from_outside).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMenu.setVisibility(View.GONE);
                introContent.setVisibility(View.VISIBLE);
                introTitle.setText(R.string.open_from_outside);
                introMsg.setText(R.string.open_from_outside_msg);
            }
        });

        if (goToOpenFromOuter) {
            findViewById(R.id.open_from_outside).performClick();
        }
    }

    private void showIntroActivity() {
        NAV.go(this, RouterHub.WELCOME_WelcomeGuideActivity);
    }

    @Override
    public void onBackPressed() {
        if (introMenu.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            introMenu.setVisibility(View.VISIBLE);
            introContent.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    protected String title() {
        return getString(R.string.introduction);
    }
}
