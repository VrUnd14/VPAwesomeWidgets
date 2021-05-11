package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.InputFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.ColorUtils

class VPEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val mContext = context

    private var backColor = 0xFFF1F1F1.toInt()
        set(value) {
            field = value
            updateUI()
        }
    private var hasBorder = true
        set(value) {
            field = value
            updateUI()
        }

    private var borderColor = 0xFFc3c3c3.toInt()
        set(value) {
            field = value
            updateUI()
        }
    private var isEnable = true
        set(value) {
            field = value
            updateUI()
        }

    private var textAllCaps = true
        set(value) {
            field = value
            updateUI()
        }

    init {
        this.setBackgroundResource(R.drawable.vp_auto_back)
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPEditText)

        backColor = parent.getColor(R.styleable.VPEditText_et_backColor, backColor)
        hasBorder = parent.getBoolean(R.styleable.VPEditText_et_hasBorder, hasBorder)

        borderColor = parent.getColor(R.styleable.VPEditText_et_borderColor, borderColor)
        isEnable = parent.getBoolean(R.styleable.VPEditText_et_enable, isEnabled)
        textAllCaps = parent.getBoolean(R.styleable.VPAutoCompleteTextView_act_textAllCaps, textAllCaps)

        parent.recycle()
    }

    private fun updateUI() {
        val tinColor = borderColor.takeIf { isEnabled }
                ?: ColorUtils.blendARGB(borderColor, Color.WHITE, 0.6f)

        // Main
        val mainGD = this.background as GradientDrawable
        mainGD.setColor(backColor)
        mainGD.setStroke(1.takeIf { hasBorder } ?: 0, tinColor)
        this.background = mainGD
        this.isEnabled = isEnabled

        if (textAllCaps)
            this.filters = arrayOf(InputFilter.AllCaps())
        else
            this.filters = arrayOf()
    }
}