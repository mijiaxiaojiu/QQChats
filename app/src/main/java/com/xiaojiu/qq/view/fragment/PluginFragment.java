package com.xiaojiu.qq.view.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyphenate.chat.EMClient;
import com.xiaojiu.qq.MainActivity;
import com.xiaojiu.qq.R;
import com.xiaojiu.qq.presenter.PluginPresenter;
import com.xiaojiu.qq.presenter.impl.PluginPresenterImpl;
import com.xiaojiu.qq.utils.ToastUtils;
import com.xiaojiu.qq.view.LoginActivity;
import com.xiaojiu.qq.view.view.PluginView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PluginFragment extends BaseFragment implements View.OnClickListener,PluginView {
    private Button button;
    private PluginPresenter pluginPresenter;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plugin, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (Button) view.findViewById(R.id.btn_logout);

        String currentUser = EMClient.getInstance().getCurrentUser();
        button.setText("退("+currentUser+")出");
        button.setOnClickListener(this);
        //对象的手动注入
        pluginPresenter = new PluginPresenterImpl(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        progressDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage("正在退出...");

        progressDialog.show();
        //退出登陆
        pluginPresenter.logout();

    }

    @Override
    public void onLogout(String username, boolean success, String msg) {
        progressDialog.hide();
        if (success){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.startActivity(LoginActivity.class,true);
        }else {
            ToastUtils.showToast(getContext(),msg);
        }
    }
}
