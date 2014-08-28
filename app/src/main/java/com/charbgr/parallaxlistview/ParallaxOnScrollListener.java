package com.charbgr.parallaxlistview;

import android.view.View;
import android.widget.AbsListView;

/**
 * Created by charbgr on 7/24/14.
 */
public class ParallaxOnScrollListener implements AbsListView.OnScrollListener {

    private boolean mIsScrollingUp;
    private int oldTop;
    private int oldFirstVisibleItem;

    private static int clippingImageViewId;

    public ParallaxOnScrollListener(int clippingImageViewId) {
        this.clippingImageViewId = clippingImageViewId;
    }


    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /**
     * Callback method to be invoked when the list or grid has been scrolled. This will be
     * called after the scroll has completed
     *
     * @param absListView      The view whose scroll state is being reported
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
            translateAllViews(absListView, firstVisibleItem, visibleItemCount,
                    mIsScrollingUp ? ClippingImageView.UP : ClippingImageView.DOWN);
        }
    }

    private void translateAllViews(AbsListView absListView,
                                         int firstVisibleItem,
                                         int lastVisibleItems,
                                         int direction) {

        int referencePoint = ((CustomListView)absListView).getReferencePoint();

        for (int i = firstVisibleItem; i < lastVisibleItems; ++i) {
            final View view = absListView.getChildAt(i);

            int coords[] = new int[2];
            view.getLocationOnScreen(coords);

            int y = coords[1];
            int factor;

            // must calculate it right
            if(y+200 < referencePoint)
                factor = 1;
            else
                factor = 5;

            if (view != null) {
                ((ClippingImageView) view.findViewById(clippingImageViewId))
                        .move(direction, factor);
            }
        }
    }
}

