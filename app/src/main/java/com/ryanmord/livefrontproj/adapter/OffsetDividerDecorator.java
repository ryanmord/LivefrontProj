package com.ryanmord.livefrontproj.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.util.UiUtils;

/**
 * Created by ryanmord on 7/21/17.
 */

public class OffsetDividerDecorator extends RecyclerView.ItemDecoration  {

    /**
     * Divider drawable
     */
    private Drawable mDivider;


    public OffsetDividerDecorator(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.drawable_line);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = 0;
        int right = parent.getWidth();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

}
