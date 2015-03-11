package com.moses.tuan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丹 on 2014/12/9.
 */
public class FragmentMerchant extends Fragment {
    LayoutInflater mInflater;
    View view;
    ListView mListView;
    TextView textView;
    Spinner mMsSpinner, mQcSpinner, mZnpxSpinner, mSxSpinner;
    ArrayAdapter spinnerAdapter1, spinnerAdapter2, spinnerAdapter3, spinnerAdapter4;
    MyAdapter myAdapter;

    /**
     * 引入布局
     *
     * @param inflater           布局管理器
     * @param container          父View
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引入布局
        view = inflater.inflate(R.layout.fragment_merchant, container, false);
        mInflater = LayoutInflater.from(getActivity());

        intiViews();
        return view;
    }

    /**
     * 初始化控件
     */
    public void intiViews() {

        mMsSpinner = (Spinner) view.findViewById(R.id.msSpinner);
        mQcSpinner = (Spinner) view.findViewById(R.id.qcSpinner);
        mZnpxSpinner = (Spinner) view.findViewById(R.id.znpxSpinner);
        mSxSpinner = (Spinner) view.findViewById(R.id.sxSpinner);
        spinnerAdapter1 = getArrayAdapter(R.array.catoGroup);
        spinnerAdapter2 = getArrayAdapter(R.array.areaGroup);
        spinnerAdapter3 = getArrayAdapter(R.array.rankGroup);
        spinnerAdapter4 = getArrayAdapter(R.array.filterGroup);
        mMsSpinner.setAdapter(spinnerAdapter1);
        mQcSpinner.setAdapter(spinnerAdapter2);
        mZnpxSpinner.setAdapter(spinnerAdapter3);
        mSxSpinner.setAdapter(spinnerAdapter4);

        textView = (TextView) mInflater.inflate(R.layout.fragment_merchant, null)
                .findViewById(R.id.catoSelected);

        mMsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] cato = getResources().getStringArray(R.array.catoGroup);
                String txt = cato[position];
                textView.setText(txt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView = (ListView) view.findViewById(R.id.list_view_merchant);
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);

    }

    class MyAdapter extends BaseAdapter {
        List<Info> mData = new ArrayList<>();

        public MyAdapter() {
            mData = getData();

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
            ViewHolderH viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_list_view_merchant, null);
                viewHolder = new ViewHolderH();
                viewHolder.img = (ImageView) convertView.findViewById(R.id.item_img);
                viewHolder.textView1 = (TextView) convertView.findViewById(R.id.title_text);
                viewHolder.textView2 = (TextView) convertView.findViewById(R.id.info_text);
                viewHolder.textView3 = (TextView) convertView.findViewById(R.id.price_text);
                viewHolder.textView4 = (TextView) convertView.findViewById(R.id.off_text);
                viewHolder.textView5 = (TextView) convertView.findViewById(R.id.count_text);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolderH) convertView.getTag();
            }
            Info info = mData.get(position);
            viewHolder.img.setImageResource(info.getImg());
            viewHolder.textView1.setText(info.getText1());
            viewHolder.textView2.setText(info.getText2());
            viewHolder.textView3.setText(info.getText3());
            viewHolder.textView4.setText(info.getText4());
            viewHolder.textView5.setText(info.getText5());
            return convertView;
        }

        public Info setData(int ii, String s1, String s2, String s3, String s4, String s5) {
            Info info = new Info();
            info.setImg(ii);
            info.setText1(s1);
            info.setText2(s2);
            info.setText3(s3);
            info.setText4(s4);
            info.setText5(s5);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    notify();
//                }
//            });
            return info;
        }

        public List<Info> getData() {
            ArrayList<Info> mList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                mList.add(setData(R.drawable.barbecue, "醉烧刀自助海鲜豆捞", "[双流机场] 59 单人自助，免费Wifi", "59元", "立减5元", "已售16544"));
                mList.add(setData(R.drawable.movie, "连影卡通电影", "[9店通用] 电影票1张，可观看2D/3D,提供免费WiFi", "27元", "120元", "已售44583"));
                mList.add(setData(R.drawable.ms1, "爱的可自助海鲜烤肉", "[春熙路] 免费Wifi", "59元", "89元", "已售107425"));
                mList.add(setData(R.drawable.ms2, "撸老爹猪脚", "[鹏瑞利.清扬广场] 其它2选1，免费WiFi", "2.99元", "7元", "已售21528"));
                mList.add(setData(R.drawable.ms3, "澳洲肥牛捞捞锅", "[东大街] 单人自助餐", "55元", "68元", "已售30833"));
                mList.add(setData(R.drawable.ms4, "九里.烤肉", "[2店通用] 晚餐自助，免费Wifi", "65元", "78元", "42807"));
            }

            return mList;
        }
    }

    class ViewHolderH {
        ImageView img;
        TextView textView1, textView2, textView3, textView4, textView5;

    }

    class Info {
        private int img;
        private String text1, text2, text3, text4, text5;

        public void setImg(int img) {
            this.img = img;
        }

        public int getImg() {
            return img;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getText1() {
            return text1;
        }

        public void setText2(String text2) {
            this.text2 = text2;
        }

        public String getText2() {
            return text2;
        }

        public void setText3(String text3) {
            this.text3 = text3;
        }

        public String getText3() {
            return text3;
        }

        public void setText4(String text4) {
            this.text4 = text4;
        }

        public String getText4() {
            return text4;
        }

        public void setText5(String text5) {
            this.text5 = text5;
        }

        public String getText5() {
            return text5;
        }

    }

    public ArrayAdapter getArrayAdapter(int arraySrcId) {
        return ArrayAdapter.createFromResource(getActivity(), arraySrcId, android.R.layout.simple_spinner_dropdown_item);
    }


}
