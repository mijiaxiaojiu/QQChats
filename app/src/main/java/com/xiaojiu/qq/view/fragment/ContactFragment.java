package com.xiaojiu.qq.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaojiu.qq.R;
import com.xiaojiu.qq.adapter.ContactAdapter;
import com.xiaojiu.qq.event.OnContactUpdate;
import com.xiaojiu.qq.presenter.ContactParenter;
import com.xiaojiu.qq.presenter.impl.ContactPresentIml;
import com.xiaojiu.qq.utils.ToastUtils;
import com.xiaojiu.qq.view.view.ContactVIew;
import com.xiaojiu.qq.widget.ContactLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment implements ContactVIew, SwipeRefreshLayout.OnRefreshListener, ContactAdapter.OnItemLongClickListener {


    private ContactLayout contactLayout;
    private ContactParenter contactParenter;
    private ContactAdapter contactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactLayout = (ContactLayout) view.findViewById(R.id.contactLayout);

        contactParenter = new ContactPresentIml(this);
        //初始化联系人界面
        contactParenter.initContacts();

        contactLayout.setOnRefreshView(this);

        EventBus.getDefault().register(this);
    }

    /**
     * 事件回调
     * @param onContactUpdate
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OnContactUpdate onContactUpdate){
        contactParenter.updateContacts();
    }

    @Override
    public void onInitContacts(List<String> contacts) {
        contactAdapter = new ContactAdapter(contacts);

        contactLayout.setAdapter(contactAdapter);
        contactAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void updateContacts(boolean b, String str) {
        contactAdapter.notifyDataSetChanged();
        contactLayout.setRefreshing(false);//隐藏下啦刷新
    }

    @Override
    public void onDelete(String contact, boolean success, String msg) {
        if (success){//删除成功
            ToastUtils.showToast(getActivity(),"删除成功");
        }else {
            ToastUtils.showToast(getActivity(),"删除失败");
        }
    }

    @Override
    public void onRefresh() {
        /**
         * 1.访问网络,获取联系人
         * 2.如果拿到数据,更新数据库
         * 3.更新UI
         * 4.隐藏下啦刷新
         */
        contactParenter.updateContacts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemLongClick(final String contact, int position) {
        Snackbar.make(contactLayout,"您和"+contact+"确定友尽了吗?",Snackbar.LENGTH_LONG).setAction("确定",new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                ToastUtils.showToast(getActivity(),"删除了"+contact);
                contactParenter.deleteContact(contact);
            }
        }).show();
    }
}
