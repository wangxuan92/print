package io.kuban.print.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.kuban.print.activity.ChooseActivity;
import io.kuban.print.activity.LogInActivity;
import io.kuban.print.activity.SetUpTheActivity;
import io.kuban.print.activity.WebActivity;


/**
 * intent跳转管理
 * Created by wangxuan on 16/6/27.
 */
public class ActivityManager {
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String TYPE = "type";

    //-----------------------------------------LogInActivity
    public static void startLogInActivity(Context context, Intent intent, String type) {
        intent.setClass(context, LogInActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    public static void toLogInActivity(Context context, Intent intent) {
        Intent mBootIntent = new Intent(context, LogInActivity.class);
        //下面这句话必须加上才能开机自动运行app的界面
        mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mBootIntent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }

    //-----------------------------------------绑定界面
    public static void startChooseActivity(Context context, Intent intent) {
        intent.setClass(context, ChooseActivity.class);
        context.startActivity(intent);
    }

    //-----------------------------------------设置界面
    public static void startSetUpTheActivity(Context context, Intent intent) {
        intent.setClass(context, SetUpTheActivity.class);
        context.startActivity(intent);
    }
//
//    //-----------------------------------------切换布局界面
//    public static void startSwitchThemeActivity(Context context, Intent intent) {
//        intent.setClass(context, SwitchThemeActivity.class);
//        context.startActivity(intent);
//    }

    //-----------------------------------------WebActivity
    public static void startWebActivity(Activity activity, Intent intent, String url) {
        intent.setClass(activity, WebActivity.class);
        intent.putExtra(URL, url);
        activity.startActivity(intent);
    }

}
