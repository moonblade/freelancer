package com.dng.gruupy;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CusFntView extends EditText {

public CusFntView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
}

public CusFntView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

public CusFntView(Context context) {
    super(context);
    init();
}

private void init() {
    if (!isInEditMode()) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "DISCO___.ttf");
        setTypeface(tf);
    }
}
}