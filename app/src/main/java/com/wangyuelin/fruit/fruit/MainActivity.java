package com.wangyuelin.fruit.fruit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wangyuelin.fruit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivtiy {

    @BindView(R.id.ls_top)
    RecyclerView lsTop;
    @BindView(R.id.ls_left)
    RecyclerView lsLeft;
    @BindView(R.id.ls_right)
    RecyclerView lsRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }




}
