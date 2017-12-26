package com.ecs.androidlibrary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void makeCircle(View view) {
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        int radius = Math.min(canvas.getWidth(), canvas.getHeight() / 2);
        int padding = 5;

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius - padding, paint);
        ImageView imageView = (ImageView) findViewById(R.id.imgView);
        imageView.setImageBitmap(bitmap);
    }

    public void makeRectangle(View view) {
        Bitmap bitmap = Bitmap.createBitmap(200, 150, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        // int radius = Math.min(canvas.getWidth(), canvas.getHeight() / 2);
        int padding = 5;

        RectF rectangle = new RectF(padding, padding, canvas.getWidth() - padding, canvas.getHeight() - padding);

        canvas.drawRoundRect(rectangle, 10, 10, paint);
        //canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius - padding, paint);
        ImageView imageView = (ImageView) findViewById(R.id.imgView);
        imageView.setImageBitmap(bitmap);
    }

}
