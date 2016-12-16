package com.withparadox2.divider.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.withparadox2.divider.DividerHandler;

public class AbstractDividerLinearLayout extends LinearLayout {
  public AbstractDividerLinearLayout(Context context) {
    super(context);
    setWillNotDraw(false);
  }

  public AbstractDividerLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
  }

  public AbstractDividerLinearLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setWillNotDraw(false);
  }

  protected DividerHandler.DividerModel model = new DividerHandler.DividerModel();

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    model.clearRect();
  }

  @Override public void draw(Canvas canvas) {
    super.draw(canvas);
    model.draw(canvas, this);
  }
}
