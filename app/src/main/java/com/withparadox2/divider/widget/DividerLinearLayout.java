package com.withparadox2.divider.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.withparadox2.divider.DividerHandler;
import com.withparadox2.divider.R;
import java.util.List;

/**
 * Each child can contain more than one dividers sharing the same
 * attributes, like padding, margin, color...
 */
public class DividerLinearLayout extends AbstractDividerLinearLayout {

    private DividerLayoutParams lpFake;//当前View的lp，仅用来处理分隔线，和真正的LayoutParams无任何关系

    public DividerLinearLayout(Context context) {
        this(context, null);
    }

    public DividerLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        lpFake = new DividerLayoutParams();
        extractAttributes(lpFake, context, attrs, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            DividerLayoutParams lp = (DividerLayoutParams) view.getLayoutParams();
            addDividerByLp(view, lp);
        }

        addDividerByLp(this, lpFake);
    }

    private void addDividerByLp(View view, DividerLayoutParams lp) {
        if (lp.posType <= 0) {
            return;
        }

        List<DividerHandler> list = model.dividerList;

        if ((lp.posType & DividerHandler.TYPE_BOTTOM) != 0) {
            list.add(buildDivider(view, DividerHandler.TYPE_BOTTOM, lp));
        }
        if ((lp.posType & DividerHandler.TYPE_TOP) != 0) {
            list.add(buildDivider(view, DividerHandler.TYPE_TOP, lp));
        }
        if ((lp.posType & DividerHandler.TYPE_LEFT) != 0) {
            list.add(buildDivider(view, DividerHandler.TYPE_LEFT, lp));
        }
        if ((lp.posType & DividerHandler.TYPE_RIGHT) != 0) {
            list.add(buildDivider(view, DividerHandler.TYPE_RIGHT, lp));
        }
    }

    protected DividerHandler buildDivider(View view, int type, DividerLayoutParams lp) {
        return new DividerHandler(view, type, lp.color, lp.marginStart, lp.marginEnd, lp.padding,
                lp.dividerWidth, DividerHandler.defaultDrawStrategy);
    }

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DividerLayoutParams(getContext(), attrs);
    }

    public static class DividerLayoutParams extends LinearLayout.LayoutParams {
        int padding;
        int marginStart;
        int marginEnd;
        int posType;
        int color;
        int dividerWidth;

        public DividerLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            extractAttributes(this, c, attrs, false);
        }

        public DividerLayoutParams() {
            super(0, 0);
        }
    }

    private static void extractAttributes(DividerLayoutParams lp, Context c, AttributeSet attrs, boolean checkOuterMost) {
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DividerLinearLayout);
        lp.posType = a.getInt(R.styleable.DividerLinearLayout_dv_type, 0);

        lp.padding = a.getDimensionPixelSize(R.styleable.DividerLinearLayout_dv_padding, 0);
        lp.marginStart = a.getDimensionPixelSize(R.styleable.DividerLinearLayout_dv_margin_start, 0);
        lp.marginEnd = a.getDimensionPixelSize(R.styleable.DividerLinearLayout_dv_margin_end, 0);
        lp.dividerWidth = a.getDimensionPixelSize(R.styleable.DividerLinearLayout_dv_width, 1);

        lp.color = a.getColor(R.styleable.DividerLinearLayout_dv_color,
                c.getResources().getColor(R.color.g_divider));

        boolean isOuterMost = a.getBoolean(R.styleable.DividerLinearLayout_dv_is_outer_most, false);
        if (checkOuterMost && !isOuterMost) {
            lp.posType = 0;
        }
        a.recycle();
    }

}
