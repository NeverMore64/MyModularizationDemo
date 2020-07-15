package com.example.basemodule.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by zy on 2020/7/14
 * </p>
 */
public abstract class BaseAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<E> mList;

    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;

    public BaseAdapter() {
        this.mList = new ArrayList<>();
    }

    public interface OnItemClickListener<E> {
        void onItemClick(E e, int position);
    }

    public interface OnItemLongClickListener<E> {
        boolean onItemLongClick(E e, int postion);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mItemLongClickListener = onItemLongClickListener;
    }

    public abstract int onBindLayout();

    protected abstract VH onCreateViewHolder(View view);

    protected abstract void onBindData(VH holder, E E, int position);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = onBindLayout();
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return onCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        final E e = mList.get(position);
        onBindData(holder, e, position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(e, position);
                }
            });
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mItemLongClickListener.onItemLongClick(e, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<E> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setData(List<E> list) {
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void remove(int positon) {
        mList.remove(positon);
        notifyDataSetChanged();
    }

    public void remove(E e) {
        mList.remove(e);
        notifyDataSetChanged();
    }

    public void add(E e) {
        mList.add(e);
        notifyDataSetChanged();
    }

    public void addLast(E e) {
        add(e);
    }

    public void addFirst(E e) {
        mList.add(0, e);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<E> getDataList() {
        return mList;
    }

}
