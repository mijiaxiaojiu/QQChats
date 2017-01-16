package com.xiaojiu.qq.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.xiaojiu.qq.lisetener.CallBackLisener;
import com.xiaojiu.qq.presenter.LoginPresent;
import com.xiaojiu.qq.view.view.LoginView;

/**
 * 作者：${xiaojiukeji} on 17/1/4 22:06
 * 作用:
 */

public class LoginPresentImpl implements LoginPresent {
    private LoginView loginView;

    public LoginPresentImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(final String username, final String password) {
        //环信目前3.5版本的所有回调方法都是在子线程中
        EMClient.getInstance().login(username, password, new CallBackLisener() {
            @Override
            public void onMainSuccess() {
                loginView.onLogin(username,password,true,null);
            }

            @Override
            public void onMainError(int i, String s) {
                loginView.onLogin(username,password,false,s);
            }
        });
    }
}