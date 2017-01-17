package com.xiaojiu.qq.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xiaojiu.qq.db.DBUtils;
import com.xiaojiu.qq.presenter.ContactParenter;
import com.xiaojiu.qq.utils.ThreadUtils;
import com.xiaojiu.qq.view.view.ContactVIew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 作者：${xiaojiukeji} on 17/1/14 10:55
 * 作用:
 */

public class ContactPresentIml implements ContactParenter {
    private ContactVIew contactVIew;
    private List<String> contactsList = new ArrayList<>();

    public ContactPresentIml(ContactVIew contactVIew) {
        this.contactVIew = contactVIew;
    }

    @Override
    public void initContacts() {
        /**
         * 1.首先访问本地的缓存
         * 2.然后开辟子线程去环信后台获取当前用户的联系人
         * 3.更新本地的缓存,刷新UI
         */
        final String currentUser = EMClient.getInstance().getCurrentUser();
        List<String> contacts = DBUtils.getContacts(currentUser);
        contactsList.clear();
        contactsList.addAll(contacts);
        contactVIew.onInitContacts(contactsList);
        updateContactsFromServer(currentUser);

    }

    private void updateContactsFromServer(final String currentUser) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();//访问环信后台,获取联系人
                    //排序
                    Collections.sort(allContactsFromServer, new Comparator<String>() {
                        @Override
                        public int compare(String s, String t1) {
                            return s.compareTo(t1);
                        }
                    });
                    //更新本地的缓存,刷新UI
                    DBUtils.updateContacts(currentUser,allContactsFromServer);
                    contactsList.clear();
                    contactsList.addAll(allContactsFromServer);
                    //通知view,刷新UI
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            contactVIew.updateContacts(true,null);
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    //通知view,刷新UI
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            contactVIew.updateContacts(false,e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void updateContacts() {
        updateContactsFromServer(EMClient.getInstance().getCurrentUser());
    }

    @Override
    public void deleteContact(final String contact) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(contact);
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            contactVIew.onDelete(contact,true,null);
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            contactVIew.onDelete(contact,false,e.toString());
                        }
                    });
                }
            }
        });

    }
}
