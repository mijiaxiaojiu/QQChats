package com.xiaojiu.qq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xiaojiu.qq.utils.FragmentFactory;
import com.xiaojiu.qq.view.BaseActivity;
import com.xiaojiu.qq.view.fragment.BaseFragment;
import com.xiaojiu.qq.view.fragment.Conversationfragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private int[] titleIds = {R.string.conversation,R.string.contact,R.string.plugin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initToolbar();
        initBottomNavigation();
    }


    private void initToolbar() {
        toolbar.setTitle("");//Toolbar隐藏掉
        setSupportActionBar(toolbar);
        tvTitle.setText(titleIds[0]);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //初始化底部导航栏
    private void initBottomNavigation() {
        BottomNavigationItem conversationItem = new BottomNavigationItem(R.mipmap.conversation_selected_2, "消息");
        BottomNavigationItem contactItem = new BottomNavigationItem(R.mipmap.contact_selected_2, "联系人");
        final BottomNavigationItem pluginItem = new BottomNavigationItem(R.mipmap.plugin_selected_2, "动态");

        bottomNavigationBar.setActiveColor(R.color.btn_normal);//设置选中后的颜色
        bottomNavigationBar.setInActiveColor(R.color.inActivity);//设置未选中的颜色

        bottomNavigationBar.addItem(conversationItem)
                .addItem(contactItem)
                .addItem(pluginItem)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

        initFirstFragemt();
    }

    private void initFirstFragemt() {
        /*    解决重影的问题
        *   如果这个activity中已经有了老的fragment(就是activity保存的历史的状态,又恢复了),先全部移除
        * */
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        for (int i = 0; i < titleIds.length; i++) {
            Fragment fragment = supportFragmentManager.findFragmentByTag(""+i);
            if (fragment != null){
                fragmentTransaction.remove(fragment);
            }
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, FragmentFactory.getFragment(0), "0").commit();
        tvTitle.setText(titleIds[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder builder = (MenuBuilder) menu;
        builder.setOptionalIconsVisible(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_friend:
                //TODO  跳转到添加好友的界面
                break;
            case R.id.menu_scan:
                showToast("分享好友");
                break;
            case R.id.menu_about:
                showToast("关于我们");
                break;
            case R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BaseFragment baseFragment = FragmentFactory.getFragment(position);

        if (!baseFragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_content,baseFragment,""+position);
        }
        fragmentTransaction.show(baseFragment).commit();
    }

    @Override
    public void onTabUnselected(int position) {
        getSupportFragmentManager().beginTransaction().hide(FragmentFactory.getFragment(position)).commit();
    }

    @Override
    public void onTabReselected(int position) {
        // TODO 测试使用,狂欢吧
    }
}
