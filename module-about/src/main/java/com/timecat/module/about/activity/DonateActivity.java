package com.timecat.module.about.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xiaojinzi.component.anno.RouterAnno;
import com.timecat.element.alert.ToastUtil;
import com.timecat.page.base.friend.toolbar.BaseToolbarActivity;
import com.timecat.component.commonsdk.utils.IOUtil;
import com.timecat.component.commonsdk.utils.LetMeKnow;
import com.timecat.identity.readonly.RouterHub;
import com.timecat.module.about.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dlink
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2018/11/13
 * @description null
 * @usage null
 */
@RouterAnno(hostAndPath = RouterHub.ABOUT_DonateActivity)
public class DonateActivity extends BaseToolbarActivity {
    private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/timecat/image/donate/";//保存的确切位置
    public static String zhifubao = "https://qr.alipay.com/tsx07466cdmokhf2ybtsx07";
    public static String qqJump = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "l6WK5VMFvgGLHSFojlShfda9Nsiq0R2t").toString();

    private TextView donateMsg;

    @Override
    protected int layout() {
        return R.layout.about_activity_donate;
    }

    @NonNull
    @Override
    protected String title() {
        return getString(R.string.donate_title);
    }

    @Override
    protected void initView() {
        findViewById(R.id.image).setOnClickListener(v -> {
            LetMeKnow.report(LetMeKnow.CLICK_DONATE_ALIPAY_SAVE);
            File file = new File(SAVE_REAL_PATH, "donate_alipay.jpg");
            if (file.exists()) {
                ToastUtil.ok(R.string.picture_saved);
                sendBrodcast4Update(file);
                return;
            } else {
                @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.donate_alipay);
                try {
                    IOUtil.saveToFile(is, file);
                    ToastUtil.ok(R.string.picture_saved);
                    sendBrodcast4Update(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        findViewById(R.id.image1).setOnClickListener(v -> {
            LetMeKnow.report(LetMeKnow.CLICK_DONATE_WECHAT_SAVE);
            File file = new File(SAVE_REAL_PATH, "donate_wechat.png");
            if (file.exists()) {
                ToastUtil.ok(R.string.picture_saved);
                sendBrodcast4Update(file);
                return;
            } else {
                @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.donate_wechat);
                try {
                    IOUtil.saveToFile(is, file);
                    ToastUtil.ok(R.string.picture_saved);
                    sendBrodcast4Update(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        String donate = getString(R.string.donate_now);

        String qq = getString(R.string.join_qq);
        donateMsg = findViewById(R.id.donate_msg);
        donateMsg.setText(Html.fromHtml(getString(R.string.thinks_for_donate) + "<br /><br /><a href='" + qqJump + "'>" + qq + "</a>" + "<br /><br /><a href='" + zhifubao + "'>" + donate + "</a>"));
        donateMsg.setMovementMethod(CountLinkMovementMethod.getInstance());
    }

    /****************
     *
     * 发起添加群流程。群号：TimeCat内测群(601215164) 的 key 为： l6WK5VMFvgGLHSFojlShfda9Nsiq0R2t
     * 调用 joinQQGroup(l6WK5VMFvgGLHSFojlShfda9Nsiq0R2t) 即可发起手Q客户端申请加群 TimeCat内测群(601215164)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    private void sendBrodcast4Update(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
    }
}