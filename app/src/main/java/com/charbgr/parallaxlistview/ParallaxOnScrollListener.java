package com.charbgr.parallaxlistview;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AbsListView;

import java.lang.ref.WeakReference;

/**
 * Created by charbgr on 7/24/14.
 */
public class ParallaxOnScrollListener implements AbsListView.OnScrollListener {

    private boolean mIsScrollingUp;
    private int oldTop;
    private int oldFirstVisibleItem;

    private static int clippingImageViewId;

    public ParallaxOnScrollListener(int clippingImageViewId){
        this.clippingImageViewId = clippingImageViewId;
    }


    public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

    /**
     * Callback method to be invoked when the list or grid has been scrolled. This will be
     * called after the scroll has completed
     *
     * @param view             The view whose scroll state is being reported
     * @param firstVisibleItem the index of the first visible cell (ignore if
     *                         visibleItemCount == 0)
     * @param visibleItemCount the number of visible cells
     * @param totalItemCount   the number of items in the list adaptor
     */
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        View view = absListView.getChildAt(0);
        int top = (view == null) ? 0 : view.getTop();

        if (firstVisibleItem == oldFirstVisibleItem) {
            if (top > oldTop) {
                mIsScrollingUp = true;
            } else if (top < oldTop) {
                mIsScrollingUp = false;
            }
        } else {
            if (firstVisibleItem < oldFirstVisibleItem) {
                mIsScrollingUp = true;
            } else {
                mIsScrollingUp = false;
            }
        }

        oldTop = top;
        oldFirstVisibleItem = firstVisibleItem;


        if (view != null) {
            if (mIsScrollingUp) {
                System.out.println("scrolling up");
                translateAllViewsUp(absListView, firstVisibleItem, totalItemCount);
            } else {
                System.out.println("falling down");
                translateAllViewsDown(absListView, firstVisibleItem, totalItemCount);
            }
        }
    }

    public void translateAllViewsUp(AbsListView absListView, int firstVisibleItem, int lastVisibleItems) {
        for(int i = firstVisibleItem; i < lastVisibleItems; ++i) {
            final View view = absListView.getChildAt(i);
            if (view != null) {
                ((ClippingImageView) view.findViewById(clippingImageViewId))
                        .move(ClippingImageView.UP);
            }
        }
    }

    public void translateAllViewsDown(AbsListView absListView, int firstVisibleItem, int lastVisibleItems) {
        for(int i = firstVisibleItem; i < lastVisibleItems; ++i) {
            final View view = absListView.getChildAt(i);
            if (view != null) {
                ((ClippingImageView) view.findViewById(clippingImageViewId))
                        .move(ClippingImageView.DOWN);
            }
        }
    }
}

