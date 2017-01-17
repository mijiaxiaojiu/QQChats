package com.xiaojiu.qq.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaojiu.qq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Conversationfragment extends BaseFragment {

    private static final String TAG = "Conversationfragment";
    private static int[] datas = {38, 17, 16, 16, 7, 31, 39, 32, 2, 11};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversationfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
