package com.example.yongzheng.viewpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yongzheng on 2016/5/29.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> datas = null;

    private Context context;

    private static final int TYPE_ITEM = 0;

    private static final int TYPE_FOOTER = 1;

    private OnFooterListener footerListener;

    private boolean isShowFooter = true;//是否显示加载更多(外部判断是否显示)

    private boolean isloading = false;//是否在加载中（内部过滤重复回调）

    /**
     * 获取是否加载
     * @return
     */
    public boolean getIsloading(){
        return isShowFooter;
    }



    public interface OnFooterListener{
        public void onFooterLisnter();
    }

    public void setOnLoadListener(OnFooterListener onFooterListener){
        this.footerListener = onFooterListener;
    }

    //是否加载完成
    public void setLoading(boolean isload){
        this.isloading = isload;
    }

    //是否隐藏加载更多
    public void setShowFooter(boolean isShowFooter){
        this.isShowFooter = isShowFooter;
        notifyDataSetChanged();
    }

    public RecycleAdapter(List<String> datas, Context context){
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Hold holder = new Hold(LayoutInflater.from(
//                context).inflate(R.layout.item_view, parent,
//                false));
//        return holder;

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_view, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT));
            return new Hold(view);
        }
        // type == TYPE_FOOTER 返回footerView
        else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footerview_hide, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }

        return null;



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Hold){
            ((Hold)holder).text.setText("数据:"+datas.get(position));
        }else if (holder instanceof FooterViewHolder){
            //每次最底部
            FooterViewHolder  foothold = (FooterViewHolder)holder;
            if (footerListener!=null){
                if (isShowFooter){
                    foothold.foot_body.setVisibility(View.VISIBLE);
                    if (isloading==false){
                        isloading = true;//正在请求处理
                        foothold.foot_body.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                footerListener.onFooterLisnter();
                            }
                        },500);
                    }
                }else {
                    foothold.foot_body.setVisibility(View.GONE);
                }
            }else {
                foothold.foot_body.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    class Hold extends RecyclerView.ViewHolder {

        private TextView text;

        public Hold(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    //脚步加载更多
    class FooterViewHolder extends RecyclerView.ViewHolder {

        LinearLayout foot_body;

        public FooterViewHolder(View view) {
            super(view);
            foot_body = (LinearLayout) view.findViewById(R.id.foot_body);
            System.out.println("生成加载更多view");
        }
    }

}
