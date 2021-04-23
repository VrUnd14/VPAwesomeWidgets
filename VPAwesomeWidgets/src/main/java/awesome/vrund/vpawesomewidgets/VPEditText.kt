package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.ColorUtils

class VPEditText @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val mContext = context

    private var backColor = 0xFFF1F1F1.toInt()
    private var hasBorder = true

    private var borderColor = 0xFFc3c3c3.toInt()
    private var enable = true

    init {
        this.setBackgroundResource(R.drawable.vp_auto_back)
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPEditText)

        backColor = parent.getColor(R.styleable.VPEditText_et_backColor, backColor)
        hasBorder = parent.getBoolean(R.styleable.VPEditText_et_hasBorder, hasBorder)

        borderColor = parent.getColor(R.styleable.VPEditText_et_borderColor, borderColor)
        enable = parent.getBoolean(R.styleable.VPEditText_et_enable, enable)

        parent.recycle()

        updateUI()
    }

    private fun updateUI() {
        val tinColor = borderColor.takeIf { enable } ?: ColorUtils.blendARGB(borderColor, Color.WHITE,0.6f)

        // Main
        val mainGD = this.background as GradientDrawable
        mainGD.setColor(backColor)
        if (hasBorder)
            mainGD.setStroke(1, tinColor)
        else
            mainGD.setStroke(0, tinColor)
        this.background = mainGD
        this.isEnabled = enable
    }

    fun setBackColor(color: Int) {
        backColor = color
        updateUI()
    }

    fun getBackColor(): Int {
        return backColor
    }

    fun setBoarderColor(color: Int) {
        borderColor = color
        updateUI()
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun isEnable(): Boolean {
        return enable
    }
}