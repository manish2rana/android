package pro.com.ecs.customimageviewpager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Manish on 04-01-2018.
 */

public class LineAdapter extends RecyclerView.Adapter {

    public static final int PLACEHOLDER_START = -2000;
    public static final int PLACEHOLDER_START_SIZE = 1;
    public static final int PLACEHOLDER_END_SIZE = 1;
    public static final int PLACEHOLDER_END = -2001;
    public static final int CELL = -2002;

    protected CustomImageViewpagerSettings settings;
    protected CustomImageViewpager.Adapter adapter;
    protected CustomImageViewpager.Customizer customizer;

    public LineAdapter(@NonNull CustomImageViewpagerSettings settings, @NonNull CustomImageViewpager.Adapter adapter,
                       CustomImageViewpager.Customizer customizer) {
        this.settings = settings;
        this.adapter = adapter;
        this.customizer = customizer;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return PLACEHOLDER_START;
        else if (position == getItemCount() - 1)
            return PLACEHOLDER_END;
        else if (adapter != null && adapter.isCustomView(position - 1))
            return position - 1;
        else
            return CELL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view;
        switch (type) {
            case PLACEHOLDER_START:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.civp_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, false, settings.paddingTop);
            case PLACEHOLDER_END:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.civp_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, false, settings.paddingBottom);
            case CELL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.civp_row, viewGroup, false);
                return new LineViewHolder(view, adapter, settings, customizer);
            default:
                if (adapter != null && adapter.isCustomView(type)) {
                    return adapter.getCustomViewForRow(viewGroup, type);
                }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int row) {
        if (viewHolder instanceof LineViewHolder)
            ((LineViewHolder) viewHolder).onBind(row - PLACEHOLDER_START_SIZE);
        else if (adapter != null && adapter.isCustomView(row - PLACEHOLDER_START_SIZE))
            adapter.onBindCustomView(viewHolder, row - PLACEHOLDER_START_SIZE);

    }

    @Override
    public int getItemCount() {
        return adapter.getLineCount() + PLACEHOLDER_START_SIZE + PLACEHOLDER_END_SIZE;
    }
}
