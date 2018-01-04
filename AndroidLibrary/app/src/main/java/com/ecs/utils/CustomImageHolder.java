package com.ecs.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecs.androidlibrary.R;

import pro.com.ecs.customimageviewpager.CustomImageViewpager;

/**
 * Created by Manish on 04-01-2018.
 */

public class CustomImageHolder extends CustomImageViewpager.ViewHolder {

    public TextView textView;
    public ImageView imageView;

    public CustomImageHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
