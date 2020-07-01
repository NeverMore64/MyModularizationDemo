package com.example.moduledemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mzule.activityrouter.router.Routers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnModuleA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Routers.open(MainActivity.this, "jump_module://moduleA_main_activity");
            }
        });

        findViewById(R.id.btnModuleB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Routers.open(MainActivity.this, "jump_module://moduleB_main_activity");
            }
        });

    }
}
