package com.xiaojiu.qq.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xiaojiu.qq.model.User;
import com.xiaojiu.qq.presenter.RegistPresent;
import com.xiaojiu.qq.utils.ThreadUtils;
import com.xiaojiu.qq.view.view.RegistView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 作者：${xiaojiukeji} on 17/1/1 23:05
 * 作用: 注册用户到环信服务器上
 */

public class RegistPresentImpl implements RegistPresent {
    private RegistView registView;

    public RegistPresentImpl(RegistView registView) {
        this.registView = registView;
    }

    @Override
    public void regist(final String username, final String pwd) {
        /*
        * 1.先注册Bmob云数据库
        * 2.如果Bmob成功了,再去注册环信平台
        * 3.如果Bmob成功了,环信失败了,则再去把Bmob上的数据给删掉
        * */




        User user = new User();
        user.setPassword(pwd);
        user.setUsername(username);

        user.signUp(new SaveListener<User>() {

            @Override
            public void done(final User user, BmobException e) {
                if (e==null){
                    //请求成功,去注册环信平台
                    ThreadUtils.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(username,pwd);
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        registView.onRegist(username,pwd,true,null);
                                    }
                                });
                            } catch (final HyphenateException e1) {
                                e1.printStackTrace();

                                //将Bmob上注册的user给删除掉
                                user.delete();
                                //环信注册失败了
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        registView.onRegist(username,pwd,false,e1.toString());
                                    }
                                });

                            }

                        }
                    });

                }else {
                    //请求失败,将结果告诉Activity
                    registView.onRegist(username,pwd,false,e.getMessage());
                }
            }
        });
    }
}
