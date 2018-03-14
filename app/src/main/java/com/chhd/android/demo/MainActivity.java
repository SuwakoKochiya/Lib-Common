package com.chhd.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chhd.android.common.ui.activity.ToolbarActivity;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance, DemoList1Activity.class));
            }
        });

        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance, DemoList2Activity.class));
            }
        });
    }

    @Override
    public int getContainerResId() {
        return R.layout.activity_main;
    }
}
