package com.xiaojiu.qq.lisetener;

import com.hyphenate.EMCallBack;
import com.xiaojiu.qq.utils.ThreadUtils;

/**
 * 作者：${xiaojiukeji} on 17/1/11 21:49
 * 作用:
 */

public abstract class CallBackLisener implements EMCallBack {
    public abstract void onMainSuccess();

    public abstract void onMainError(int i,String s);

    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainSuccess();
            }
        });
    }

    @Override
    public void onError(final int i, final String s) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainError(i,s);
            }
        });
    }

    @Override
    public void onProgress(int i, String s) {

    }
}
