package com.wangyuelin.fruit.fruit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangyuelin.fruit.R;
import com.wangyuelin.fruit.utils.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyuelin on 2018/2/1.
 * <p>
 * 提供统一的常用的页面状态：加载中、失败、为空
 * 提供titlebar
 * 考虑到使用用户自己的titlebar的情况
 * <p>
 * 显示页面的状态思路：动态在mStatusContainer里面添加view，所以可以将mStatusContainer设置为别的，达到将状态显示到别地的目的
 */

public class BaseUIActivity extends BaseRxActivity {


    private View mRootView;

    private LayoutInflater mInflater;
    private View mErrorView;//错误状态的view
    private View mEmptyView;//空状态的view

    private ViewGroup mStatusContainer;//状态View的容器
    private boolean mIsShowTitleBar;//是否显示标题
    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        init();
        mInflater.inflate(layoutResID, mViewHolder.mContent);


    }

    private void init() {
        mRootView = mInflater.inflate(R.layout.activity_baseui, null);
        mViewHolder = new ViewHolder(mRootView);
        super.setContentView(mRootView);
        mStatusContainer = mViewHolder.mContent;
        mViewHolder.rlTitleWhole.setVisibility(mIsShowTitleBar ? View.VISIBLE : View.INVISIBLE);

    }


    /**
     * 显示错误的状态
     */
    protected void showErrorView() {
        if (mErrorView == null) {
            mErrorView = mInflater.inflate(R.layout.view_error, null);
        }
        ViewUtil.hiddenChildern(mStatusContainer);
        mStatusContainer.removeView(mErrorView);
        mStatusContainer.addView(mErrorView);

    }

    /**
     * 显示错误的状态
     */
    protected void showEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = mInflater.inflate(R.layout.view_empty, null);
        }
        ViewUtil.hiddenChildern(mStatusContainer);
        mStatusContainer.removeView(mEmptyView);
        mStatusContainer.addView(mEmptyView);
    }

    /**
     * 设置显示状态的ViewGroup
     *
     * @param statusContainer
     */
    public void setStatusContainer(ViewGroup statusContainer) {
        this.mStatusContainer = statusContainer;
    }

    /**
     * 是否显示标题，子类必须得在调用setContentView方法之前调用
     * @param isShowTitleBar
     */
    public void setIsShowTitleBar(boolean isShowTitleBar) {
        this.mIsShowTitleBar = isShowTitleBar;
    }


    public static class ViewHolder{
        @BindView(R.id.iv_left)
        ImageView ivLeft;
        @BindView(R.id.tv_left)
        TextView tvLeft;
        @BindView(R.id.ll_left)
        LinearLayout llLeft;
        @BindView(R.id.tv_center)
        TextView tvCenter;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.tv_right)
        TextView tvRight;
        @BindView(R.id.ll_right)
        LinearLayout llRight;
        @BindView(R.id.fl_content)
        ViewGroup mContent;
        @BindView(R.id.rl_title_whole)
        RelativeLayout rlTitleWhole;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
