package pro.com.ecs.customimageviewpager;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Manish on 04-01-2018.
 */

public class LineViewHolder extends RecyclerView.ViewHolder {

    protected final CustomImageViewpagerSettings settings;
    protected final RecyclerView recyclerView;
    protected final CustomImageViewpager.Adapter adapter;
    protected final CustomImageViewpager.Customizer customizer;

    protected ViewGroup layout;
    protected TextView title;

    protected int row;
    protected boolean wrapped = false;

    public LineViewHolder(View itemView, @NonNull CustomImageViewpager.Adapter adapter, @NonNull CustomImageViewpagerSettings settings, final CustomImageViewpager.Customizer customizer) {
        super(itemView);
        this.adapter = adapter;
        this.settings = settings;
        this.customizer = customizer;

        layout = (ViewGroup) itemView.findViewById(R.id.row_layout);
        title = (TextView) itemView.findViewById(R.id.row_title);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.row_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void onBind(int row) {
        this.row = row;

        {
            String titleString = adapter.getTitleForRow(this.row);
            if (titleString == null || titleString.trim().isEmpty())
                title.setVisibility(View.GONE);
            else
                title.setText(titleString);

            if (settings.titleColor != null)
                title.setTextColor(settings.titleColor);
            if (settings.titleSize != -1)
                title.setTextSize(settings.titleSize);

            if (this.customizer != null)
                customizer.customizeTitle(title);
        }
        {
            if (settings.lineSpacing != null) {
                layout.setPadding(
                        layout.getPaddingLeft(),
                        layout.getPaddingTop(),
                        layout.getPaddingRight(),
                        settings.lineSpacing
                );
            }
        }

        recyclerView.setAdapter(new CellAdapter(row, adapter, settings, new CellAdapter.HeightCalculatedCallback() {
            @Override
            public void onHeightCalculated(int height) {
                if (!wrapped) {
                    recyclerView.getLayoutParams().height = height;
                    recyclerView.requestLayout();
                    wrapped = true;
                }
            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);
                        cellViewHolder.newPosition(i);
                    }
                }
            }
        });
    }
}
