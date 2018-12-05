package com.byrj.pet.fun;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.byrj.pet.base.BaseFragment;
import com.byrj.pet.fun.information.InformationFragment;
import com.byrj.pet.fun.oneself.OneselfFragment;
import com.byrj.pet.fun.square.SquareFragment;
import com.byrj.pet.fun.star.StarFragment;
import com.byrj.pet.pet.R;
import com.xll.mvplib.view.NoSwipeViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xll
 * @date 2018/12/3
 */

public class MainFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.view_pager)
    NoSwipeViewPager mViewPager;
    @BindView(R.id.iv_star)
    ImageView mIvStar;
    @BindView(R.id.tv_star)
    TextView mTvStar;
    @BindView(R.id.iv_square)
    ImageView mIvSquare;
    @BindView(R.id.tv_square)
    TextView mTvSquare;
    @BindView(R.id.iv_information)
    ImageView mIvInformation;
    @BindView(R.id.tv_information)
    TextView mTvInformation;
    @BindView(R.id.iv_oneself)
    ImageView mIvOneself;
    @BindView(R.id.tv_oneself)
    TextView mTvOneself;

    private int[] imgResNormal, imgResSelected;
    private ImageView[] mImageViews;
    private TextView[] mTextViews;
    private List<BaseFragment> fragments;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initResource();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        mImageViews = new ImageView[]{mIvStar, mIvSquare, mIvInformation, mIvOneself};
        mTextViews = new TextView[]{mTvStar, mTvSquare, mTvInformation, mTvOneself};

        MainPageAdapter mAdapter = new MainPageAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        changeView(0);
        initListener();
        return view;
    }

    private void initListener() {

    }

    public void switchFragment(int position) {
        mViewPager.setCurrentItem(position);
    }

    private void initResource() {
        fragments = new ArrayList<>();
        imgResNormal = new int[]{R.drawable.star_normal, R.drawable.square_normal,
                R.drawable.information_normal, R.drawable.oneself_normal};
        imgResSelected = new int[]{R.drawable.star_selected, R.drawable.square_selected,
                R.drawable.information_selected, R.drawable.oneself_selected};

        fragments.add(StarFragment.newInstance());
        fragments.add(SquareFragment.newInstance());
        fragments.add(InformationFragment.newInstance());
        fragments.add(OneselfFragment.newInstance());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_star, R.id.ll_square, R.id.ll_plus, R.id.ll_information, R.id.ll_oneself})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_star:
                changeView(0);
                switchFragment(0);
                break;
            case R.id.ll_square:
                changeView(1);
                switchFragment(1);
                break;
            case R.id.ll_plus:
                break;
            case R.id.ll_information:
                changeView(2);
                switchFragment(2);
                break;
            case R.id.ll_oneself:
                changeView(3);
                switchFragment(3);
                break;
        }
    }

    private void changeView(int position) {
        for (int i = 0; i < mImageViews.length; i++) {
            if (i == position) {
                mImageViews[i].setImageResource(imgResSelected[i]);
                mTextViews[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.material_blue));
            } else {
                mImageViews[i].setImageResource(imgResNormal[i]);
                mTextViews[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.black_3));
            }
        }
    }

    public class MainPageAdapter extends FragmentStatePagerAdapter {

        private List<BaseFragment> mFragmentList;

        private MainPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.mFragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            if (mFragmentList != null) {
                return mFragmentList.size();
            }
            return -1;
        }
    }
}
