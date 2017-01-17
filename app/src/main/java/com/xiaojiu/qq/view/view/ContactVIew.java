package com.xiaojiu.qq.view.view;

import java.util.List;

/**
 * 作者：${xiaojiukeji} on 17/1/14 10:54
 * 作用:
 */

public interface ContactVIew {
    void onInitContacts(List<String> contacts);

    void updateContacts(boolean b, String str);

    void onDelete(String contact,boolean success,String msg);
}
