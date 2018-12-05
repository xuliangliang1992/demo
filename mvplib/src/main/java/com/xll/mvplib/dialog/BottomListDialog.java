package com.xll.mvplib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BottomBaseDialog;
import com.xll.mvplib.view.ItemClickListener;
import com.xll.mvplib.view.decoration.HorizontalDividerItemDecoration;

/**
 * 底部有取消按钮的弹出框 从底部弹出
 *
 * @author xll
 * @date 2018/1/1
 */
public class BottomListDialog extends BottomBaseDialog {
    private String[] titles;
    private ItemClickListener mItemClickListener;

    public BottomListDialog(Context context, String[] titles, ItemClickListener itemClickListener) {
        super(context);
        this.titles = titles;
        this.mItemClickListener = itemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.dialog_bottom_layout, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(context)
                .color(context.getResources().getColor(R.color.base_black_9))
                .sizeResId(R.dimen.y1).build();
        recyclerView.addItemDecoration(decoration);
        ItemAdapter mItemAdapter = new ItemAdapter();
        mItemAdapter.setItemClickListener(mItemClickListener);
        recyclerView.setAdapter(mItemAdapter);
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        return true;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

        private ItemClickListener itemClickListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.text_layout, parent, false);
            return new MyViewHolder(view, itemClickListener);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.mTextView.setText(titles[position]);
        }

        @Override
        public int getItemCount() {
            if (titles != null) {
                return titles.length;
            }
            return 0;
        }

        public void setItemClickListener(ItemClickListener onItemClickListener) {
            itemClickListener = onItemClickListener;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public MyViewHolder(View itemView, final ItemClickListener itemClickListener) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClickListener(v, getAdapterPosition());
                            dismiss();
                        }
                    }
                });
            }
        }
    }
}
