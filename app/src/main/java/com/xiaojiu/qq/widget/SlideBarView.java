package com.xiaojiu.qq.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.hyphenate.util.DensityUtil;
import com.xiaojiu.qq.R;
import com.xiaojiu.qq.adapter.ContactAdapter;
import com.xiaojiu.qq.adapter.IContactAdapter;
import com.xiaojiu.qq.utils.StringUtils;

import java.util.List;

/**
 * 作者：${xiaojiukeji} on 17/1/14 10:04
 * 作用:
 */

public class SlideBarView extends View {
    private static final String[] SECTIONS = {"搜", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int mAvgWidth;
    private int mAvgHeight;
    private Paint paint;
    private TextView tv_float;
    private RecyclerView recyclerView;

    public SlideBarView(Context context) {
        this(context, null);
    }

    public SlideBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        //参数目的是不让有锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setTextSize(10);//在代码中直接写入的一般都是像素值,就是px
        paint.setTextSize(DensityUtil.sp2px(getContext(), 10));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#9c9c9c"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        mAvgWidth = measuredWidth / 2;
        mAvgHeight = measuredHeight / SECTIONS.length;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            canvas.drawText(SECTIONS[i], mAvgWidth, mAvgHeight * (i + 1), paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                /**
                 * 1.设置背景为灰色, 矩形,圆角
                 * 2.move到哪个文本section就把floatTitle修改掉
                 * 3.判断通讯录中正好有当前section字母开头的用户,则定位RecycleView的位置,让用户看得见
                 */

                setBackgroundResource(R.drawable.slidebar_bk);
                showFloatAndScrollRecyclerView(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 隐藏背景,把背景变成全透明
                 */
                setBackgroundColor(Color.TRANSPARENT);
                if (tv_float != null) {
                    tv_float.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    private void showFloatAndScrollRecyclerView(float y) {
        /**
         * 1.根据Y坐标计算点中的文本
         * 2.
         */

        int index = (int) (y / mAvgHeight);
        if (index < 0) {
            index = 0;
        } else if (index > SECTIONS.length - 1) {
            index = SECTIONS.length - 1;
        }
        String section = SECTIONS[index];
        /**
         * 获取FloatTitle,然后设置给section,通过父控件来获取
         *
         */
        if (tv_float == null) {
            ViewGroup parent = (ViewGroup) getParent();
            tv_float = (TextView) parent.findViewById(R.id.tv_float);
            recyclerView = (RecyclerView) parent.findViewById(R.id.recycleView);
        }
        tv_float.setVisibility(VISIBLE);
        tv_float.setText(section);
        /**
         * 1.拿到section后判断这个section在recycleView中所有数据的角标(也可能不存在)
         *
         * 通过recycleView获取Adapter,通过Adapter获取到联系人数据
         */
        RecyclerView.Adapter contactAdapter = recyclerView.getAdapter();
        if (contactAdapter instanceof IContactAdapter){//避免数据类型一场
            IContactAdapter iContactAdapter = (IContactAdapter) contactAdapter;
            List<String> contactData = iContactAdapter.getData();
            for (int i = 0; i < contactData.size(); i++) {
                String contact = contactData.get(i);
                if (section.equals(StringUtils.getInitial(contact))){
                    recyclerView.smoothScrollToPosition(i);
                    return;
                }
            }

        }else {
            throw new RuntimeException("使用slidebar绑定的adapter时,必须绑定IContactAdapter接口");
        }
    }
}
