package com.moses.tuan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by 丹 on 2014/12/9
 */
public class FragmentMine extends Fragment {
    View view;  //这个view很关键， 需要通过它获得布局中的对象。其实例化在onCreateView（）中,即引入之布局对象
    LayoutInflater mInflater;
    @Nullable
    @Override
    /**
     * 引入布局
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,container,false);
        mInflater = LayoutInflater.from(getActivity());
        intiViews();
        return view;
    }

    /**
     * 初始化操作
     */
    public void intiViews(){

    }

}
