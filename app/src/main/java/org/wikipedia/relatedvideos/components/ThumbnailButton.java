package org.wikipedia.relatedvideos.components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Fred on 2018-03-26.
 */

public class ThumbnailButton extends AppCompatImageView {

    private static final int FILTER_COLOR = 0x77000000;

    private static final float PRESS_OFFSET = 5.0f;

    public ThumbnailButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AppCompatImageView view = (AppCompatImageView) v;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        //overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(FILTER_COLOR, PorterDuff.Mode.SRC_ATOP);

                        view.invalidate();

                        view.setTranslationX(-PRESS_OFFSET);
                        view.setTranslationY(PRESS_OFFSET);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        //clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        view.setTranslationX(0.0f);
                        view.setTranslationY(0.0f);
                        break;
                    }
                    default:
                        break;
                }

                return false;
            }
        });

        // Empty OnClickListener to fire ACTION_UP event
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
    }

    public boolean performClick() {
        return callOnClick();
    }
}
