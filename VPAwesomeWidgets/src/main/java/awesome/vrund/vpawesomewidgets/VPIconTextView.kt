package awesome.vrund.vpawesomewidgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import awesome.vrund.vpawesomewidgets.VPIconTextView.DrawableClickListener.DrawablePosition


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

    var drawableClickListener: DrawableClickListener? = null

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
                val bitmap = (item as BitmapDrawable).bitmap
                item = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, true))
                if (iconTint != 0) {
                    DrawableCompat.setTint(item, iconTint)
                }
                return LayerDrawable(arrayOf(item))
            } catch (e: Exception) {
            }
        }
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && showIcon && resizeIcon() != null) {
            when (iconPosition) {
                LEFT -> if(event.rawX <= (this.left + this.totalPaddingLeft)) {
                    drawableClickListener?.onClick(DrawablePosition.LEFT)
                    return true
                }
                TOP -> if(event.rawY <= (this.top + this.totalPaddingTop)) {
                    drawableClickListener?.onClick(DrawablePosition.TOP)
                    return true
                }
                RIGHT -> if(event.rawX <= (this.right + this.totalPaddingEnd)) {
                    drawableClickListener?.onClick(DrawablePosition.RIGHT)
                    return true
                }
                BOTTOM -> if(event.rawY <= (this.bottom + this.totalPaddingBottom)) {
                    drawableClickListener?.onClick(DrawablePosition.BOTTOM)
                    return true
                }
            }
            return false
        }
        return false
    }

    interface DrawableClickListener {
        enum class DrawablePosition {
            TOP, BOTTOM, LEFT, RIGHT
        }

        fun onClick(target: DrawablePosition)
    }
}