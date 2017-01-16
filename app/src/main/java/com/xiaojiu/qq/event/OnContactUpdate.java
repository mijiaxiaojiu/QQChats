package com.xiaojiu.qq.event;

/**
 * 作者：${xiaojiukeji} on 17/1/15 17:45
 * 作用:
 */

public class OnContactUpdate {
    public String contact;
    public boolean isAdded;

    public OnContactUpdate(String contact, boolean isAdded) {
        this.contact = contact;
        this.isAdded = isAdded;
    }
}
