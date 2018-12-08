package com.luwei.itemdecorationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.luwei.itemdecorationdemo.grid.GridSpaceActivity;
import com.luwei.itemdecorationdemo.grid.TableDividerActivity;
import com.luwei.itemdecorationdemo.linear.CustomDividerActivity;
import com.luwei.itemdecorationdemo.linear.SimpleDividerActivity;
import com.luwei.itemdecorationdemo.staggered.StaggeredActivity;
import com.luwei.itemdecorationdemo.stick.StickHeaderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_simple_divider).setOnClickListener(this);
        findViewById(R.id.btn_custom_divider).setOnClickListener(this);
        findViewById(R.id.btn_grid_space).setOnClickListener(this);
        findViewById(R.id.btn_table).setOnClickListener(this);
        findViewById(R.id.btn_stick_header).setOnClickListener(this);
        findViewById(R.id.btn_staggered).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_simple_divider:
                //简单分割线
                startActivity(new Intent(this,SimpleDividerActivity.class));
                break;
            case R.id.btn_custom_divider:
                //自定义分割线
                startActivity(new Intent(this, CustomDividerActivity.class));
                break;
            case R.id.btn_grid_space:
                //网格布局间隔
                startActivity(new Intent(this, GridSpaceActivity.class));
                break;
            case R.id.btn_table:
                //表格分割线
                startActivity(new Intent(this, TableDividerActivity.class));
                break;
            case R.id.btn_stick_header:
                //粘性头部
                startActivity(new Intent(this, StickHeaderActivity.class));
                break;
            case R.id.btn_staggered:
                //瀑布流间隔
                startActivity(new Intent(this, StaggeredActivity.class));
                break;
        }
    }
}
