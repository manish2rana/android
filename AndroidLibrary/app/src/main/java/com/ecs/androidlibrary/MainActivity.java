package com.ecs.androidlibrary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecs.customprogressdialog.CustomProgressDialog;
import com.ecs.utils.CustomImageHolder;
import com.squareup.picasso.Picasso;

import pro.com.ecs.customimageviewpager.CustomImageViewpager;

public class MainActivity extends AppCompatActivity {

    CustomImageViewpager customImageViewpager;

    String[] sImageUrls = {
            "", "", "", "", ""
    };
    ImageView imgGalleryShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customImageViewpager = findViewById(R.id.customImageViewpager);
        imgGalleryShow = findViewById(R.id.imgGalleryShow);

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        customImageViewpager.setMinimumHeight(displayMetrics.heightPixels / 4);
        customImageViewpager.setCustomizer(new CustomImageViewpager.Customizer() {
            @Override
            public void customizeTitle(TextView textView) {

                textView.setTypeface(null, Typeface.BOLD);
            }
        });

        customImageViewpager.setAdapter(new CustomImageViewpager.Adapter<CustomImageHolder>() {
            @Override
            public int getLineCount() {
                return 1;
            }

            @Override
            public int getCellsCount(int line) {
                return sImageUrls.length;
            }

            @Override
            public CustomImageHolder onCreateViewHolder(ViewGroup viewGroup, int line) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_material_lean_back_cell, viewGroup, false);
                return new CustomImageHolder(view);
            }

            @Override
            public void onBindViewHolder(CustomImageHolder viewHolder, final int i) {
                /*Picasso.with(MainActivity.this).load(sImageUrls[i]).into(viewHolder.imgGalleryLeanBack);
                viewHolder.imgGalleryLeanBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picasso.with(MainActivity.this).load(sImageUrls[i]).into(imgGalleryShow);
                        //tvSlideCountGallery.setText((i + 1) + "/" + sImageUrls.length);
                    }
                });*/
                viewHolder.textView.setText("test " + i);

                String url = "http://www.lorempixel.com/40" + viewHolder.row + "/40" + viewHolder.cell + "/";
                Picasso.with(viewHolder.imageView.getContext()).load(url).into(viewHolder.imageView);
            }

            @Override
            public String getTitleForRow(int row) {
                //return "Line " + row;
                return  "Line " + row;
            }

        });
    }

    public void showProgressBar(View view) {
        new BackgroundSplashTask().execute();
    }

    public void showCustomViewPager(View view) {

    }


    private class BackgroundSplashTask extends AsyncTask<Void, Void, Void> {
        CustomProgressDialog customProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog = new CustomProgressDialog(MainActivity.this, R.drawable.pd_ecs_logo, "Please wait we are processing..!");
            customProgressDialog.setCancelable(false);
            customProgressDialog.setCanceledOnTouchOutside(false);
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            customProgressDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }
}
