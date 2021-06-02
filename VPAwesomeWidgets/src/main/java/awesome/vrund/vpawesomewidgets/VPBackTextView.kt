package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class VPBackTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val mContext = context

    var backColor = Color.TRANSPARENT
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setColor(backColor)
        }

    var isCapsule = false
        set(value) {
            field = value
            setCorners()
        }

    var cornerRadiusTopLeft = 0F
        set(value) {
            field = value
            setCorners()
        }

    var cornerRadiusBottomLeft = 0F
        set(value) {
            field = value
            setCorners()
        }
    var cornerRadiusTopRight = 0F
        set(value) {
            field = value
            setCorners()
        }
    var cornerRadiusBottomRight = 0F
        set(value) {
            field = value
            setCorners()
        }

    var borderColor = Color.BLACK
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setStroke(borderWidth.takeIf { hasBorder } ?: 0, borderColor)
        }

    var borderWidth = 0
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setStroke(borderWidth.takeIf { hasBorder } ?: 0, borderColor)
        }

    var hasBorder = true
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setStroke(borderWidth.takeIf { hasBorder } ?: 0, borderColor)
        }

    init {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        this.background = shape

        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPBackTextView)

        backColor = parent.getColor(R.styleable.VPBackTextView_backColor, backColor)
        isCapsule = parent.getBoolean(R.styleable.VPBackTextView_isCapsule, isCapsule)
        cornerRadiusTopLeft = parent.getDimensionPixelSize(R.styleable.VPBackTextView_cornerRadiusTopLeft, 0).toFloat()
        cornerRadiusTopRight = parent.getDimensionPixelSize(R.styleable.VPBackTextView_cornerRadiusTopRight, 0).toFloat()
        cornerRadiusBottomLeft = parent.getDimensionPixelSize(R.styleable.VPBackTextView_cornerRadiusBottomLeft, 0).toFloat()
        cornerRadiusBottomRight = parent.getDimensionPixelSize(R.styleable.VPBackTextView_cornerRadiusBottomRight, 0).toFloat()
        borderColor = parent.getColor(R.styleable.VPBackTextView_borderColor, borderColor)
        borderWidth = parent.getDimensionPixelSize(R.styleable.VPBackTextView_borderWidth, borderWidth)
        hasBorder = parent.getBoolean(R.styleable.VPBackTextView_hasBorder, hasBorder)

        parent.recycle()
    }

    private fun setCorners() {
        val dg = this.background as GradientDrawable
        if (isCapsule)
            dg.cornerRadii = floatArrayOf(200F, 200F, 200F, 200F, 200F, 200F, 200F, 200F)
        else
            dg.cornerRadii = floatArrayOf(cornerRadiusTopLeft, cornerRadiusTopLeft, cornerRadiusTopRight, cornerRadiusTopRight, cornerRadiusBottomRight, cornerRadiusBottomRight, cornerRadiusBottomLeft, cornerRadiusBottomLeft)
    }
}