package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import awesome.vrund.vpawesomewidgets.VPIconTextView.DrawableClickListener.DrawablePosition


class VPIconTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val mContext = context

    companion object {
        const val TOP = 1
        const val BOTTOM = 2
        const val LEFT = 3
        const val RIGHT = 4
    }

    var icon = 0
        set(value) {
            field = value
            setupIcon()
        }

    var iconSize = 24
        set(value) {
            field = value
            setupIcon()
        }

    var showIcon = true
        set(value) {
            field = value
            setupIcon()
        }
    var iconPosition = LEFT
        set(value) {
            field = value
            setupIcon()
        }

    private var clickListener: DrawableClickListener? = null

    var actionX = 0
    var actionY = 0

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPIconTextView)

        icon = parent.getResourceId(R.styleable.VPIconTextView_icon, icon)
        showIcon = parent.getBoolean(R.styleable.VPIconTextView_showIcon, showIcon)
        iconSize = parent.getDimensionPixelSize(R.styleable.VPIconTextView_icon_size, iconSize)

        if (parent.hasValue(R.styleable.VPIconTextView_icon_position)) {
            iconPosition = parent.getInt(R.styleable.VPIconTextView_icon_position, LEFT)
        }

        parent.recycle()
    }

    private fun setupIcon() {
        when (iconPosition) {
            TOP -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resizeIcon(), null, null)
            BOTTOM -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizeIcon())
            LEFT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(resizeIcon(), null, null, null)
            RIGHT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resizeIcon(), null)
        }
    }

    private fun resizeIcon(): Drawable? {
        if (showIcon) {
            try {
                var item = mContext.getDrawableFromRes(icon)
                val bitmap = (item as BitmapDrawable).bitmap
                item = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, true))
                return LayerDrawable(arrayOf(item))
            } catch (e: Exception) {
            }
        }
        return null
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var bounds: Rect?
        if (event?.action == MotionEvent.ACTION_DOWN) {
            actionX = event.x.toInt()
            actionY = event.y.toInt()

            val kek = resizeIcon()
            if (kek != null && clickListener != null) {
                when (iconPosition) {
                    TOP -> if (kek.bounds.contains(actionX, actionY)) {
                        clickListener?.onClick(DrawablePosition.TOP)
                        return super.onTouchEvent(event)
                    }
                    BOTTOM -> if (kek.bounds.contains(actionX, actionY)) {
                        clickListener?.onClick(DrawablePosition.BOTTOM)
                        return super.onTouchEvent(event)
                    }
                    LEFT -> {
                        bounds = kek.bounds

                        var x: Int
                        var y: Int
                        val extraTapArea = (13 * resources.displayMetrics.density + 0.5).toInt()

                        x = actionX
                        y = actionY

                        if (!bounds.contains(actionX, actionY)) {
                            x = (actionX - extraTapArea)
                            y = (actionY - extraTapArea)

                            if (x <= 0)
                                x = actionX
                            if (y <= 0)
                                y = actionY

                            if (x < y) {
                                y = x
                            }
                        }

                        if (bounds.contains(x, y)) {
                            clickListener?.onClick(DrawablePosition.LEFT)
                            event.action = MotionEvent.ACTION_CANCEL
                            return false

                        }
                    }
                    RIGHT -> {
                        bounds = kek.bounds

                        var x: Int
                        var y: Int
                        val extraTapArea = (13 * resources.displayMetrics.density + 0.5).toInt()

                        x = (actionX + extraTapArea)
                        y = (actionY - extraTapArea)
                        x = width - x

                        if (x <= 0) {
                            x += extraTapArea;
                        }

                        if (y <= 0) {
                            y = actionY
                        }

                        if (bounds.contains(x, y)) {
                            clickListener?.onClick(DrawablePosition.RIGHT)
                            event.action = MotionEvent.ACTION_CANCEL
                            return false
                        }
                    }
                }
                return super.onTouchEvent(event)
            }
            return super.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    interface DrawableClickListener {
        enum class DrawablePosition {
            TOP, BOTTOM, LEFT, RIGHT
        }

        fun onClick(target: DrawablePosition)
    }
}