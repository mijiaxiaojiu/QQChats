package com.xiaojiu.qq.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.xiaojiu.qq.presenter.SplashPresenter;
import com.xiaojiu.qq.view.view.SplashView;

/**
 * Created by daaihuimin on 17/1/1.
 */

public class SplashPresentImpl implements SplashPresenter {
    private SplashView mSplashView;

    public SplashPresentImpl(SplashView mSplashView) {
        this.mSplashView = mSplashView;
    }

    @Override
    public void checkLoginer() {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()){
            //已经登陆过
            mSplashView.onCheckedLogin(true);
        }else {
            //还未登陆
            mSplashView.onCheckedLogin(false);
        }
    }
}
