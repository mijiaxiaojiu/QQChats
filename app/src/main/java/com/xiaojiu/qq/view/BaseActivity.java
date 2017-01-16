package com.xiaojiu.qq.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xiaojiu.qq.model.User;
import com.xiaojiu.qq.utils.Constant;
import com.xiaojiu.qq.utils.ToastUtils;

/**
 * 作者：${xiaojiukeji} on 17/1/1 11:19
 * 作用:
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    public void startActivity(Class clazz,boolean isFinish){
        startActivity(new Intent(this,clazz));
        if (isFinish){
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);//不能自动消除
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
    }

    public void saveUser(String username,String pwd){
        sharedPreferences.edit().putString(Constant.SP_KEY_USERNAME,username)
                .putString(Constant.SP_KEY_PASSWORD,pwd).commit();
    }

    public String getUserName(){
        return sharedPreferences.getString(Constant.SP_KEY_USERNAME,"");
    }
    public String getPassword(){
        return sharedPreferences.getString(Constant.SP_KEY_PASSWORD,"");
    }

    public void showDialog(String msg){
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void hideDialog(){
        progressDialog.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    public void showToast(String msg){
        ToastUtils.showToast(this,msg);
    }
}
