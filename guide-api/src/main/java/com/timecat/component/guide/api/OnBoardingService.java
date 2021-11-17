package com.timecat.component.guide.api;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
public interface OnBoardingService {
    View buildOnBoardingView(
            Context context,
            List<OnBoardingPage> onBoardingPageList,
            OnBoardingCallback callback
    );
}
