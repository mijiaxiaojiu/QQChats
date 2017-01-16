package com.xiaojiu.qq.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 作者：${xiaojiukeji} on 17/1/4 20:23
 * 作用: 创建一个单例toast
 */

public class ToastUtils {
    private static Toast toast;
    public static void showToast(Context context,String msg){
        if (toast == null){
            toast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
