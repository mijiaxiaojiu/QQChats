package com.xiaojiu.qq.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaojiu.qq.MainActivity;
import com.xiaojiu.qq.R;
import com.xiaojiu.qq.presenter.LoginPresent;
import com.xiaojiu.qq.presenter.impl.LoginPresentImpl;
import com.xiaojiu.qq.utils.StringUtils;
import com.xiaojiu.qq.view.view.LoginView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener,LoginView {

    private static final int REQUEST_SDCARD = 1;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;

    @InjectView(R.id.tv_newuser)
    TextView mTvNewuser;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.til_username)
    TextInputLayout tilUsername;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @InjectView(R.id.activity_login)
    LinearLayout activityLogin;

    private LoginPresent loginPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);
        //数据的回显
        etUsername.setText(getUserName());
        etPwd.setText(getPassword());
        etPwd.setOnEditorActionListener(this);

        loginPresent = new LoginPresentImpl(this);
    }
    /*
    当再次启动activity时,调用
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //数据的回显
        etUsername.setText(getUserName());
        etPwd.setText(getPassword());
    }

    @OnClick({R.id.btn_login, R.id.tv_newuser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_newuser:
                startActivity(RegistActivity.class, false);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (textView.getId() == R.id.et_pwd){
            if (i == EditorInfo.IME_ACTION_DONE){
                login();
            }
        }
        return false;
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (StringUtils.checkUsername(username)){
            tilUsername.setErrorEnabled(true);
            tilUsername.setError("用户名不合法");
            etUsername.requestFocus(View.FOCUS_RIGHT);
            return;
        }else {
            tilUsername.setErrorEnabled(false);
        }
        if (StringUtils.checkPwd(pwd)){
            tilPwd.setErrorEnabled(true);
            tilPwd.setError("密码不合法");
            etPwd.requestFocus(View.FOCUS_RIGHT);
            return;
        }else {
            tilUsername.setErrorEnabled(false);
        }
        showDialog("正在玩儿命登陆中");
        /**
         * 1. 动态申请权限
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_SDCARD);
            return;
        }

        loginPresent.login(username,pwd);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_SDCARD){
            if (grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
                //被授权了
                login();
            }else{
                showToast("没有给予该应用权限，不让你用了");
            }
        }
    }

    @Override
    public void onLogin(String username, String pwd, boolean success, String msg) {
        hideDialog();
        if (success){//保存用户,跳到主界面
            saveUser(username,pwd);
            startActivity(MainActivity.class,true);
        }else {
            showToast("登陆失败:"+msg);
        }
    }
}
