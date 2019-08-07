package pereira.agnaldo.library

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import kotlin.math.roundToInt

class BubbleImageView : android.support.v7.widget.AppCompatImageView {

    private var mBitmapWidth: Int = 0
    private var mBitmapHeight: Int = 0
    private var mDirect = LEFT
    private var mAnchor = BOTTOM
    private var mIsArrowVisible = true
    private var mRoundPixels: Int = 0
    private var mArrowSize: Int = 0
    private var mBaseArrowSize: Int = 0

    private var mArrowPath = Path()
    private var mMaskBubblePath = Path()

    constructor(context: Context) : super(context) {
        mRoundPixels = convertDpToPx(context, 2)
        mArrowSize = convertDpToPx(context, 5)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getStyles(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        getStyles(attrs, defStyle)
    }

    init {
        createMaskBubblePath()
        addArrowToMaskBubblePath()
    }

    private fun getStyles(attrs: AttributeSet, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BubbleImageView, defStyle, R.style.DefaultBubbleImageView)
        mArrowSize = typedArray.getDimensionPixelSize(
                R.styleable.BubbleImageView_arrowSize, convertDpToPx(context, 5))
        mBaseArrowSize = typedArray.getDimensionPixelSize(
                R.styleable.BubbleImageView_baseArrowSize, mArrowSize / 2)
        mRoundPixels = typedArray.getDimensionPixelSize(
                R.styleable.BubbleImageView_round, convertDpToPx(context, 4))
        mIsArrowVisible = typedArray.getBoolean(R.styleable.BubbleImageView_arrowVisible, true)
        mDirect = typedArray.getInteger(R.styleable.BubbleImageView_arrowDirection, LEFT)
        mAnchor = typedArray.getInteger(R.styleable.BubbleImageView_arrowAnchor, BOTTOM)
        typedArray.recycle()
    }

    private fun convertDpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            mBitmapHeight = h
            mBitmapWidth = w
            createMaskBubblePath()
            addArrowToMaskBubblePath()
        }
    }

    private fun createMaskBubblePath() {
        if (mDirect == RIGHT) {
            mMaskBubblePath = Path()
            mMaskBubblePath.addRoundRect(RectF(0f, 0f, (width - mArrowSize).toFloat(), height.toFloat()),
                    mRoundPixels.toFloat(), mRoundPixels.toFloat(), Path.Direction.CW)
        }

        if (mDirect == LEFT) {
            mMaskBubblePath = Path()
            mMaskBubblePath.addRoundRect(RectF(mArrowSize.toFloat(), 0f, width.toFloat(), height.toFloat()),
                    mRoundPixels.toFloat(), mRoundPixels.toFloat(), Path.Direction.CW)
        }

        if (mDirect == TOP) {
            mMaskBubblePath = Path()
            mMaskBubblePath.addRoundRect(RectF(0f, mArrowSize.toFloat(), width.toFloat(), height.toFloat()),
                    mRoundPixels.toFloat(), mRoundPixels.toFloat(), Path.Direction.CW)
        }

        if (mDirect == BOTTOM) {
            mMaskBubblePath = Path()
            mMaskBubblePath.addRoundRect(RectF(0f, 0f, width.toFloat(), (height - mArrowSize).toFloat()),
                    mRoundPixels.toFloat(), mRoundPixels.toFloat(), Path.Direction.CW)
        }
    }

    private fun addArrowToMaskBubblePath() {
        mArrowPath = Path()

        if (mIsArrowVisible) {

            when (mDirect) {
                RIGHT -> when (mAnchor) {
                    TOP -> {
                        mArrowPath.moveTo((mBitmapWidth - mArrowSize - mRoundPixels).toFloat(), 0f)
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), 0f)
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize - mRoundPixels).toFloat(), mBaseArrowSize.toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize - mRoundPixels).toFloat(), 0f)
                    }

                    MIDDLE -> {
                        mArrowPath.moveTo((mBitmapWidth - mArrowSize).toFloat(),
                                (mBitmapHeight / 2 - mBaseArrowSize / 2).toFloat())
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), (mBitmapHeight / 2).toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize).toFloat(),
                                (mBitmapHeight / 2 + mBaseArrowSize / 2).toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize).toFloat(),
                                (mBitmapHeight / 2 - mBaseArrowSize / 2).toFloat())
                    }

                    BOTTOM -> {
                        mArrowPath.moveTo((mBitmapWidth - mArrowSize - mRoundPixels).toFloat(), mBitmapHeight.toFloat())
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), mBitmapHeight.toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize - mRoundPixels).toFloat(),
                                (mBitmapHeight - mBaseArrowSize).toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize - mRoundPixels).toFloat(), mBitmapHeight.toFloat())
                    }
                }

                LEFT -> when (mAnchor) {
                    TOP -> {
                        mArrowPath.moveTo((mArrowSize + mRoundPixels).toFloat(), 0f)
                        mArrowPath.lineTo(0f, 0f)
                        mArrowPath.lineTo((mArrowSize + mRoundPixels).toFloat(), mBaseArrowSize.toFloat())
                        mArrowPath.lineTo((mArrowSize + mRoundPixels).toFloat(), 0f)
                    }

                    MIDDLE -> {
                        mArrowPath.moveTo(mArrowSize.toFloat(), (mBitmapHeight / 2 - mBaseArrowSize / 2).toFloat())
                        mArrowPath.lineTo(0f, (mBitmapHeight / 2).toFloat())
                        mArrowPath.lineTo(mArrowSize.toFloat(), (mBitmapHeight / 2 + mBaseArrowSize / 2).toFloat())
                        mArrowPath.lineTo(mArrowSize.toFloat(), (mBitmapHeight / 2 - mBaseArrowSize / 2).toFloat())
                    }

                    BOTTOM -> {
                        mArrowPath.moveTo((mArrowSize + mRoundPixels).toFloat(), mBitmapHeight.toFloat())
                        mArrowPath.lineTo(0f, mBitmapHeight.toFloat())
                        mArrowPath.lineTo((mArrowSize + mRoundPixels).toFloat(), (mBitmapHeight - mBaseArrowSize).toFloat())
                        mArrowPath.lineTo((mArrowSize + mRoundPixels).toFloat(), mBitmapHeight.toFloat())
                    }
                }

                TOP -> when (mAnchor) {
                    RIGHT -> {
                        mArrowPath.moveTo(mBitmapWidth.toFloat(), 0f)
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), (mArrowSize + mRoundPixels).toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mBaseArrowSize).toFloat(),
                                (mBaseArrowSize + mRoundPixels).toFloat())
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), 0f)
                    }

                    MIDDLE -> {
                        mArrowPath.moveTo((mBitmapWidth / 2).toFloat(), 0f)
                        mArrowPath.lineTo((mBitmapWidth / 2 - mBaseArrowSize / 2).toFloat(), mArrowSize.toFloat())
                        mArrowPath.lineTo((mBitmapWidth / 2 + mBaseArrowSize / 2).toFloat(), mArrowSize.toFloat())
                        mArrowPath.lineTo((mBitmapWidth / 2).toFloat(), 0f)
                    }

                    LEFT -> {
                        mArrowPath.moveTo(0f, 0f)
                        mArrowPath.lineTo(0f, (mArrowSize + mRoundPixels).toFloat())
                        mArrowPath.lineTo(mBaseArrowSize.toFloat(), (mArrowSize + mRoundPixels).toFloat())
                        mArrowPath.lineTo(0f, 0f)
                    }
                }

                BOTTOM -> when (mAnchor) {
                    RIGHT -> {
                        mArrowPath.moveTo(mBitmapWidth.toFloat(), (mBitmapHeight - mArrowSize - mRoundPixels).toFloat())
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), mBitmapHeight.toFloat())
                        mArrowPath.lineTo((mBitmapWidth - mArrowSize).toFloat(),
                                (mBitmapHeight - mArrowSize - mRoundPixels).toFloat())
                        mArrowPath.lineTo(mBitmapWidth.toFloat(), (mBitmapHeight - mArrowSize - mRoundPixels).toFloat())
                    }

                    MIDDLE -> {
                        mArrowPath.moveTo((mBitmapWidth / 2).toFloat(), mBitmapHeight.toFloat())
                        mArrowPath.lineTo((mBitmapWidth / 2 - mBaseArrowSize / 2).toFloat(),
                                (mBitmapHeight - mArrowSize).toFloat())
                        mArrowPath.lineTo((mBitmapWidth / 2 + mBaseArrowSize / 2).toFloat(),
                                (mBitmapHeight - mArrowSize).toFloat())
                        mArrowPath.lineTo((mBitmapWidth / 2).toFloat(), mBitmapHeight.toFloat())
                    }

                    LEFT -> {
                        mArrowPath.moveTo(0f, (mBitmapHeight - mArrowSize - mRoundPixels).toFloat())
                        mArrowPath.lineTo(0f, mBitmapHeight.toFloat())
                        mArrowPath.lineTo(mArrowSize.toFloat(), (mBitmapHeight - mArrowSize - mRoundPixels).toFloat())
                        mArrowPath.lineTo(0f, (mBitmapHeight - mArrowSize - mRoundPixels).toFloat())
                    }
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onDraw(canvas: Canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mMaskBubblePath.op(mArrowPath, Path.Op.UNION)
            canvas.clipPath(mMaskBubblePath)
        } else {
            canvas.clipPath(mMaskBubblePath)
            canvas.clipPath(mArrowPath, Region.Op.UNION)
        }
        super.onDraw(canvas)
    }

    companion object {
        const val RIGHT = 0
        const val LEFT = 1
        const val TOP = 2
        const val BOTTOM = 3
        const val MIDDLE = 4
    }
}
