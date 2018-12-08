package com.luwei.itemdecorationdemo.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.luwei.itemdecorationdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/6
 */
public class GridSpaceActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvTest;
    Button btnOrientation;
    private GridAdapter mAdapter;
    int layoutVertical;
    int layoutHorizontal;
    private boolean isVertical = true;
    private GridSpaceDecoration decoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        initView();
    }

    private void initView() {
        layoutVertical = R.layout.item_sample;
        layoutHorizontal = R.layout.item_grid_horizontal;
        rvTest = findViewById(R.id.rv_test);
        btnOrientation = findViewById(R.id.btn_orientation);
        btnOrientation.setOnClickListener(this);
        reset(new GridSpaceDecoration(50, 50, 0, 0, 100, 0), LinearLayout.VERTICAL);
    }

    private List<String> createList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add("第" + i + "个");
        }
        return dataList;
    }

    @Override
    public void onClick(View v) {
        if (!isVertical) {
            reset(new GridSpaceDecoration(50, 50, 50, 50, 50, 50), LinearLayout.VERTICAL);
            isVertical = true;
        } else {
            reset(new GridSpaceDecoration(50, 50, 50, 50, 50, 50), LinearLayout.HORIZONTAL);
            isVertical = false;
        }
    }

    public void reset(GridSpaceDecoration decoration, int orientation) {
        mAdapter = new GridAdapter(createList(), orientation == LinearLayoutManager.VERTICAL ?
                layoutVertical : layoutHorizontal);
        GridLayoutManager manager = new GridLayoutManager(this, 4, orientation, false);
        if (orientation == LinearLayoutManager.VERTICAL) {
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    if (i == 4 || i == 13) {
                        return 3;
                    }
                    if (i == 12) {
                        return 2;
                    }
                    return 1;
                }
            });
        }
        rvTest.setLayoutManager(manager);
        rvTest.removeItemDecoration(this.decoration);
        this.decoration = decoration;
        rvTest.addItemDecoration(decoration);
        rvTest.setAdapter(mAdapter);
    }

}
