package com.example.yongzheng.viewpage;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static String[] strs = new String[]{"tab1","tab2","tab3","tab4"};

    private TabLayout tabs;

    private ViewPager viewpager;
    private ChilderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tabs = (TabLayout) super.findViewById(R.id.tabs);
        this.viewpager = (ViewPager) super.findViewById(R.id.viewpager);

//        for (String t : strs){
//            tabs.addTab(tabs.newTab().setText(t));
//        }

        adapter = new ChilderAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);
    }



    private class ChilderAdapter extends FragmentPagerAdapter{

        public ChilderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return RecycleFragment.newInstance(""+position);
            }else if (position==1){
                return PeoplePowerFragment.newInstance(""+ position);
            }
            return TabFragment.newInstance(""+position);
        }

        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strs[position];
        }
    }

}
