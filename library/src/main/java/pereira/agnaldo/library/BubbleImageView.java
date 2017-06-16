package pereira.agnaldo.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class BubbleImageView extends android.support.v7.widget.AppCompatImageView {

    private Context context;
    private Path maskBubblePath;

    private int bitmapWidth;
    private int bitmapHeight;
    private int direct = LEFT;
    private int anchor = BOTTOM;
    private boolean arrowVisible = true;
    private int roundPixels;
    private int arrowSize;
    private int baseArrowSize;

    public final static int RIGHT = 0;
    public final static int LEFT = 1;
    public final static int TOP = 2;
    public final static int BOTTOM = 3;
    public final static int MIDDLE = 4;

    public BubbleImageView(final Context context) {
        super(context);
        this.context = context;
        roundPixels = convertDpToPx(context, 2);
        arrowSize = convertDpToPx(context, 5);
        init();
    }

    public BubbleImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getStyles(attrs, 0);
        init();
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        getStyles(attrs, defStyle);
        init();
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(final int direct) {
        this.direct = direct;
    }

    public int getAnchor() {
        return anchor;
    }

    public void setAnchor(final int anchor) {
        this.anchor = anchor;
    }

    public boolean isArrowVisible() {
        return arrowVisible;
    }

    public void setArrowVisible(final boolean arrowVisible) {
        this.arrowVisible = arrowVisible;
    }

    public int getRoundPixels() {
        return roundPixels;
    }

    public void setRoundPixels(final int roundPixels) {
        this.roundPixels = roundPixels;
    }

    public int getArrowSize() {
        return arrowSize;
    }

    public void setArrowSize(final int arrowSize) {
        this.arrowSize = arrowSize;
    }

    public int getBaseArrowSize() {
        return baseArrowSize;
    }

    public void setBaseArrowSize(final int baseArrowSize) {
        this.baseArrowSize = baseArrowSize;
    }

    private void getStyles(final AttributeSet attrs, final int defStyle) {
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.BubbleImageView, defStyle,
                        R.style.DefaultBubbleImageView);
        arrowSize = a.getDimensionPixelSize(R.styleable.BubbleImageView_arrowSize,
                convertDpToPx(context, 5));
        baseArrowSize =
                a.getDimensionPixelSize(R.styleable.BubbleImageView_baseArrowSize, arrowSize / 2);
        roundPixels = a.getDimensionPixelSize(R.styleable.BubbleImageView_round,
                convertDpToPx(context, 4));
        arrowVisible = a.getBoolean(R.styleable.BubbleImageView_arrowVisible, true);
        direct = a.getInteger(R.styleable.BubbleImageView_arrowDirection, LEFT);
        anchor = a.getInteger(R.styleable.BubbleImageView_arrowAnchor, BOTTOM);
        a.recycle();
    }

    private int convertDpToPx(final Context context, final int dp) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private void init() {
        createMaskBubblePath();
        addArrowToMaskBubblePath();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            bitmapHeight = h;
            bitmapWidth = w;
            createMaskBubblePath();
            addArrowToMaskBubblePath();
        }
    }

    public void createMaskBubblePath() {
        if (direct == RIGHT) {
            maskBubblePath = new Path();
            maskBubblePath.addRoundRect(new RectF(0, 0, getWidth() - arrowSize, getHeight()),
                    roundPixels, roundPixels, Path.Direction.CW);
        }

        if (direct == LEFT) {
            maskBubblePath = new Path();
            maskBubblePath.addRoundRect(new RectF(arrowSize, 0, getWidth(), getHeight()),
                    roundPixels, roundPixels, Path.Direction.CW);
        }

        if (direct == TOP) {
            maskBubblePath = new Path();
            maskBubblePath.addRoundRect(new RectF(0, arrowSize, getWidth(), getHeight()),
                    roundPixels, roundPixels, Path.Direction.CW);
        }

        if (direct == BOTTOM) {
            maskBubblePath = new Path();
            maskBubblePath.addRoundRect(new RectF(0, 0, getWidth(), getHeight() - arrowSize),
                    roundPixels, roundPixels, Path.Direction.CW);
        }
    }

    private void addArrowToMaskBubblePath() {
        arrowPath = new Path();

        if (arrowVisible) {

            switch (direct) {
                case RIGHT:
                    switch (anchor) {
                        case TOP:
                            arrowPath.moveTo(bitmapWidth - arrowSize - roundPixels, 0);
                            arrowPath.lineTo(bitmapWidth, 0);
                            arrowPath.lineTo(bitmapWidth - arrowSize - roundPixels, baseArrowSize);
                            arrowPath.lineTo(bitmapWidth - arrowSize - roundPixels, 0);
                            break;

                        case MIDDLE:
                            arrowPath.moveTo(bitmapWidth - arrowSize,
                                    bitmapHeight / 2 - baseArrowSize / 2);
                            arrowPath.lineTo(bitmapWidth, bitmapHeight / 2);
                            arrowPath.lineTo(bitmapWidth - arrowSize,
                                    bitmapHeight / 2 + baseArrowSize / 2);
                            arrowPath.lineTo(bitmapWidth - arrowSize,
                                    bitmapHeight / 2 - baseArrowSize / 2);
                            break;

                        case BOTTOM:
                            arrowPath.moveTo(bitmapWidth - arrowSize - roundPixels, bitmapHeight);
                            arrowPath.lineTo(bitmapWidth, bitmapHeight);
                            arrowPath.lineTo(bitmapWidth - arrowSize - roundPixels,
                                    bitmapHeight - baseArrowSize);
                            arrowPath.lineTo(bitmapWidth - arrowSize - roundPixels, bitmapHeight);
                            break;
                    }
                    break;

                case LEFT:
                    switch (anchor) {
                        case TOP:
                            arrowPath.moveTo(arrowSize + roundPixels, 0);
                            arrowPath.lineTo(0, 0);
                            arrowPath.lineTo(arrowSize + roundPixels, baseArrowSize);
                            arrowPath.lineTo(arrowSize + roundPixels, 0);
                            break;

                        case MIDDLE:
                            arrowPath.moveTo(arrowSize, bitmapHeight / 2 - baseArrowSize / 2);
                            arrowPath.lineTo(0, bitmapHeight / 2);
                            arrowPath.lineTo(arrowSize, bitmapHeight / 2 + baseArrowSize / 2);
                            arrowPath.lineTo(arrowSize, bitmapHeight / 2 - baseArrowSize / 2);
                            break;

                        case BOTTOM:
                            arrowPath.moveTo(arrowSize + roundPixels, bitmapHeight);
                            arrowPath.lineTo(0, bitmapHeight);
                            arrowPath.lineTo(arrowSize + roundPixels, bitmapHeight - baseArrowSize);
                            arrowPath.lineTo(arrowSize + roundPixels, bitmapHeight);
                            break;
                    }

                    break;

                case TOP:
                    switch (anchor) {
                        case RIGHT:
                            arrowPath.moveTo(bitmapWidth, 0);
                            arrowPath.lineTo(bitmapWidth, arrowSize + roundPixels);
                            arrowPath.lineTo(bitmapWidth - baseArrowSize,
                                    baseArrowSize + roundPixels);
                            arrowPath.lineTo(bitmapWidth, 0);
                            break;

                        case MIDDLE:
                            arrowPath.moveTo(bitmapWidth / 2, 0);
                            arrowPath.lineTo(bitmapWidth / 2 - baseArrowSize / 2, arrowSize);
                            arrowPath.lineTo(bitmapWidth / 2 + baseArrowSize / 2, arrowSize);
                            arrowPath.lineTo(bitmapWidth / 2, 0);
                            break;

                        case LEFT:
                            arrowPath.moveTo(0, 0);
                            arrowPath.lineTo(0, arrowSize + roundPixels);
                            arrowPath.lineTo(baseArrowSize, arrowSize + roundPixels);
                            arrowPath.lineTo(0, 0);
                            break;
                    }

                    break;

                case BOTTOM:
                    switch (anchor) {
                        case RIGHT:
                            arrowPath.moveTo(bitmapWidth, bitmapHeight - arrowSize - roundPixels);
                            arrowPath.lineTo(bitmapWidth, bitmapHeight);
                            arrowPath.lineTo(bitmapWidth - arrowSize,
                                    bitmapHeight - arrowSize - roundPixels);
                            arrowPath.lineTo(bitmapWidth, bitmapHeight - arrowSize - roundPixels);
                            break;

                        case MIDDLE:
                            arrowPath.moveTo(bitmapWidth / 2, bitmapHeight);
                            arrowPath.lineTo(bitmapWidth / 2 - baseArrowSize / 2,
                                    bitmapHeight - arrowSize);
                            arrowPath.lineTo(bitmapWidth / 2 + baseArrowSize / 2,
                                    bitmapHeight - arrowSize);
                            arrowPath.lineTo(bitmapWidth / 2, bitmapHeight);
                            break;

                        case LEFT:
                            arrowPath.moveTo(0, bitmapHeight - arrowSize - roundPixels);
                            arrowPath.lineTo(0, bitmapHeight);
                            arrowPath.lineTo(arrowSize, bitmapHeight - arrowSize - roundPixels);
                            arrowPath.lineTo(0, bitmapHeight - arrowSize - roundPixels);
                            break;
                    }
                    break;

            }


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(maskBubblePath);
        canvas.clipPath(arrowPath, Region.Op.UNION);
        super.onDraw(canvas);
    }

    private Path arrowPath;
}
