package com.xiaojiu.qq;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.xiaojiu.qq.db.DBUtils;
import com.xiaojiu.qq.event.OnContactUpdate;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by daaihuimin on 16/12/31.
 */

public class QQApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initHuanXin();
        initBmob();
        initContext();
    }

    private void initContext() {
        DBUtils.initDB(this);
    }

    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "3176c9f6f1c36276070804e14806f0d7");
    }

    private void initHuanXin() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，
        // falses代表需要验证
        options.setAcceptInvitationAlways(false);


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }


        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请

            }

            @Override
            public void onFriendRequestAccepted(String s) {

            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
                //好友请求被同意,发出通知让ContactFragment更新UI
                EventBus.getDefault().post(new OnContactUpdate(username, false));
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                //好友请求被同意,发出通知让ContactFragment更新UI
                EventBus.getDefault().post(new OnContactUpdate(username, true));
            }
        });
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
