package com.timecat.module.about.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.drakeet.about.AbsAboutActivity;
import com.drakeet.about.Card;
import com.drakeet.about.Category;
import com.drakeet.about.Contributor;
import com.drakeet.about.License;
import com.drakeet.about.Recommendation;
import com.drakeet.about.provided.GlideImageLoader;
import com.timecat.component.router.app.NAV;
import com.timecat.identity.readonly.RouterHub;
import com.timecat.module.about.R;
import com.xiaojinzi.component.anno.RouterAnno;

import java.util.List;


/**
 * @author dlink
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2018/11/10
 * @description null
 * @usage null
 */
@RouterAnno(hostAndPath = RouterHub.ABOUT_AboutActivity)
public class AboutActivity extends AbsAboutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageLoader(new GlideImageLoader());
    }

    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.drawable.ic_launcher);
        slogan.setText("送你最高效的时光喵~\nฅ(≧▽≦)ฅ");
        version.setText("v" + AppUtils.getAppVersionName());
    }

    @Override
    protected void onItemsCreated(@NonNull List<Object> items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card("现有的笔记、日程类APP都有一个共同的缺点，需要用户主动打开它们，才可以进行信息管理。而时光猫会主动响应用户操作，真正为用户省时间。在任意界面，复制一段文字即可触发时光猫的悬浮球。通过悬浮球，用户可在任意界面随时获取信息，包括但不限于截图笔记、全局复制等。\n\n" +
                "简单教程：\n\n" +
                "    [1] 新建任务/笔记页面：整合多种信息类型，有笔记模式和任务模式。笔记是纯文本或富文本，提供文集功能和标签以便分类。任务是文本+时间，文本方面和笔记模式的一样，也是纯文本或富文本，时间方面提供日期时间、闹钟提醒、全天、重要紧急等维度。所以时光猫里的任务是一个非常强大的概念，一个任务可以是课程(什么时候哪里上课)，可以是日程(什么时候提醒做什么)，可以是日历(明天生日提醒)，可以是闹钟(早上提醒起床)，可以是临时计划(今天写作业2小时，我有自控力不要提醒，设置为全天)，可以是.....。任务模式类似文集，也提供了计划、子计划功能，比文集多一个管理层级，使信息管理更加轻松！当然，笔记和任务还可以相互转化（!!!），以笔记转任务为例，只需在对已创建的笔记进行编辑时切换到任务模式即可，转化是复制式的，不用担心信息丢失。\n\n" +
                "    [2] 截图笔记：手机上的文本有些不利于获取（比如WPS中的pdf），时光猫提供了强大的获取信息的功能，截图笔记、全局复制、OCR、复制分词等。截图笔记对当前界面某一矩形截图，截图作为富文本笔记保存，可修改。也可OCR识别文字（需要自己设置密钥）。OCR目前需要自己设置密钥，设置页面有教程，以后会提供本地OCR，更稳定也更经济。 全局复制：主动对布局按文本分块，选中一块视为复制该块文本，比如按钮中的文字一般不能长按复制，那么可以通过这种方式选取。需要辅助功能权限。暂不支持Android8.0及以上。\n\n" +
                "    [3] 视图切换：长按主页面底部的导航栏上的图标，可以切换视图。目前支持的视图有：日程模块有列表视图、日程视图、星期视图(课程表)，月视图、四象限视图、卡片视图(闹钟)；习惯模块有卡片视图、多功能视图；笔记模块有列表视图、时间轴视图、Markdown视图；计划模块有列表视图、卡片视图。以后可自定义的视图还会增加，可以加群601215164参与定制。\n\n" +
                "    [4] 小部件：目前只支持习惯，有标记小部件、历史小部件、习惯强度小部件、连续完成数小部件、频率小部件。以后逐步对日程、笔记、计划模块提供小部件支持。\n\n" +
                "    [5] 通知栏：应内测群成员的需求优化了通知UI。通知可以显示今天任务数和使用手机时长。后者需要“查看应用使用情况”的权限，在侧滑栏 - 统计页面 - 工具栏快捷申请权限。权限管理正在完善。\n\n" +
                "    [6] ...功能太多了，先留个坑，有空再写（想帮忙写的话，请把写好的发邮件到 linxy59@mail2.sysu.edu.cn 或者通过 App 的反馈页面提交）\n\n"));

        items.add(new Category("Developers"));
        items.add(new Contributor(R.drawable.ic_launcher, "兮尘", "Developer & designer", "https://github.com/LinXueyuanStdio"));

        items.add(new Category("我独立开发的应用"));
        items.add(new Recommendation(
                0, "Tima Cat时光猫",
                "http://image.coolapk.com/apk_logo/2018/0618/ic_launcher-182751-o_1cg98m57g17uf1hv21pbocabnkdq-uid-1556227@1280x1277.png",
                "com.time.cat",
                "        现有的笔记、日程类APP都有一个共同的缺点，需要用户主动打开它们，才可以进行信息管理。\n" +
                        "        而时光猫会主动响应用户操作，真正为用户省时间。在任意界面，复制一段文字即可触发时光猫的悬浮球。\n" +
                        "        通过悬浮球，用户可在任意界面随时获取信息，包括但不限于截图笔记、全局复制等。",
                "https://www.coolapk.com/apk/com.time.cat",
                "2018-05-15 10:23:53",
                "2018-06-15 10:23:53", 15.00, false)
        );

        items.add(new Category("参考其架构或设计"));
        items.add(new Recommendation(
                1, "锤子BigBang",
                "http://image.coolapk.com/apk_logo/2017/1125/bigbangE682ACE6B5AE32-110827-o_1bvp1q5c5156p1104ahf55s246q-uid-704307@512x512.png",
                "com.forfan.bigbang",
                "是最初启发我开发timecat的项目，并且timecat原来是基于bigbang做的，对timecat有重要意义\n" +
                        "\n" +
                        "不止是分词！更是您的高效助手！ 将文字拆分成词组，随意编辑、拖拽组合，提高效率! 单击、双击、长按、复制等多种选词方式随心选！ 更有全局复制从任意位置复制文字，图片识别OCR从图片中提取文字，各种黑科技加持，给你的手机插上翅膀！ BigBang是锤子手机中的一项重磅功能，您可以在任意app中对选中文字进行进行快速编辑，包括分词，翻译，复制以及调整顺序，现在您不必拥有锤子，也能够体验到高效的文字处理了！",
                "https://www.coolapk.com/apk/com.forfan.bigbang",
                "2018-06-03 23:13:32",
                "2018-06-03 23:13:32", 4.68, false)
        );
        items.add(new Recommendation(
                2, "日事清",
                "http://pp.myapp.com/ma_icon/0/icon_10467604_1531880485/256",
                "com.rishiqing",
                "参考了其日程页面、月视图页面\n\n" +
                        "日事清是个人网民钟爱的日程安排、时间管理应用；团队喜爱的工作计划、工作日志软件。 ",
                "https://www.coolapk.com/apk/com.rishiqing",
                "2018-06-03 23:13:32",
                "2018-06-03 23:13:32", 62.68, false)
        );
        items.add(new Recommendation(
                3, "阅读",
                "http://image.coolapk.com/apk_logo/2018/0704/ic_launcher_round-171756-o_1chi6ctvs1m5n565u48155gh7nq-uid-440435@192x192.png",
                "com.gedoor.monkeybook",
                "参考其书架界面、阅读界面，timecat在其基础上进行修改以获得更好的阅读体验\n\n" +
                        "开源的阅读软件来了\n" +
                        "\n" +
                        "如今的小说阅读软件总是在不断的添加广告,作为一个程序猿这是受不了的,\n" +
                        "\n" +
                        "于是开源的阅读软件来,你不用再担心广告,\n" +
                        "\n" +
                        "本软件fork一个开源的软件代码经过大量修改,更符合使用习惯",
                "https://www.coolapk.com/apk/com.gedoor.monkeybook",
                "2018-06-03 23:13:32",
                "2018-06-03 23:13:32", 5.29, false)
        );
        items.add(new Recommendation(
                4, "抖音短视频",
                "http://pp.myapp.com/ma_icon/0/icon_42350811_1525936443/256",
                "com.ss.android.ugc.aweme",
                "timecat的计划视图是仿抖音视频的布局\n\n" +
                        "【抖音短视频】中国很受欢迎、旨在帮助用户记录美好生活的短视频平台。\n" +
                        "\n" +
                        "《快乐大本营》开设抖音专区啦~ 跟随快乐家族一起来抖音“捧脸杀”！《这！就是街舞》带你揭秘街舞大神们的“抖音”日常！《高能少年团》相约今夏，用抖音记录张一山杨紫的美好生活~",
                "https://www.coolapk.com/apk/com.ss.android.ugc.aweme",
                "2018-06-03 23:13:32",
                "2018-06-03 23:13:32", 57.18, false)
        );
        items.add(new Recommendation(
                5, "Idea Note-胶囊便签",
                "http://image.coolapk.com/apk_logo/2018/0613/E5BA94E794A8E59586E5BA97E59BBEE6A087-189766-o_1cfsa8mndak912v4vo915f31etpq-uid-1757352@512x512.png",
                "com.goyourfly.bigidea",
                "参考其无界面的设计，timecat悬浮窗从下到上第5个按钮打开\n\n" +
                        "Idea Note - 胶囊便签，语音笔记，非常高效，通过语音识别将您的声音转换为文字，同时还会保留语音，您可以在任何地方打开它（悬浮窗侧滑），它不是一个普通的笔记，它极大的提高了您输入的效率，同时它还会保存您的语音，以便您可以随时重听。",
                "https://www.coolapk.com/apk/com.goyourfly.bigidea",
                "2018-06-03 23:13:32",
                "2018-06-03 23:13:32", 12.31, false)
        );
        items.add(new Recommendation(
                6, "FV悬浮球-fooView",
                "http://image.coolapk.com/apk_logo/2016/0608/12202_1465371925_0046.png",
                "com.fooview.android.fooview",
                "启发悬浮窗的玩法，但是其设计不给用，所以没有参考这个。放这里是因为回复我的邮件很用心\n o(*////▽////*)q\n\n" +
                        "你是否算过每天点击了多少次手机按键，切换了多少次应用，有多少本来可以节省下来，却被浪费在无效操作上的时间？\n" +
                        "•\t一个手势，你是否需要一款截屏神器，随意一圈，在优酷，奇艺，腾讯视频中区域截屏，自动识别，一键分享?\n" +
                        "•\t一个手势，随意滑动几下，就能找到你所需要的文件内容？\n" +
                        "•\t一个手势，直接在微博，今日头条等软件中能完成翻译，导航，搜索，电话查询？\n" +
                        "•\t一个手势，能代替所有系统按键，比苹果上的虚拟按键更强大，更好操作？\n" +
                        "•\t一个手势，一键切换微信，QQ，一键跳转刚使用过的淘宝？一键搜索百度，谷歌，京东，亚马逊？\n" +
                        "FV浮动阅览器，发挥手机的智能，节约你80%的操作，让一切操作变得简单！更多等你发现！",
                "https://www.coolapk.com/apk/com.fooview.android.fooview",
                "2018-06-03 23:13:32",
                "2018-06-03 23:13:32", 8.48, false)
        );
        items.add(new License("轻番茄", "icodechef", License.MIT, "https://github.com/icodechef/Tick"));
        items.add(new License("Simple-Calendar", "SimpleMobileTools", License.APACHE_2, "https://github.com/SimpleMobileTools/Simple-Calendar"));
        items.add(new License("FastHub", "k0shk0sh", License.GPL_V3, "https://github.com/k0shk0sh/FastHub"));
        items.add(new License("920 Text Editor", "jecelyin", License.APACHE_2, "https://github.com/jecelyin/920-text-editor-v2"));
        items.add(new License("AdaptiveTableLayout", "Cleveroad", License.MIT, "https://github.com/Cleveroad/AdaptiveTableLayout"));
        items.add(new License("Loop Habit Tracker", "iSoron", License.GPL_V3, "https://github.com/iSoron/uhabits"));
        items.add(new License("codeboard", "gazlaws-dev", License.APACHE_2, "https://github.com/gazlaws-dev/codeboard"));
        items.add(new License("BoomMenu", "Nightonke", License.APACHE_2, "https://github.com/Nightonke/BoomMenu"));
        items.add(new License("StandOut", "pingpongboss", License.MIT, "https://github.com/pingpongboss/StandOut"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_github) {
            NAV.go(this, RouterHub.ABOUT_LicenseActivity);
        }
        return true;
    }
}
