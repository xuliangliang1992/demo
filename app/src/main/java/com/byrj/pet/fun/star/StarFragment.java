package com.byrj.pet.fun.star;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byrj.pet.base.BaseFragment;
import com.byrj.pet.pet.R;

/**
 * @author xll
 * @date 2018/12/4
 */
public class StarFragment extends BaseFragment {

    public static StarFragment newInstance() {
        return new StarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.star_fragment, container, false);
        return view;
    }
}
