package com.withparadox2.divider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsd
 */

public class DividerHandler {
    public final static int TYPE_LEFT = 1;
    public final static int TYPE_TOP = 2;
    public final static int TYPE_RIGHT = 4;
    public final static int TYPE_BOTTOM = 8;

    static Paint dividerPaint = new Paint();
    static Rect rect = new Rect();
    static int[] point = new int[2];
    public static IDrawStrategy defaultDrawStrategy = new IDrawStrategy() {
        @Override
        public boolean isDraw(Rect topRect, DividerHandler handler) {
            return handler.view.isShown();
        }
    };

    static {
        dividerPaint.setStyle(Paint.Style.STROKE);
    }

    View view;
    int padding;
    int marginStart;// from top to down, from left to right
    int marginEnd;
    int posType;

    int color;
    float dividerWidth;

    IDrawStrategy drawStrategy = defaultDrawStrategy;

    public DividerHandler(View view, int type, IDrawStrategy drawStrategy) {
        this(view, type, view.getResources().getColor(R.color.g_divider), 0, 0, 0, 1,
                drawStrategy);
    }

    public DividerHandler(View view, int type, int color, int marginStart, int marginEnd, int padding,
                          int dividerWidth, IDrawStrategy drawStrategy) {
        this.view = view;
        this.posType = type;
        this.color = color;
        this.padding = padding;
        this.marginEnd = marginEnd;
        this.marginStart = marginStart;
        this.dividerWidth = dividerWidth;
        this.drawStrategy = drawStrategy;
    }

    public DividerHandler(View view, int type) {
        this(view, type, defaultDrawStrategy);
    }

    public DividerHandler setColor(int color) {
        this.color = color;
        return this;
    }

    public DividerHandler setDividerWidth(float width) {
        this.dividerWidth = width;
        return this;
    }

    public DividerHandler setMargin(int start, int end) {
        this.marginStart = start;
        this.marginEnd = end;
        return this;
    }

    public View getView() {
        return view;
    }

    public void drawDivider(Rect topRect, Canvas canvas) {
        view.getLocationOnScreen(point);
        rect.set(0, 0, view.getWidth(), view.getHeight());
        rect.offset(point[0], point[1]);
        if (drawStrategy != null && !drawStrategy.isDraw(topRect, this)) {
            return;
        }
        dividerPaint.setColor(color);
        dividerPaint.setStrokeWidth(dividerWidth);

        int diffY = rect.top - topRect.top;
        int diffX = rect.left - topRect.left;

        switch (posType) {
            case TYPE_LEFT: {
                int top = marginStart + diffY;
                float x = diffX - padding + dividerWidth / 2;
                int bottom = diffY + rect.height() - marginEnd;
                canvas.drawLine(x, top, x, bottom, dividerPaint);
                break;
            }
            case TYPE_RIGHT: {
                int top = marginStart + diffY;
                float x = diffX + rect.width() + padding - dividerWidth / 2;
                int bottom = diffY + rect.height() - marginEnd;
                canvas.drawLine(x, top, x, bottom, dividerPaint);
                break;
            }
            case TYPE_TOP: {
                int left = marginStart + diffX;
                float y = diffY - padding + dividerWidth / 2;
                int right = diffX + rect.width() - marginEnd;
                canvas.drawLine(left, y, right, y, dividerPaint);
                break;
            }
            case TYPE_BOTTOM: {
                int left = marginStart + diffX;
                float y = diffY + rect.height() + padding - dividerWidth / 2;
                int right = diffX + rect.width() - marginEnd;
                canvas.drawLine(left, y, right, y, dividerPaint);
                break;
            }
        }
    }

    public static interface IDrawStrategy {
        public boolean isDraw(Rect topRect, DividerHandler handler);
    }

    /**
     * To use this method, you need to subclass a ViewGroup and call it in {@link View#draw(Canvas)}
     * <p>
     * If topView is a LinearLayout inside a ScrollView, assume the visible area (axis y) of topView
     * is y_v,
     * and if we scroll the content from down to up with a distance of deltaY, then the canvas's
     * origin in topView will also move up to y_o (where y_o=y_v-deltaY). Our target's view (the view
     * we want to draw divider inside topView) has a distance y_t from origin of topView's canvas. So
     * first we need to move canvas down with a distance of deltaY to new origin y_v, and then draw
     * divider from the new origin with an offset y_t-y_v
     * <p>
     * Use topView.getLocationOnScreen(point) to get y_o
     * <p>
     * Use topView.getGlobalVisibleRect(topRect) to get y_v
     * <p>
     * Use targetView.getLocationOnScreen(point) to get y_t, see {@link #drawDivider(Rect, Canvas)}
     * <p>
     * Actually, y_t and y_o will be enough to calculate the position, without moving canvas, just
     * draw with y_t-y_o, however y_v can help to make decision about whether draw divider or not.
     * <p>
     * <em>Note:</em> If your layout is a scrollView containing a childView, you should make
     * childView as the topView to achieve a better performance, because if not so, method {@link
     * View#draw(Canvas)} will be called every frame when you scroll your content. If you
     * have to set scrollView as the topView, you can ask IDrawStrategy for some help.
     *
     * @see #drawDivider(Rect, Canvas)
     */
    public static void draw(Rect topRect, View topView, Canvas canvas,
                            List<DividerHandler> dividerList) {
        if (topRect.isEmpty()) {
            topView.getGlobalVisibleRect(topRect);
        }
        topView.getLocationOnScreen(point);

        canvas.save();
        canvas.translate(topRect.left - point[0], topRect.top - point[1]);
        for (DividerHandler divider : dividerList) {
            divider.drawDivider(topRect, canvas);
        }
        canvas.restore();
    }

    public static class DividerModel {
        public List<DividerHandler> dividerList = new ArrayList<DividerHandler>();
        private Rect topRect = new Rect();

        //called in onSizeChanged()
        public void clearRect() {
            topRect.setEmpty();
        }

        //called in draw(Canvas canvas)
        public void draw(Canvas canvas, View view) {
            DividerHandler.draw(topRect, view, canvas, dividerList);
        }
    }
}
