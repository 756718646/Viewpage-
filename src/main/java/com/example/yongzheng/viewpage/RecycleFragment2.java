package com.example.yongzheng.viewpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongzheng on 2016/5/29.
 */
public class RecycleFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecycleAdapter.OnFooterListener {

    private int position = 0;
    private TextView tv_test;

    private final static String ID = "id";
    private String id;

    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh;

    private List<String> datas = null;
    private RecycleAdapter adapter;

    private LinearLayout foot_body;

    public RecycleFragment2(){
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        foot_body = (LinearLayout) view.findViewById(R.id.foot_body);
        foot_body.setVisibility(View.GONE);
        refresh.setOnRefreshListener(this);

        init();
        return view;
    }

    private void init() {

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        refresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

//        refresh.setRefreshing(true);

        datas = new ArrayList<>();
        for (int i=0;i<30;i++){
            datas.add("数据:"+i);
        }

        recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),4));
        adapter = new RecycleAdapter(datas,getActivity());
        recycler_view.setAdapter(adapter);
        adapter.setOnLoadListener(this);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    public static Fragment newInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        RecycleFragment2 fragment = new RecycleFragment2();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        recycler_view.postDelayed(new Runnable() {

            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    datas.add("数据-:"+i);
                }
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        },3000);
    }

    @Override
    public void onFooterLisnter() {

        System.out.println("数据大小:"+datas.size());

        if (datas.size()>50){
            try{
                adapter.setLoading(false);
                adapter.setShowFooter(false);
                foot_body.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }
        foot_body.setVisibility(View.VISIBLE);
        System.out.println("加载更多onFooterLisnter");
        recycler_view.postDelayed(new Runnable() {

            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    datas.add("加载更多-:"+i);
                }
                System.out.println("加载完成");
                adapter.setLoading(false);
                foot_body.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        },5000);
    }
}
