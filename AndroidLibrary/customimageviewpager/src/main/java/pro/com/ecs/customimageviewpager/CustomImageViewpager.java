package pro.com.ecs.customimageviewpager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Manish on 04-01-2018.
 */

public class CustomImageViewpager extends FrameLayout {

    RecyclerView recyclerView;
    ImageView imageBackground;
    View imageBackgroundOverlay;

    CustomImageViewpager.Adapter adapter;
    CustomImageViewpager.Customizer customizer;
    CustomImageViewpagerSettings settings = new CustomImageViewpagerSettings();

    public CustomImageViewpager(Context context) {
        super(context);
    }

    public CustomImageViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        settings.handleAttributes(context, attrs);
    }

    public CustomImageViewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settings.handleAttributes(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addView(LayoutInflater.from(getContext()).inflate(R.layout.civp_layout, this, false));

        imageBackground = (ImageView) findViewById(R.id.civp_imageBackground);
        imageBackgroundOverlay = findViewById(R.id.civp_imageBackgroundOverlay);

        if (settings.backgroundId != null)
            imageBackground.setBackgroundDrawable(getContext().getResources().getDrawable(settings.backgroundId));

        if (settings.backgroundOverlay != null)
            //ViewHelper.setAlpha(imageBackgroundOverlay,settings.backgroundOverlay);
            if (settings.backgroundOverlayColor != null)
                imageBackgroundOverlay.setBackgroundColor(settings.backgroundOverlayColor);

        recyclerView = (RecyclerView) findViewById(R.id.civp_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public static abstract class Adapter<VH extends CustomImageViewpager.ViewHolder> {
        public int getLineCount() {
            return 0;
        }

        public int getCellsCount(int row) {
            return 0;
        }

        public VH onCreateViewHolder(ViewGroup viewGroup, int row) {
            return null;
        }

        public void onBindViewHolder(VH viewHolder, int i) {
        }

        public String getTitleForRow(int row) {
            return null;
        }

        public boolean isCustomView(int row) {
            return false;
        }

        //if you want to set a custom view into the MaterialLeanBack, eg: a header
        public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewGroup, int row) {
            return null;
        }

        public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(new LineAdapter(settings, adapter, customizer));
    }

    public static class ViewHolder {

        public int row;
        public int cell;
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

    }

    public interface Customizer {
        void customizeTitle(TextView textView);
    }

    public Customizer getCustomizer() {
        return customizer;
    }

    public void setCustomizer(Customizer customizer) {
        this.customizer = customizer;
    }

    public ImageView getImageBackground() {
        return imageBackground;
    }

}
