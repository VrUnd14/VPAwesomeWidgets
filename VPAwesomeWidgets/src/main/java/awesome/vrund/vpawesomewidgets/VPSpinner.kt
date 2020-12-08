package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.Filterable
import android.widget.RelativeLayout
import android.widget.SpinnerAdapter
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import kotlinx.android.synthetic.main.vp_awesome_widget.view.*

class VPSpinner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        RelativeLayout(context, attrs, defStyleAttr) {

    private val mContext = context

    private var cornerRadius = dpToPx(5F)
    private var backColor = 0xFFF1F1F1.toInt()
    private var hasBorder = true

    private var hasLabel = true
    private var labelText = ""
    private var labelTextSize = spToPx(14F)
    private var labelTextColor = 0xFF666666.toInt()

    private var dropSize = dpToPx(36F)
    private var dropIcon = ContextCompat.getDrawable(mContext, R.drawable.vp_drop_icon)
    private var dropIconTint = 0xFF666666.toInt()

    private var tinColor = 0xFFc3c3c3.toInt()
    private var enable = true

    var itemSelectedListener: OnItemSelectedListener? = null

    fun getView(): VPSpinner {
        return this
    }

    init {
        View.inflate(mContext, R.layout.vp_awesome_widget, this)
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPSpinner)

        cornerRadius = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_cornerRadius, cornerRadius)
        backColor = parent.getColor(R.styleable.VPSpinner_sp_backColor, backColor)
        hasBorder = parent.getBoolean(R.styleable.VPSpinner_sp_hasBorder, hasBorder)

        hasLabel = parent.getBoolean(R.styleable.VPSpinner_sp_hasLabel, hasLabel)
        if (parent.hasValue(R.styleable.VPSpinner_sp_labelText))
            labelText = parent.getString(R.styleable.VPSpinner_sp_labelText).toString()
        labelTextSize = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_labelTextSize, labelTextSize)
        labelTextColor = parent.getColor(R.styleable.VPSpinner_sp_labelTextColor, labelTextColor)

        dropSize = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_dropSize, dropSize)
        dropIcon = ContextCompat.getDrawable(mContext, parent.getResourceId(R.styleable.VPSpinner_sp_dropIcon, R.drawable.vp_drop_icon))
        dropIconTint = parent.getColor(R.styleable.VPSpinner_sp_dropIconTint, dropIconTint)

        tinColor = parent.getColor(R.styleable.VPSpinner_sp_tint, tinColor)
        enable = parent.getBoolean(R.styleable.VPSpinner_sp_enable, enable)
        parent.recycle()

        vpSpinner.visibility = View.VISIBLE
        vpSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.selectedItem
                itemSelectedListener?.onItemSelected(getView(), item, position)
            }

        }

        updateUI()
    }

    private fun updateUI() {

        tinColor = tinColor.takeIf { enable } ?: ColorUtils.blendARGB(tinColor, Color.WHITE,0.6f)
        labelTextColor = labelTextColor.takeIf { enable } ?: ColorUtils.blendARGB(labelTextColor, Color.WHITE,0.5f)
        dropIconTint = dropIconTint.takeIf { enable } ?: ColorUtils.blendARGB(dropIconTint, Color.WHITE,0.5f)

        // Main
        val mainGD = vpParentLayout.background as GradientDrawable
        mainGD.setColor(backColor)
//        mainGD.cornerRadius = cornerRadius.toFloat()
        if (hasBorder)
            mainGD.setStroke(1, tinColor)
        else
            mainGD.setStroke(0, tinColor)

        // Label
        if (hasLabel) {
            vpLabel.visibility = View.VISIBLE
            curveImg.visibility = View.VISIBLE
            setSpinnerLeftMargin(28F)
        } else {
            setSpinnerLeftMargin(0F)
            vpLabel.visibility = View.GONE
            curveImg.visibility = View.GONE
        }
        vpLabel.text = labelText
        vpLabel.setTextColor(labelTextColor)
        vpLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize.toFloat())
        val labelGD = vpLabel.background as GradientDrawable
//        labelGD.cornerRadii = floatArrayOf(cornerRadius.toFloat(), cornerRadius.toFloat(), 0f, 0f, 0f, 0f, cornerRadius.toFloat(), cornerRadius.toFloat())
        labelGD.setColor(tinColor)
        curveImg.setColorFilter(tinColor, PorterDuff.Mode.SRC_ATOP)

        // Drop
        val dropGD = vpDropFrame.background as GradientDrawable
//        dropGD.cornerRadii = floatArrayOf(0f, 0f, cornerRadius.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat(), 0f, 0f)
        val params = vpDropFrame.layoutParams
        params.width = dropSize
        vpDropFrame.layoutParams = params
        vpDropIcon.setImageDrawable(dropIcon)
        vpDropIcon.setColorFilter(dropIconTint, PorterDuff.Mode.SRC_ATOP)
        dropGD.setColor(tinColor)

        vpSpinner.isEnabled = enable
        vpDropFrame.isEnabled = enable
    }

    private fun setSpinnerLeftMargin(margin: Float) {
        val params = vpSpinner.layoutParams as MarginLayoutParams
        params.setMargins(dpToPx(margin), 0, 0, 0)
        vpSpinner.layoutParams = params
    }

    fun setCorners(corner: Int) {
        cornerRadius = corner
        updateUI()
    }

    fun getCorners(): Int {
        return cornerRadius
    }

    fun setBackColor(color: Int) {
        backColor = color
        updateUI()
    }

    fun getBackColor(): Int {
        return backColor
    }

    fun showLabel(show: Boolean) {
        hasLabel = show
        updateUI()
    }

    fun hasLabel(): Boolean {
        return hasLabel
    }

    fun setLabel(label: String) {
        this.labelText = label
        updateUI()
    }

    fun getLabel(): String {
        return labelText
    }

    fun setLabelTextSize(size: Int) {
        labelTextSize = size
        updateUI()
    }

    fun getLabelTextSize(): Int {
        return labelTextSize
    }

    fun setLabelTextColor(color: Int) {
        labelTextColor = color
        updateUI()
    }

    fun getLabelTextColor(): Int {
        return labelTextColor
    }

    fun setDropSize(size: Int) {
        dropSize = size
        updateUI()
    }

    fun getDropSize(): Int {
        return dropSize
    }

    fun setDropIcon(icon: Int) {
        dropIcon = ContextCompat.getDrawable(mContext, icon)
        updateUI()
    }

    fun setDropIcon(icon: Drawable) {
        dropIcon = icon
        updateUI()
    }

    fun getDropIcon(): Drawable? {
        return dropIcon
    }

    fun setIconTint(color: Int) {
        dropIconTint = color
        updateUI()
    }

    fun getIconTint(): Int {
        return dropIconTint
    }

    fun setTint(color: Int) {
        tinColor = color
        updateUI()
    }

    fun getTint(): Int {
        return tinColor
    }

    fun setSelection(position: Int) {
        vpSpinner.setSelection(position)
    }

    fun getSelectedPosition(): Int {
        return vpSpinner.selectedItemPosition
    }

    fun setEnable(enable: Boolean) {
        this.enable = enable
        updateUI()
    }

    fun isEnable(): Boolean {
        return enable
    }

    fun <T> setAdapter(adapter: T) where T : SpinnerAdapter?, T : Filterable? {
        vpSpinner.adapter = adapter
    }

    fun getAdapter(): SpinnerAdapter? {
        return vpSpinner.adapter
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.resources.displayMetrics).toInt()
    }

    private fun spToPx(sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.resources.displayMetrics).toInt()
    }

    interface OnItemSelectedListener {
        fun onItemSelected(view: VPSpinner, selectedItem: Any?, position: Int)
    }
}