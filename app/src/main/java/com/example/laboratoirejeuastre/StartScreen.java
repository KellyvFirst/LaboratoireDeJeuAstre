package com.example.laboratoirejeuastre;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class StartScreen extends View {

    private Bitmap accelerometerBitmap, touchBitmap, logoBitmap;
    private float accelerometerX, accelerometerY, touchX, touchY;
    private OnControlSelectedListener listener;

    public StartScreen(Context context, OnControlSelectedListener listener) {
        super(context);
        this.listener = listener;
        init();
    }

    private void init() {
        logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        accelerometerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.accelerometre);
        accelerometerBitmap = Bitmap.createScaledBitmap(accelerometerBitmap, 160, 160, true);
        touchBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tactile);
        touchBitmap = Bitmap.createScaledBitmap(touchBitmap, 160, 160, true);
        // Vous pouvez ajuster la taille et la position selon vos besoins
        accelerometerX = 100;
        accelerometerY = 500;
        touchX = 500;
        touchY = 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(logoBitmap, getWidth() / 2 - logoBitmap.getWidth() / 2, 100, null);
        canvas.drawBitmap(accelerometerBitmap, accelerometerX, accelerometerY, null);
        canvas.drawBitmap(touchBitmap, touchX, touchY, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x >= accelerometerX && x <= accelerometerX + accelerometerBitmap.getWidth()
                        && y >= accelerometerY && y <= accelerometerY + accelerometerBitmap.getHeight()) {
                    accelerometerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.accelerometre2);
                    invalidate();
                    if (listener != null) {
                        listener.onControlSelected("accelerometer");
                    }
                } else if (x >= touchX && x <= touchX + touchBitmap.getWidth()
                        && y >= touchY && y <= touchY + touchBitmap.getHeight()) {
                    touchBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tactile2);
                    invalidate();
                    if (listener != null) {
                        listener.onControlSelected("touch");
                    }
                }
                break;
        }

        return true;
    }

    public interface OnControlSelectedListener {
        void onControlSelected(String controlType);
    }
}

