package top.itmp.jiandan2.view.floorview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;

import top.itmp.jiandan2.R;

/**
 * Created by hz on 16/6/3.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FloorView extends LinearLayout {

    private int density;
    private Drawable drawable;
    private SubComments datas;
    private SubFloorFactory factory;

    public FloorView(Context context){
        this(context, null);
    }

    public FloorView(Context context, AttributeSet attributeSet){
        //this(context, attributeSet, 0);
        super(context, attributeSet);
        init(context);
    }

    public FloorView(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
        init(context);
    }

    public void setBoundDrawer(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setComments(SubComments cmts) {
        datas = cmts;
    }

    public void setFactory(SubFloorFactory fac) {
        factory = fac;
    }

    public int getFloorNum() {
        return getChildCount();
    }

    public void init(Context context){
        this.setOrientation((LinearLayout.VERTICAL));
        density = (int) (3.0F * context.getResources().getDisplayMetrics().density);
    }

    public void init(){
        if(datas.iterator() == null){
            return;
        }
        if(datas.getFloorNum() < 7){
            for(Iterator<? extends Commentable> iterator = datas.iterator(); iterator.hasNext();){
                View view = factory.buildSubFloor(iterator.next(), this);
                addView(view);
            }
        }else{
            View view;
            view = factory.buildSubFloor(datas.get(0), this);
            addView(view);
            view = factory.buildSubFloor(datas.get(1), this);
            addView(view);

            view = factory.buildSubHideFloor(datas.get(2), this);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView hide_text = (TextView)v.findViewById(R.id.hide_text);
                    hide_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    v.findViewById(R.id.hide_pb).setVisibility(View.VISIBLE);
                    removeAllViews();

                    for(Iterator<? extends Commentable> iterator = datas.iterator(); iterator.hasNext();){
                        View view = factory.buildSubFloor(iterator.next(), FloorView.this);
                        addView(view);
                    }
                    reLayoutChildren();
                }
            });
            addView(view);
            view = factory.buildSubFloor(datas.get(datas.size() - 1), this);
            addView(view);
        }
        reLayoutChildren();
    }

    public void reLayoutChildren(){
        int count = getChildCount();
        for(int i = 0; i < count; i++){
            View view = getChildAt(i);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            int margin = Math.min((count - i - 1), 4)* density;
            if(count -1 == i){
                layoutParams.topMargin = 0;
            }else {
                layoutParams.topMargin = Math.min((count - i), 4) * density;
            }
            view.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int i = getChildCount();
        if(drawable != null && i > 0){
            for(int j = i - 1; j >= 0; j--){
                View view = getChildAt(j);
                drawable.setBounds(view.getLeft(), view.getLeft(),
                        view.getRight(), view.getBottom());
                drawable.draw(canvas);
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(this.getChildCount() <= 0){
            setMeasuredDimension(0, 0);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
