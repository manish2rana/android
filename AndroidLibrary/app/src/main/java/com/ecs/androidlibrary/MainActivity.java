package com.ecs.androidlibrary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ecs.customprogressdialog.CustomProgressDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void showProgressBar(View view) {
        new BackgroundSplashTask().execute();
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
