package com.luwei.itemdecorationdemo.linear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luwei.itemdecorationdemo.R;
import com.luwei.itemdecorationdemo.SampleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/26
 */
public class CustomDividerActivity extends AppCompatActivity {
    RecyclerView rvTest;
    private SampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_divider);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_padding).setVisibility(View.GONE);
        rvTest = findViewById(R.id.rv_test);
        mAdapter = new SampleAdapter(createList());
        rvTest.setAdapter(mAdapter);
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_gradient));
        rvTest.addItemDecoration(decoration);
    }

    private List<String> createList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("第" + i + "个 Item");
        }
        return dataList;
    }
}
