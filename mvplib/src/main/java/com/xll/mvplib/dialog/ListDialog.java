package com.xll.mvplib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.InputMethodUtil;
import com.xll.mvplib.utils.StringUtil;
import com.xll.mvplib.view.EmptyRecyclerView;
import com.xll.mvplib.view.ItemClickListener;
import com.xll.mvplib.view.decoration.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 全屏dialog
 *
 * @author xll
 * @date 2018/1/1
 */
public class ListDialog extends BaseDialog {

    private String title;
    private List<Map<String, String>> data;
    private ItemClickListener mOnItemClickListener;

    private List<Map<String, String>> copyData;
    private ItemAdapter adapter;
    private ImageView ivBack;
    private TextView tvTitle;

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public ListDialog(Context context) {
        super(context);
    }

    public ListDialog(Context context, String title, List<Map<String, String>> data, ItemClickListener onItemClickListener) {
        super(context);
        this.title = title;
        this.data = data;
        this.mOnItemClickListener = onItemClickListener;
        filterData();
        copyData = new ArrayList<>(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.list_dialog, null);
        widthScale(1);

        final EmptyRecyclerView recyclerView = (EmptyRecyclerView) view.findViewById(R.id.rv_list);
        RelativeLayout emptyView = (RelativeLayout) view.findViewById(R.id.ll_emptyview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ItemAdapter();
        adapter.setOnItemClickListener(mOnItemClickListener);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(context)
                .color(ContextCompat.getColor(context, R.color.base_bg_color))
                .sizeResId(R.dimen.y3).build();
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);

        final SearchView searchView = (SearchView) view.findViewById(R.id.search_view);
        final EditText editText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.base_black_9));
        editText.setTextSize(14);
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.base_black_3));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!StringUtil.isStringNull(query)) {
                    InputMethodUtil.hideSoftInput(getContext(), searchView);
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    private void filterData() {
        //过滤掉uc的经销商
        if (data != null && data.size() > 0) {
            Iterator<Map<String, String>> iterator = data.iterator();
            while (iterator.hasNext()) {
                Map<String, String> map = iterator.next();
                String name = map.get("name");
                if (!StringUtil.isStringNull(name) && name.toLowerCase().contains("uc")) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public boolean setUiBeforeShow() {
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return false;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> implements Filterable {

        private ItemClickListener itemClickListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.one_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClickListener(v, position);
                        dismiss();
                    }
                });
            }
            holder.mTextView.setText(data.get(position).get("name"));
        }

        @Override
        public int getItemCount() {
            if (data != null) {
                return data.size();
            }
            return 0;
        }

        public void setOnItemClickListener(ItemClickListener onItemClickListener) {
            if (itemClickListener != null) {
                itemClickListener = onItemClickListener;
            }
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (filterResults.values == null) {
                        data.clear();
                        data.addAll(copyData);
                    }

                    if (constraint == null || constraint.length() == 0) {
                        filterResults.values = copyData;
                        filterResults.count = copyData.size();
                    } else {
                        String prefixString = constraint.toString();
                        ArrayList<Map<String, String>> queryData = new ArrayList<>();
                        for (Map<String, String> map : data) {
                            String name = map.get("name").toLowerCase();
                            if (name.contains(prefixString.toLowerCase())) {
                                queryData.add(map);
                            }
                        }
                        filterResults.values = queryData;
                        filterResults.count = queryData.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    data.clear();
                    data.addAll((List<Map<String, String>>) results.values);
                    adapter.notifyDataSetChanged();
                }
            };
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_item);
            }
        }

    }
}
