package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class VPIconTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val mContext = context

    companion object {
        const val LEFT = 0
        const val TOP = 1
        const val RIGHT = 2
        const val BOTTOM = 3
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

    var iconTint = 0
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

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPIconTextView)

        icon = parent.getResourceId(R.styleable.VPIconTextView_icon, icon)
        showIcon = parent.getBoolean(R.styleable.VPIconTextView_showIcon, showIcon)
        iconSize = parent.getDimensionPixelSize(R.styleable.VPIconTextView_icon_size, iconSize)
        iconTint = parent.getColor(R.styleable.VPIconTextView_iconTint, iconTint)

        if (parent.hasValue(R.styleable.VPIconTextView_icon_position)) {
            iconPosition = parent.getInt(R.styleable.VPIconTextView_icon_position, LEFT)
        }

        parent.recycle()
    }

    private fun setupIcon() {
        when (iconPosition) {
            LEFT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(resizeIcon(), null, null, null)
            TOP -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resizeIcon(), null, null)
            RIGHT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resizeIcon(), null)
            BOTTOM -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizeIcon())
        }
    }

    private fun resizeIcon(): Drawable? {
        if (showIcon) {
            try {
                var item = mContext.getDrawableFromRes(icon)
                item = WrappedDrawable(item!!)
                item.setBounds(0,0, iconSize, iconSize)
                if (iconTint != 0) {
//                    DrawableCompat.setTint(item, iconTint)
                    item.setColorFilter(iconTint, PorterDuff.Mode.SRC_ATOP)
                }
                return item
            } catch (e: Exception) {
            }
        }
        return null
    }

    inner class WrappedDrawable(private val drawable: Drawable) : Drawable() {

        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            //update bounds to get correctly
            super.setBounds(left, top, right, bottom)
            val drawable: Drawable? = drawable
            drawable?.setBounds(left, top, right, bottom)
        }

        override fun setAlpha(alpha: Int) {
            val drawable: Drawable? = drawable
            if (drawable != null) {
                drawable.alpha = alpha
            }
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            val drawable: Drawable? = drawable
            if (drawable != null) {
                drawable.colorFilter = colorFilter
            }
        }

        override fun getOpacity(): Int {
            val drawable: Drawable? = drawable
            return drawable?.opacity ?: PixelFormat.UNKNOWN
        }

        override fun draw(canvas: Canvas) {
            val drawable: Drawable? = drawable
            drawable?.draw(canvas)
        }

        override fun getIntrinsicWidth(): Int {
            val drawable: Drawable? = drawable
            return drawable?.bounds?.width() ?: 0
        }

        override fun getIntrinsicHeight(): Int {
            val drawable: Drawable? = drawable
            return drawable?.bounds?.height() ?: 0
        }

    }
}