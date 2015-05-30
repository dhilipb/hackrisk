package com.mu.mothersunited;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    @InjectView(R.id.list_questions)
    RecyclerView listQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("Private"));
        tabLayout.addTab(tabLayout.newTab().setText("Public"));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listQuestions.setLayoutManager(layoutManager);

    }

}
