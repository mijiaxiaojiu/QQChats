package com.xiaojiu.qq.utils;

import com.xiaojiu.qq.view.fragment.BaseFragment;
import com.xiaojiu.qq.view.fragment.ContactFragment;
import com.xiaojiu.qq.view.fragment.Conversationfragment;
import com.xiaojiu.qq.view.fragment.PluginFragment;

/**
 * 作者：${xiaojiukeji} on 17/1/10 22:06
 * 作用:
 */

public class FragmentFactory {
    private static Conversationfragment conversationfragment;
    private static ContactFragment contactFragment;
    private static PluginFragment pluginFragment;
    public static BaseFragment getFragment(int position){
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (conversationfragment == null){
                    conversationfragment = new Conversationfragment();
                }
                baseFragment = conversationfragment;
                break;
            case 1:
                if (contactFragment == null){
                    contactFragment = new ContactFragment();
                }
                baseFragment = contactFragment;
                break;
            case 2:
                if (pluginFragment == null){
                    pluginFragment = new PluginFragment();
                }
                baseFragment = pluginFragment;
                break;
        }
        return baseFragment;
    }
}
