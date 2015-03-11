package com.moses.tuan;


import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 丹 on 2014/12/9.
 */
public class FragmentGroupBuy extends Fragment {
    GridView catoGridView, goodsGridView;
    Spinner citySpinner;
    LayoutInflater mInflater;
    View view;
    TextView mTimerTxt;
    Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group_buy_layout, container, false);

        mInflater = LayoutInflater.from(getActivity());

        catoGridView = (GridView) view.findViewById(R.id.grid_view_cato);
        goodsGridView = (GridView) view.findViewById(R.id.grid_view_goods);
        citySpinner = (Spinner) view.findViewById(R.id.citySpinner);

        ArrayAdapter cityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.city, android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        CatoGridViewAdapter catoGridViewAdapter = new CatoGridViewAdapter();
        catoGridView.setAdapter(catoGridViewAdapter);

        MyGoodsAdapter myGoodsAdapter = new MyGoodsAdapter(getActivity());
        goodsGridView.setAdapter(myGoodsAdapter);

        mTimerTxt = (TextView) view.findViewById(R.id.timer_txt);
        new TimerThread(3, 2, 11).start();
        return view;

    }


    /**
     * 写一个类继承BaseAdapter，用以创建适配器
     */
    class CatoGridViewAdapter extends BaseAdapter {
        List<GridViewItem> mList = new ArrayList<>();

        public CatoGridViewAdapter() {
            mList = getData();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /**
             * 判断条件，确定是创建view还是保存view
             */
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.grid_cato_layout, null);
                holder = new ViewHolder();
                holder.catoImg = (ImageView) convertView.findViewById(R.id.catoImg);
                holder.catoTxt = (TextView) convertView.findViewById(R.id.catoTxt);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            GridViewItem gridViewItem = mList.get(position);
            holder.catoImg.setImageResource(gridViewItem.getImg());
            holder.catoTxt.setText(gridViewItem.getText());

            return convertView;
        }

    }


    /**
     * 写一个ViewHolder,优化适配器
     */
    class ViewHolder {
        TextView catoTxt;
        ImageView catoImg;
    }


    /**
     * 这个类包含gridview中的item的属性
     */
    class GridViewItem {
        private int img;
        private String text;

        public void setImg(int img) {
            this.img = img;
        }

        public int getImg() {
            return img;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    //封装一个获取数据的方法
    public List<GridViewItem> getData() {
        ArrayList<GridViewItem> mData = new ArrayList<>();
        mData.add(setData(R.drawable.ic_category_food, getResources().getString(R.string.foodCato)));
        mData.add(setData(R.drawable.ic_category_movie, getResources().getString(R.string.movieCato)));
        mData.add(setData(R.drawable.ic_category_hotel, getResources().getString(R.string.hotelCato)));
        mData.add(setData(R.drawable.ic_category_ktv, getResources().getString(R.string.ktvCato)));
        mData.add(setData(R.drawable.ic_category_voucher, getResources().getString(R.string.voucherCato)));
        mData.add(setData(R.drawable.ic_category_spots_tikect, getResources().getString(R.string.spotsTicketCato)));
        mData.add(setData(R.drawable.ic_category_daily_bill, getResources().getString(R.string.dailyBillCato)));
        mData.add(setData(R.drawable.ic_category_more, getResources().getString(R.string.moreCato)));
        return mData;
    }

    //封装一个设置数据的方法
    public GridViewItem setData(int imgId, String textTxt) {
        GridViewItem gridViewItem = new GridViewItem();
        gridViewItem.setImg(imgId);
        gridViewItem.setText(textTxt);

        return gridViewItem;
    }


    //view_pager_goods的适配器
    public class MyGoodsAdapter extends BaseAdapter {
        List<Goods> mData = new ArrayList<>();
        Context mContext;

        public MyGoodsAdapter(Context context) {
            mContext = context;
            mData = getGoodsData();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GoodsViewHolder goodsViewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.grid_goods_layout, null);
                goodsViewHolder = new GoodsViewHolder();
                goodsViewHolder.goodsImg = (ImageView) convertView.findViewById(R.id.goodsImg);
                goodsViewHolder.goodsName = (TextView) convertView.findViewById(R.id.goodsName);
                goodsViewHolder.goodsPrice = (TextView) convertView.findViewById(R.id.goodsPrice);
                goodsViewHolder.goodsPriceOffInfo = (TextView) convertView.findViewById(R.id.goodsOffIno);

                convertView.setTag(goodsViewHolder);
            } else {
                goodsViewHolder = (GoodsViewHolder) convertView.getTag();
            }
            goodsViewHolder.goodsImg.setImageResource(mData.get(position).getGoodsImg());
            goodsViewHolder.goodsName.setText(mData.get(position).getGoodsName());
            goodsViewHolder.goodsPrice.setText(mData.get(position).getGoodsPrice());
            goodsViewHolder.goodsPriceOffInfo.setText(mData.get(position).getGoodsPriceOffInfo());

            return convertView;
        }
    }

    class GoodsViewHolder {
        ImageView goodsImg;
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsPriceOffInfo;
    }

    class Goods {
        private int goodsImg;
        private String goodsName;
        private String goodsPrice;
        private String goodsPriceOffInfo;

        public void setGoodsImg(int goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsPriceOffInfo() {
            return goodsPriceOffInfo;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsPriceOffInfo(String goodsPriceOffInfo) {
            this.goodsPriceOffInfo = goodsPriceOffInfo;
        }

        public String getGoodsName() {
            return goodsName;
        }
    }

    public Goods setGoodsData(int img, String name, String price, String goodsPriceOffInfo) {
        Goods newGoods = new Goods();
        newGoods.setGoodsImg(img);
        newGoods.setGoodsName(name);
        newGoods.setGoodsPrice(price);
        newGoods.setGoodsPriceOffInfo(goodsPriceOffInfo);

        return newGoods;
    }

    public List<Goods> getGoodsData() {
        ArrayList<Goods> newList = new ArrayList<>();
        newList.add(setGoodsData(R.drawable.ba_guo_bu_yi, "巴国布衣代金券1张", "¥78", "手机购买立减¥20"));
        newList.add(setGoodsData(R.drawable.niao_niao_lao_huo_guo, "骉骉老火锅4人餐", "¥138", "手机购买立减¥20"));

        newList.add(setGoodsData(R.drawable.niao_niao_lao_huo_guo, "骉骉老火锅4人餐", "¥138", "手机购买立减¥20"));
        newList.add(setGoodsData(R.drawable.he_qiao_tan_shao, "鹤桥炭火烤肉双人餐1份", "¥62.9", "手机购买立减¥15"));

        newList.add(setGoodsData(R.drawable.ba_guo_bu_yi, "巴国布衣代金券1张", "¥78", "手机购买立减¥20"));
        newList.add(setGoodsData(R.drawable.niao_niao_lao_huo_guo, "骉骉老火锅4人餐", "¥138", "手机购买立减¥20"));

        newList.add(setGoodsData(R.drawable.niao_niao_lao_huo_guo, "骉骉老火锅4人餐", "¥138", "手机购买立减¥20"));
        newList.add(setGoodsData(R.drawable.he_qiao_tan_shao, "鹤桥炭火烤肉双人餐1份", "¥62.9", "手机购买立减¥15"));

        newList.add(setGoodsData(R.drawable.ba_guo_bu_yi, "巴国布衣代金券1张", "¥78", "手机购买立减¥20"));
        newList.add(setGoodsData(R.drawable.niao_niao_lao_huo_guo, "骉骉老火锅4人餐", "¥138", "手机购买立减¥20"));

        newList.add(setGoodsData(R.drawable.niao_niao_lao_huo_guo, "骉骉老火锅4人餐", "¥138", "手机购买立减¥20"));
        newList.add(setGoodsData(R.drawable.he_qiao_tan_shao, "鹤桥炭火烤肉双人餐1份", "¥62.9", "手机购买立减¥15"));

        return newList;

    }

    class TimerThread extends Thread {
        private int hour = 0, min = 0, sec = 0;
        private int curTime = 0;
        private int totTime;

        public TimerThread(int hour, int min, int sec) {
            totTime = (hour * 60 + min) * 60 + sec;
        }

        @Override
        public void run() {
            while (curTime < totTime) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        hour = (totTime - curTime) / 3600;
                        min = ((totTime - curTime) - 3600 * hour) / 60;
                        sec = ((totTime - curTime) - 3600 * hour) % 60;

                        String hourStr = String.valueOf(hour);
                        String minStr = String.valueOf(min);
                        String secStr = String.valueOf(sec);
                        if (hour < 10) {
                            hourStr = "0" + hourStr;
                        }
                        if (min < 10) {
                            minStr = "0" + minStr;
                        }
                        if (sec < 10) {
                            secStr = "0" + secStr;
                        }
                        String timeStr = hourStr + ":" + minStr + ":" + secStr;
                        mTimerTxt.setText(timeStr);
                    }
                });
                curTime = curTime + 1;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }


    }

}
