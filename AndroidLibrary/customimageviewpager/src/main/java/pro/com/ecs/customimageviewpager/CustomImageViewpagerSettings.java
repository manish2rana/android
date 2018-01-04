package pro.com.ecs.customimageviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/**
 * Created by Manish on 04-01-2018.
 */

public class CustomImageViewpagerSettings {

    public Integer titleColor;
    public int titleSize;
    public boolean animateCards;
    public boolean overlapCards;
    public int elevationReduced;
    public int elevationEnlarged;

    public Integer backgroundId;
    public Float backgroundOverlay;
    public Integer backgroundOverlayColor;

    public Integer paddingTop;
    public Integer paddingBottom;
    public Integer paddingLeft;
    public Integer paddingRight;

    public Integer lineSpacing;

    protected void handleAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaterialCustomVp);

            {
                if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_titleColor))
                    titleColor = styledAttrs.getColor(R.styleable.MaterialCustomVp_civp_titleColor, -1);
                titleSize = styledAttrs.getDimensionPixelSize(R.styleable.MaterialCustomVp_civp_titleSize, -1);
                animateCards = styledAttrs.getBoolean(R.styleable.MaterialCustomVp_civp_animateCards, true);
                overlapCards = styledAttrs.getBoolean(R.styleable.MaterialCustomVp_civp_overlapCards, true);
                elevationEnlarged = styledAttrs.getInteger(R.styleable.MaterialCustomVp_civp_cardElevationEnlarged, 8);
                elevationReduced = styledAttrs.getInteger(R.styleable.MaterialCustomVp_civp_cardElevationReduced, 5);

                {
                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_paddingTop))
                        paddingTop = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialCustomVp_civp_paddingTop, -1);
                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_paddingBottom))
                        paddingBottom = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialCustomVp_civp_paddingBottom, -1);
                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_paddingLeft))
                        paddingLeft = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialCustomVp_civp_paddingLeft, -1);
                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_paddingRight))
                        paddingRight = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialCustomVp_civp_paddingRight, -1);
                }

                {
                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_background))
                        backgroundId = styledAttrs.getResourceId(R.styleable.MaterialCustomVp_civp_background, -1);

                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_backgroundOverlay))
                        backgroundOverlay = styledAttrs.getFloat(R.styleable.MaterialCustomVp_civp_backgroundOverlay, -1);

                    if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_backgroundOverlayColor))
                        backgroundOverlayColor = styledAttrs.getColor(R.styleable.MaterialCustomVp_civp_backgroundOverlayColor, -1);
                }

                if (styledAttrs.hasValue(R.styleable.MaterialCustomVp_civp_lineSpacing))
                    lineSpacing = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialCustomVp_civp_lineSpacing, -1);
            }

            styledAttrs.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
