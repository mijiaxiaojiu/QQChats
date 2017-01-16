package com.xiaojiu.qq.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xiaojiu.qq.MainActivity;
import com.xiaojiu.qq.R;
import com.xiaojiu.qq.adapter.AnimatorListernerAdapter;
import com.xiaojiu.qq.presenter.impl.SplashPresentImpl;
import com.xiaojiu.qq.presenter.SplashPresenter;
import com.xiaojiu.qq.view.view.SplashView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by daaihuimin on 17/1/1.
 * 启动界面
 */

public class SplashActivity extends BaseActivity implements SplashView {
    private SplashPresenter mSplashPresenter;
    @InjectView(R.id.iv_splash)
    ImageView mIvSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        mSplashPresenter = new SplashPresentImpl(this);
        /*
        * 1.判断是否登陆了
        * 2.如果登陆了,直接进入MainActivity
        * 3.否则闪屏2秒(渐变动画),进入LoginActivity*/
        mSplashPresenter.checkLoginer();
    }

    @Override
    public void onCheckedLogin(boolean isLogin) {
        if (isLogin){
            startActivity(MainActivity.class,true);
        }else {
            //否则闪屏2秒(渐变动画),进入LoginActivity
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mIvSplash,"alpha",0,1).setDuration(2000);
            alpha.start();
            alpha.addListener(new AnimatorListernerAdapter(){
                @Override
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    startActivity(LoginActivity.class,true);
                }
            });
        }
    }
}
