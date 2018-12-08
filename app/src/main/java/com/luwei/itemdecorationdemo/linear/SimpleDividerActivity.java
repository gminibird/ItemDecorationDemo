package com.luwei.itemdecorationdemo.linear;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class SimpleDividerActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvTest;
    private SampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_divider);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_padding).setOnClickListener(this);
        rvTest = findViewById(R.id.rv_test);
        mAdapter = new SampleAdapter(createList());
        rvTest.setAdapter(mAdapter);
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        rvTest.addItemDecoration(new SimpleDividerDecoration());
    }

    private List<String> createList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("第" + i + "个 Item");
        }
        return dataList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_padding:
                rvTest.setPadding(20,20,20,20);
                break;
        }
    }
}
