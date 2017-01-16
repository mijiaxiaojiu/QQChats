package com.xiaojiu.qq.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaojiu.qq.R;
import com.xiaojiu.qq.presenter.RegistPresent;
import com.xiaojiu.qq.presenter.impl.RegistPresentImpl;
import com.xiaojiu.qq.utils.StringUtils;
import com.xiaojiu.qq.view.view.RegistView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity implements TextView.OnEditorActionListener,RegistView {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.til_username)
    TextInputLayout tilUsername;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @InjectView(R.id.btn_regist)
    Button btnRegist;
    @InjectView(R.id.tv_newuser)
    TextView tvNewuser;
    @InjectView(R.id.activity_login)
    LinearLayout activityLogin;

    private RegistPresent registPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_regist);
        ButterKnife.inject(this);
        //给Ui绑定一些事件
        etPwd.setOnEditorActionListener(this);
        registPresent = new RegistPresentImpl(this);
    }

    @OnClick(R.id.btn_regist)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regist:
                //点击注册
                regist();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (textView.getId() == R.id.et_pwd){
            if (i == EditorInfo.IME_ACTION_DONE){
                regist();
                return true;
            }
        }
        return false;
    }

    private void regist() {
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
        showDialog("正在注册");

        registPresent.regist(username,pwd);
    }

    @Override
    public void onRegist(String username, String pwd, boolean isSuccess, String msg) {
        hideDialog();
        if (isSuccess){//注册成功,将注册结果保存到数据库里
            saveUser(username,pwd);
            startActivity(LoginActivity.class,true);
        }else {//注册失败
            showToast("注册失败:"+msg);
        }
    }
}
