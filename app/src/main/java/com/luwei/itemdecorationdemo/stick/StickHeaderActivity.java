package com.luwei.itemdecorationdemo.stick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.luwei.itemdecorationdemo.R;
import com.luwei.itemdecorationdemo.SampleAdapter;
import com.luwei.itemdecorationdemo.linear.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/4
 */
public class StickHeaderActivity extends AppCompatActivity {

    RecyclerView rvTest;
    private StickAdapter mAdapter;
    private List<StickBean> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick);
        initView();
    }

    private void initView() {
        mList = createList();
        rvTest = findViewById(R.id.rv_test);
        mAdapter = new StickAdapter(mList);
        rvTest.setAdapter(mAdapter);
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        StickHeaderDecoration decoration = new StickHeaderDecoration(mAdapter);
        rvTest.addItemDecoration(decoration);
    }

    private List<StickBean> createList() {
        List<StickBean> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if (i % 5 == 0) {
                dataList.add(new StickBean("第" + i + "个 Item", StickBean.TYPE_HEADER));
            } else if (i % 8 == 0) {
                dataList.add(new StickBean("第" + i + "个 Item", StickBean.TYPE_HEADER_2));
            } else {
                dataList.add(new StickBean("第" + i + "个 Item", StickBean.TYPE_CONTENT));
            }
        }
        return dataList;
    }
}
