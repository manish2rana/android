package com.ecs.lib;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Manish on 14-12-2017.
 */

public interface PBDelegate {

    void draw(Canvas canvas, Paint paint);

    void start();

    void stop();

    void progressiveStop(CircularProgressDrawable.OnEndListener listener);
}
