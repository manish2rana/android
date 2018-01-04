package pro.com.ecs.customimageviewpager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by Manish on 04-01-2018.
 */

public class CellAdapter extends RecyclerView.Adapter {

    public static final int PLACEHOLDER_START = 3000;
    public static final int PLACEHOLDER_END = 3001;
    public static final int PLACEHOLDER_START_SIZE = 1;
    public static final int PLACEHOLDER_END_SIZE = 1;
    public static final int CELL = 3002;

    final protected int row;
    final protected CustomImageViewpager.Adapter adapter;
    final protected CustomImageViewpagerSettings settings;
    protected HeightCalculatedCallback heightCalculatedCallback;

    public CellAdapter(int row, CustomImageViewpager.Adapter adapter, CustomImageViewpagerSettings settings, HeightCalculatedCallback heightCalculatedCallback) {
        this.row = row;
        this.adapter = adapter;
        this.settings = settings;
        this.heightCalculatedCallback = heightCalculatedCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return PLACEHOLDER_START;
        else if (position == getItemCount() - 1)
            return PLACEHOLDER_END;
        else
            return CELL;
    }

    public interface HeightCalculatedCallback {
        void onHeightCalculated(int height);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        final View view;
        switch (type) {
            case PLACEHOLDER_START:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.civp_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, true, settings.paddingLeft);
            case PLACEHOLDER_END:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.civp_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, true, settings.paddingRight);
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.civp_cell, viewGroup, false);

                //simulate wrap_content
                view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (heightCalculatedCallback != null)
                            heightCalculatedCallback.onHeightCalculated(view.getHeight());
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
        }

        return new CellViewHolder(view, this.row, adapter, settings);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CellViewHolder) {
            CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
            if (position == 1)
                cellViewHolder.setEnlarged(false);
            else
                cellViewHolder.setEnlarged(true);
            cellViewHolder.newPosition(position - PLACEHOLDER_START_SIZE);

            cellViewHolder.onBind();
        }
    }

    @Override
    public int getItemCount() {
        return this.adapter.getCellsCount(this.row) + PLACEHOLDER_START_SIZE + PLACEHOLDER_END_SIZE;
    }
}
