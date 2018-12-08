package com.luwei.itemdecorationdemo.stick;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luwei.itemdecorationdemo.R;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/4
 */
public class StickAdapter extends RecyclerView.Adapter<StickAdapter.ViewHolder> implements StickHeaderDecoration.StickProvider {

    private List<StickBean> mList;

    public StickAdapter(@NonNull List<StickBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case StickBean.TYPE_CONTENT:
            case StickBean.TYPE_HEADER:
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_sample, viewGroup, false);
                return new ViewHolder(view);
            case StickBean.TYPE_HEADER_2:
                View header2 = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_stick_header_2, viewGroup, false);
                return new ViewHolder(header2);
        }
        return new ViewHolder(new View(viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        StickBean bean = mList.get(i);
        viewHolder.tvContent.setText(bean.getContent());
        switch (bean.getType()) {
            case StickBean.TYPE_HEADER:
                viewHolder.tvContent.setBackgroundColor(viewHolder.itemView
                        .getContext().getResources().getColor(R.color.colorAccent));
                break;
            case StickBean.TYPE_HEADER_2:
                viewHolder.tvContent.setBackgroundColor(viewHolder.itemView
                        .getContext().getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public boolean isStick(int position) {
        return mList.get(position).type == StickBean.TYPE_HEADER
                || mList.get(position).type == StickBean.TYPE_HEADER_2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
