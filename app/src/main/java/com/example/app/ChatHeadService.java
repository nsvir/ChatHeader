package com.example.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by svirch_n on 22/03/14.
 * nicolas.svirchevsky@epitech.eu
 */

public class ChatHeadService extends Service implements View.OnTouchListener {

    private WindowManager windowManager;
    private ImageView chatHead;
    private WindowManager.LayoutParams params;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.bastion_icone_little);
        chatHead.setOnTouchListener(this);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = -50;
        params.y = 200;


        windowManager.addView(chatHead, params);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null)
            windowManager.removeView(chatHead);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void moveToBorder() {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (params.x + (chatHead.getWidth() / 2) <= size.x / 2) {
            // Left Border
            params.x = 0 - 50;
        } else {
            //right Border
            params.x = (size.x - chatHead.getWidth()) + 60;
        }
        if (size.y - (params.y + chatHead.getHeight() / 2) <= size.y / 5) {
            //exit
            stopSelf();
        }
        windowManager.updateViewLayout(chatHead, params);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = motionEvent.getRawX();
                initialTouchY = motionEvent.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                moveToBorder();
                return true;
            case MotionEvent.ACTION_MOVE:
                params.x = initialX + (int) (motionEvent.getRawX() - initialTouchX);
                params.y = initialY + (int) (motionEvent.getRawY() - initialTouchY);
                windowManager.updateViewLayout(chatHead, params);
                return true;
        }
        return false;
    }
}
