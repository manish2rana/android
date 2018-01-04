package pro.com.ecs.customimageviewpager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Manish on 04-01-2018.
 */

public class PlaceHolderViewHolder extends RecyclerView.ViewHolder {

    public PlaceHolderViewHolder(View itemView, boolean horizontal, int dimen) {
        super(itemView);

        if(horizontal) {
            if (dimen != -1) {
                itemView.getLayoutParams().width = dimen;
                itemView.requestLayout();
            }
        }else{
            if (dimen != -1) {
                itemView.getLayoutParams().height = dimen;
                itemView.requestLayout();
            }
        }
    }
}
