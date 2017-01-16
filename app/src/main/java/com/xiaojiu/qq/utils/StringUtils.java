package com.xiaojiu.qq.utils;

import android.text.TextUtils;

/**
 * 作者：${xiaojiukeji} on 17/1/2 22:39
 * 作用: 字符串验证工具类
 */

public class StringUtils {
    public static boolean checkUsername(String username){
        if (TextUtils.isEmpty(username)){
            return false;
        }
        return username.matches("^[a-zA-Z]\\w{2,19}$");
    }
    public static boolean checkPwd(String pwd){
        if (TextUtils.isEmpty(pwd)){
            return false;
        }
        return pwd.matches("^[0-9]{3,20}$");
    }

    /**
     * 获取首字母
     * @param string
     * @return
     */
    public static String getInitial(String string){
        if (TextUtils.isEmpty(string)){
            return string;
        }else {
            return string.substring(0,1).toUpperCase();
        }
    }
}
