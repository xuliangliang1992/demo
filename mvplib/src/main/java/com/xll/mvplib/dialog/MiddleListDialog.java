package com.xll.mvplib.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.view.ItemClickListener;
import com.xll.mvplib.view.decoration.HorizontalDividerItemDecoration;

/**
 * @author xll
 * @date 2018/1/1
 */
public class MiddleListDialog extends BaseDialog {

    private String title;
    private ItemClickListener mOnItemClickListener;

    private TextView tvTitle;

    private SparseArray<String> listData;

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public MiddleListDialog(Context context) {
        super(context);
    }

    public MiddleListDialog(Context context, String title, SparseArray<String> data, ItemClickListener onItemClickListener) {
        super(context);
        this.title = title;
        this.listData = data;
        this.mOnItemClickListener = onItemClickListener;
        setCancel(false);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.middle_list_dialog, null);
        widthScale(0.8f);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lv_address);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ItemAdapter adapter = new ItemAdapter();
        adapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(adapter);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(context)
                .color(ContextCompat.getColor(context, R.color.base_bg_color))
                .sizeResId(R.dimen.y3).build();
        recyclerView.addItemDecoration(decoration);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        tvTitle.setText(title);
        return false;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

        private ItemClickListener itemClickListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.one_item, parent, false);
            return new MyViewHolder(view, mOnItemClickListener);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.mTextView.setText(listData.valueAt(position));
        }

        @Override
        public int getItemCount() {
            if (listData != null) {
                return listData.size();
            }
            return 0;
        }

        public void setOnItemClickListener(ItemClickListener onItemClickListener) {
            if (itemClickListener != null) {
                itemClickListener = onItemClickListener;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public MyViewHolder(View itemView, final ItemClickListener onItemClickListener) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_item);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClickListener(v, getAdapterPosition());
                    }
                });
            }
        }

    }
}
