package com.mu.mothersunited;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CreateQuestionActivity extends ActionBarActivity
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Mothers United");
    }

}
