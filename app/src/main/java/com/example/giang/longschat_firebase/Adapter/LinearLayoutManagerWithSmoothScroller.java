package com.example.giang.longschat_firebase.Adapter;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

/**
 * Created by giang on 5/13/2016.
 */
public class LinearLayoutManagerWithSmoothScroller extends LinearLayoutManager {

    private Context mContext;

    public LinearLayoutManagerWithSmoothScroller(Context context) {
        super(context);
        mContext = context;
    }

    //Override this method? Check.
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView,
                                       RecyclerView.State state, int position) {

        //Create your RecyclerView.SmoothScroller instance? Check.
        LinearSmoothScroller smoothScroller =
                new LinearSmoothScroller(mContext) {

                    //Automatically implements this method on instantiation.
                    @Override
                    public PointF computeScrollVectorForPosition
                    (int targetPosition) {
                        return null;
                    }
                };

        //Docs do not tell us anything about this,
        //but we need to set the position we want to scroll to.
        smoothScroller.setTargetPosition(position);

        //Call startSmoothScroll(SmoothScroller)? Check.
        startSmoothScroll(smoothScroller);
    }
}