package com.mu.mothersunited;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class CardSwipeView extends ViewGroup
{

    public CardSwipeView(Context context)
    {
        super(context);
    }

    public CardSwipeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CardSwipeView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CardSwipeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {

    }

}
