package com.webarch.srmobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author Manoj Khanna
 */

public class AndroidUtils {

    public static Bitmap createCircleBitmap(Context context, byte[] picture, int sizeDp) {
        int size = (int) (sizeDp * context.getResources().getDisplayMetrics().density);
        Bitmap pictureBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(pictureBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float halfSize = size / 2.0f;
        canvas.drawCircle(halfSize, halfSize, halfSize, paint);
        Rect rect = new Rect(0, 0, size, size);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(BitmapFactory.decodeByteArray(picture, 0, picture.length), rect, rect, paint);
        return pictureBitmap;
    }

    public static void postLayout(final View view, final Runnable runnable) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //noinspection deprecation
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                view.post(runnable);
            }

        });
    }

}
