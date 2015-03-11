package com.moses.tuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by 丹 on 2014/12/4.
 */
public class PagerActivity extends Activity {
    ViewPager viewPager;
    ArrayList<View> mList;
    LayoutInflater inflater;    //将xml布局文件转为view对象加入到viewPager中
    View v1, v2, v3;
    MyPagerAdapter pagerAdapter;
    ImageView img1, img2, img3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pager);
        //初始化属性
        inflater = LayoutInflater.from(this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);  //获得viewPager对象
        //数据源
        mList = new ArrayList<View>();
        //获得view对象， 它们将被加入viewpager中
        v1 = inflater.inflate(R.layout.view_pager_1, null);
        v2 = inflater.inflate(R.layout.view_pager_2, null);
        v3 = inflater.inflate(R.layout.view_pager_3, null);
        //为数据源添加数据
        mList.add(v1);
        mList.add(v2);
        mList.add(v3);

        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        //设置数据  【setData()  + 刷新】
        pagerAdapter.setData(mList);

        //获得图片对象，在监听中动态改变其状态
        img1 = (ImageView) findViewById(R.id.scollImg1);
        img2 = (ImageView) findViewById(R.id.scollImg2);
        img3 = (ImageView) findViewById(R.id.scollImg3);

        //设置监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        img1.setImageResource(R.drawable.page_now);
                        img2.setImageResource(R.drawable.page);
                        img3.setImageResource(R.drawable.page);
                        break;
                    case 1:
                        img1.setImageResource(R.drawable.page);
                        img2.setImageResource(R.drawable.page_now);
                        img3.setImageResource(R.drawable.page);
                        break;
                    case 2:
                        img1.setImageResource(R.drawable.page);
                        img2.setImageResource(R.drawable.page);
                        img3.setImageResource(R.drawable.page_now);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //点击按钮进入下一个页面
        //这里注意：  一定是用已经初始化过的v3.findViewById()，而不能重新获取view对象，那样的话就不能获得需要的button
        button = (Button) v3.findViewById(R.id.goButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       Intent intent = new Intent(PagerActivity.this,FragmentTabHostActivity.class);
                       startActivity(intent);
                       finish();
                   }
               },1000);
            }
        });

    }

    class MyPagerAdapter extends PagerAdapter {
        ArrayList<View> mData = new ArrayList<View>();
        Context mContext;

        public MyPagerAdapter(Context context) {
            mContext = context;

        }

        //养成封装的好习惯
        public void setData(ArrayList<View> list) {
            mData = list;
            //数据源更新后，刷新数据
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //移除view
            View view = mData.get(position);
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加view
            View view = mData.get(position);
            container.addView(view);
            return view;
        }
    }
}
