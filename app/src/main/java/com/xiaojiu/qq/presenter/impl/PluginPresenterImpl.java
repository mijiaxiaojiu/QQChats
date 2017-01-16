package com.xiaojiu.qq.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.xiaojiu.qq.lisetener.CallBackLisener;
import com.xiaojiu.qq.presenter.PluginPresenter;
import com.xiaojiu.qq.view.view.PluginView;

/**
 * 作者：${xiaojiukeji} on 17/1/11 21:41
 * 作用:
 */

public class PluginPresenterImpl implements PluginPresenter {
    private PluginView pluginView;

    public PluginPresenterImpl(PluginView pluginView) {
        this.pluginView = pluginView;
    }

    @Override
    public void logout() {
        //参数1:true:解除绑定,不再推送消息
        EMClient.getInstance().logout(true, new CallBackLisener() {
                    @Override
                    public void onMainSuccess() {
                        pluginView.onLogout(EMClient.getInstance().getCurrentUser(),true,null);
                    }

                    @Override
                    public void onMainError(int i, String s) {
                        pluginView.onLogout(EMClient.getInstance().getCurrentUser(),false,s);
                    }
                }

        );
    }
}