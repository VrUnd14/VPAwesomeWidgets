package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


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

    var iconPosition = LEFT
        set(value) {
            field = value
            setupIcon()
        }

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPIconTextView)

        icon = parent.getResourceId(R.styleable.VPIconTextView_icon, icon)
        iconSize = parent.getDimensionPixelSize(R.styleable.VPIconTextView_icon_size, iconSize)

        if (parent.hasValue(R.styleable.VPIconTextView_icon_position)) {
            iconPosition = parent.getInt(R.styleable.VPIconTextView_icon_position, LEFT)
        }

        parent.recycle()
    }
    private fun setupIcon() {
        when(iconPosition) {
            TOP -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resizeIcon(), null, null)
            BOTTOM -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizeIcon())
            LEFT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(resizeIcon(), null, null, null)
            RIGHT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resizeIcon(), null)
        }
    }

    private fun resizeIcon(): Drawable? {
        return try {
            var item = mContext.getDrawableFromRes(icon)
            val bitmap = (item as BitmapDrawable).bitmap
            item = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, true))
            LayerDrawable(arrayOf(item))
        } catch (e: Exception) {
            null
        }
    }
}