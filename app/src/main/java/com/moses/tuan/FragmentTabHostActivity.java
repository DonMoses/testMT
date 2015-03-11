package com.moses.tuan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by 丹 on 2014/12/9.
 */
public class FragmentTabHostActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * fragment对象
     */

    private FragmentGroupBuy fragmentGroupBuy;
    private FragmentMerchant fragmentMerchant;
    private FragmentMine fragmentMine;
    private FragmentMore fragmentMore;
    /**
     * tabhost效果中每一项item布局
     */
    View groupBuyLayout;
    View merchantLayout;
    View mineLayout;
    View moreLayout;
    /**
     * tabhost效果中每一项item布局中的对象
     */
    private ImageView groupBuyImg;
    private ImageView merchantImg;
    private ImageView mineImg;
    private ImageView moreImg;
    private TextView groupBuyTxt;
    private TextView merchantTxt;
    private TextView mineTxt;
    private TextView moreTxt;

    /**
     * Fragment管理器
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_tabhost);
        // 初始化布局元素
        initViews();
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }


    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */

    private void initViews() {

        groupBuyLayout = findViewById(R.id.group_buy_layout);
        merchantLayout = findViewById(R.id.merchant_layout);
        mineLayout = findViewById(R.id.mine_layout);
        moreLayout = findViewById(R.id.more_layout);
        groupBuyImg = (ImageView) findViewById(R.id.group_buy_image);
        merchantImg = (ImageView) findViewById(R.id.merchant_image);
        mineImg = (ImageView) findViewById(R.id.mine_image);
        moreImg = (ImageView) findViewById(R.id.more_image);
        groupBuyTxt = (TextView) findViewById(R.id.group_buy_text);
        merchantTxt = (TextView) findViewById(R.id.merchant_text);
        mineTxt = (TextView) findViewById(R.id.mine_text);
        moreTxt = (TextView) findViewById(R.id.more_text);

        groupBuyLayout.setOnClickListener(this);
        merchantLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        moreLayout.setOnClickListener(this);

    }

    /**
     * tab选项的点击事件   点击则进入对应fragment
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_buy_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.merchant_layout:
                // 当点击了消息tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.mine_layout:
                // 当点击了消息tab时，选中第2个tab
                setTabSelection(2);
                break;
            case R.id.more_layout:
                // 当点击了消息tab时，选中第3个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    /**
     * 下面代码虽然多，但实际上就是2个gridview的数据源适配器
     *
     * @param index : fragment selected
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                groupBuyImg.setImageResource(R.drawable.tab_group_on);
                groupBuyTxt.setTextColor(Color.WHITE);
                if (fragmentGroupBuy == null) {
                    // fragmentGroupBuy，则创建一个并添加到界面上
                    fragmentGroupBuy = new FragmentGroupBuy();
                    transaction.add(R.id.content, fragmentGroupBuy);
                } else {
                    // 如果fragmentGroupBuy不为空，则直接将它显示出来
                    transaction.show(fragmentGroupBuy);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                merchantImg.setImageResource(R.drawable.tab_merchant_on);
                merchantTxt.setTextColor(Color.WHITE);
                if (fragmentMerchant == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    fragmentMerchant = new FragmentMerchant();
                    transaction.add(R.id.content, fragmentMerchant);
                } else {
                    // 如果fragmentMerchant不为空，则直接将它显示出来
                    transaction.show(fragmentMerchant);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                mineImg.setImageResource(R.drawable.tab_mine_on);
                mineTxt.setTextColor(Color.WHITE);
                if (fragmentMine == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    fragmentMine = new FragmentMine();
                    transaction.add(R.id.content, fragmentMine);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(fragmentMine);
                }
                break;
            case 3:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                moreImg.setImageResource(R.drawable.tab_more_on);
                moreTxt.setTextColor(Color.WHITE);
                if (fragmentMore == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    fragmentMore = new FragmentMore();
                    transaction.add(R.id.content, fragmentMore);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(fragmentMore);
                }
                break;

        }
        transaction.commit();
    }


    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        groupBuyImg.setImageResource(R.drawable.tab_group_off);
        groupBuyTxt.setTextColor(Color.parseColor("#82858b"));
        merchantImg.setImageResource(R.drawable.tab_merchant_off);
        merchantTxt.setTextColor(Color.parseColor("#82858b"));
        mineImg.setImageResource(R.drawable.tab_mine_off);
        mineTxt.setTextColor(Color.parseColor("#82858b"));
        moreImg.setImageResource(R.drawable.tab_more_off);
        moreTxt.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragmentGroupBuy != null) {
            transaction.hide(fragmentGroupBuy);
        }
        if (fragmentMerchant != null) {
            transaction.hide(fragmentMerchant);
        }
        if (fragmentMine != null) {
            transaction.hide(fragmentMine);
        }
        if (fragmentMore != null) {
            transaction.hide(fragmentMore);
        }
    }


}
