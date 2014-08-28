package com.charbgr.parallaxlistview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by charbgr on 7/24/14.
 */
public class ClippingImageView extends ImageView {

    private final Rect mClipRect = new Rect();

    public ClippingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initClip();
    }

    public ClippingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClip();
    }

    public ClippingImageView(Context context) {
        super(context);
        initClip();
    }

    private void initClip() {
        post(new Runnable() {
            @Override
            public void run() {
                setImageCrop(0.2f);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (clip()) canvas.clipRect(mClipRect);
        super.onDraw(canvas);
    }

    private boolean clip() {
        // true if clip bounds have been set aren't equal to the view's bounds
        return !mClipRect.isEmpty() && !clipEqualsBounds();
    }

    private boolean clipEqualsBounds() {
        final int width = getWidth();
        final int height = getHeight();

        return mClipRect.width() == width && mClipRect.height() == height;
    }

    public void toggle() {
        final float[] values = clipEqualsBounds() ? new float[]{0f, 0.5f} : new float[]{0.5f, 0f};
        ObjectAnimator.ofFloat(this, "translationY", values).start();
    }


    public void setImageCrop(float value) {
        // nothing to do if there's no drawable set
        final Drawable drawable = getDrawable();
        if (drawable == null) return;

        // nothing to do if no dimensions are known yet
        final int width = getWidth();
        final int height = getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        // construct the clip bounds based on the supplied 'value' (which is assumed to be within the range [0...1])
        final int clipHeight = (int) (value * height);
        currentTop = clipHeight / 2;
        currentBottom = height - currentTop;

        // set clipping bounds
        mClipRect.set(0, currentTop, width, currentBottom);
        // schedule a draw pass for the new clipping bounds to take effect visually
        invalidate();

    }

    private int currentTop;
    private int currentBottom;

    //direction
    public static final int UP = -1;
    public static final int DOWN = 1;

    public void move(int direction, int aFactor) {

        if (direction == UP && currentTop <= 0) {
            return;
        }

        if (direction == DOWN && currentBottom >= getHeight()) {
            return;
        }

        int factor = aFactor * direction;

        currentTop = currentTop + factor ;
        currentBottom = currentBottom + factor ;

        mClipRect.set(0, currentTop, getWidth(), currentBottom);
        invalidate();
    }


}
