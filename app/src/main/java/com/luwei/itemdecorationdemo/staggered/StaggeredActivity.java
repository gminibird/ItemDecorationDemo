package com.luwei.itemdecorationdemo.staggered;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.luwei.itemdecorationdemo.R;
import com.luwei.itemdecorationdemo.SampleAdapter;
import com.luwei.itemdecorationdemo.linear.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/5
 */
public class StaggeredActivity extends AppCompatActivity {

    RecyclerView rvTest;
    private StaggeredAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_divider);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_padding).setVisibility(View.GONE);
        rvTest = findViewById(R.id.rv_test);
        rvTest.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mAdapter = new StaggeredAdapter(createList());
        rvTest.setAdapter(mAdapter);
        rvTest.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rvTest.addItemDecoration(new StaggeredSpaceDecoration(30,30,50,40,60,70));
    }

    private List<String> createList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            dataList.add("第" + i + "个 Item");
        }
        return dataList;
    }
}
