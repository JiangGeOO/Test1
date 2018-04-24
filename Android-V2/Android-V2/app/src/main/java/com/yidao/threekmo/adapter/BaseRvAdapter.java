package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/6/1.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected LayoutInflater layoutInflater;
    protected Context context;
    protected List<T> dataList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    public OnButtonClickListener onButtonClickListener;
    public BaseRvAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<T>();
    }
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener = onButtonClickListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
    public interface OnButtonClickListener{
        void itemButtonListener(int position);
    }
    public interface OnItemClickListener {
        void itemClickLIstener(View view, int position);
    }
    public interface OnItemLongClickListener{
        void itemLongClickListener(View view, int position);
    }
    public void addSingleData(T t){
        dataList.add(t);
        notifyDataSetChanged();
    }
    public void addSingleData(int position,T t){
        dataList.add(position,t);
        notifyDataSetChanged();
    }
    public void addData(List<T> dataList){
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
    public void dataUpdate(List<T> dataList){
        this.dataList.clear();
        if (dataList != null) {
            this.dataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }
    public void dataClear(){
        this.dataList.clear();
        notifyDataSetChanged();
    }

    public void delateDateAt(int position){
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void delateDateAt1(int position){
        dataList.remove(position);
        notifyDataSetChanged();
    }


    public List<T> getDatas(){
        return dataList;
    }
    protected class BaseViewHolder extends RecyclerView.ViewHolder{
        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != onItemClickListener) {
                        onItemClickListener.itemClickLIstener(v, getLayoutPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(null != onItemLongClickListener){
                        onItemLongClickListener.itemLongClickListener(v,getLayoutPosition());
                    }
                    return false;
                }
            });
        }
    }
}