package com.example.yongzheng.viewpage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongzheng on 2016/05/29.
 */
public class PeoplePowerFragment extends  Fragment{

    private ParentViewPager vp_indicator;

    private ChilderAdapter adapter;

    private TabLayout tabLayout;

    private List<String> datas = new ArrayList<>();//数据


    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle  =getArguments();
        View view = inflater.inflate(R.layout.fragment_power, container, false);
        vp_indicator = (ParentViewPager) view.findViewById(R.id.vp_indicator);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        init();
        return view;
    }

    private void init() {
        for (int i=0;i<10;i++){
            datas.add("tab"+i);
        }
        adapter = new ChilderAdapter(getChildFragmentManager());

        vp_indicator.setAdapter(adapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setupWithViewPager(vp_indicator);

    }

    public static Fragment newInstance(String id){
        Bundle bundle = new Bundle();
        PeoplePowerFragment fragment = new PeoplePowerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private class ChilderAdapter extends FragmentPagerAdapter
    {
        public ChilderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return RecycleFragment2.newInstance("");
            }
                return new TabFragment();
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return datas.get(position);
        }

    }


}
