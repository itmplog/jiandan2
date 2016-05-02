package top.itmp.jiandan2.views;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hz on 2016/4/26.
 */
public class CustomSlidingPaneLayout extends SlidingPaneLayout {

    private boolean slidingEnabled = false;

    public CustomSlidingPaneLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public CustomSlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomSlidingPaneLayout(Context context) {
        this(context, null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //return super.onInterceptTouchEvent(ev);
        if (!isOpen()) return false;
        return !slidingEnabled || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return super.onTouchEvent(ev);
        if (!slidingEnabled) {
            // Careful here, view might be null
            getChildAt(1).dispatchTouchEvent(ev);
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public void setSwipe(boolean enableSliding) {
        slidingEnabled = enableSliding;
    }
}
