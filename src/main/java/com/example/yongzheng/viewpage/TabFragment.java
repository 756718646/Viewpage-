package com.example.yongzheng.viewpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yongzheng on 2016/5/29.
 */
public class TabFragment extends Fragment {

    private int position = 0;
    private TextView tv_test;

    private final static String ID = "id";
    private String id;

    public TabFragment(){
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle  =getArguments();
        if (bundle!=null){
            id = bundle.getString(ID);
        }
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        tv_test = (TextView) view.findViewById(R.id.text);
        tv_test.setText("这是第" + (position + 1) + "个页面");
        return view;
    }

    public static Fragment newInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
