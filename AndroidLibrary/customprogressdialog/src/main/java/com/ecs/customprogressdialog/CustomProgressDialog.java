package com.ecs.customprogressdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Manish on 14-12-2017.
 */

public class CustomProgressDialog extends Dialog {

    private ImageView imgView;
    private TextView tvInfoMessage;
    private String infoMessage;
    private int imgResource;
    private Context context;

    /**
     *
     * @param context
     * @param imgResource 48X48 dimens
     * @param msg
     */
    public CustomProgressDialog(@NonNull Context context, int imgResource, String msg) {
        super(context);
        this.imgResource = imgResource;
        this.infoMessage = msg;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        imgView = findViewById(R.id.imgView);
        tvInfoMessage = findViewById(R.id.tvInfoMessage);
        imgView.setImageResource(imgResource);
        tvInfoMessage.setText(infoMessage);
        tvInfoMessage.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf"));
    }


}
