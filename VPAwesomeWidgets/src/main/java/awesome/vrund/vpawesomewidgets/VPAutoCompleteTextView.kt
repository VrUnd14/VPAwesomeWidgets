package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Filterable
import android.widget.ListAdapter
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.vp_awesome_widget.view.*

class VPAutoCompleteTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        RelativeLayout(context, attrs, defStyleAttr) {

    private val mContext = context

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

    private var hint = ""
    private var text = ""
    private var hintColor = 0xFF808080.toInt()
    private var textColor = 0xFF666666.toInt()
    private var textSize = spToPx(14F)
    private var textStyle = 0
    private var threshold = 1

    var itemClickListener: OnItemClickListener? = null

    init {
        View.inflate(mContext, R.layout.vp_awesome_widget, this)
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPAutoCompleteTextView)

        backColor = parent.getColor(R.styleable.VPAutoCompleteTextView_act_backColor, backColor)
        hasBorder = parent.getBoolean(R.styleable.VPAutoCompleteTextView_act_hasBorder, hasBorder)

        hasLabel = parent.getBoolean(R.styleable.VPAutoCompleteTextView_act_hasLabel, hasLabel)
        if (parent.hasValue(R.styleable.VPAutoCompleteTextView_act_labelText))
            labelText = parent.getString(R.styleable.VPAutoCompleteTextView_act_labelText).toString()
        labelTextSize = parent.getDimensionPixelSize(R.styleable.VPAutoCompleteTextView_act_labelTextSize, labelTextSize)
        labelTextColor = parent.getColor(R.styleable.VPAutoCompleteTextView_act_labelTextColor, labelTextColor)

        dropSize = parent.getDimensionPixelSize(R.styleable.VPAutoCompleteTextView_act_dropSize, dropSize)
        dropIcon = ContextCompat.getDrawable(mContext, parent.getResourceId(R.styleable.VPAutoCompleteTextView_act_dropIcon, R.drawable.vp_drop_icon))
        dropIconTint = parent.getColor(R.styleable.VPAutoCompleteTextView_act_dropIconTint, dropIconTint)

        tinColor = parent.getColor(R.styleable.VPAutoCompleteTextView_act_tint, tinColor)

        vpAutoText.visibility = View.VISIBLE
        if (parent.hasValue(R.styleable.VPAutoCompleteTextView_act_hint))
            hint = parent.getString(R.styleable.VPAutoCompleteTextView_act_hint).toString()
        if (parent.hasValue(R.styleable.VPAutoCompleteTextView_act_text))
            text = parent.getString(R.styleable.VPAutoCompleteTextView_act_text).toString()
        hintColor = parent.getColor(R.styleable.VPAutoCompleteTextView_act_hintColor, hintColor)
        textColor = parent.getColor(R.styleable.VPAutoCompleteTextView_act_textColor, textColor)
        textSize = parent.getDimensionPixelSize(R.styleable.VPAutoCompleteTextView_act_textSize, textSize)
        textStyle = parent.getInt(R.styleable.VPAutoCompleteTextView_act_textStyle, textStyle)

        parent.recycle()

        updateUI()

        vpDropFrame.setOnClickListener {
            vpAutoText.showDropDown()
        }
        vpAutoText.onItemClickListener = MyItemClickListener(this)
    }

    private fun updateUI() {

        // Main
        val mainGD = vpParentLayout.background as GradientDrawable
        mainGD.setColor(backColor)
        if (hasBorder)
            mainGD.setStroke(1, tinColor)
        else
            mainGD.setStroke(0, tinColor)
        vpParentLayout.background = mainGD
        // Label
        if (hasLabel) {
            vpLabel.visibility = View.VISIBLE
            curveImg.visibility = View.VISIBLE
            vpAutoText.setPadding(dpToPx(28F), dpToPx(10F), dpToPx(10F), dpToPx(10F))
        } else {
            vpLabel.visibility = View.GONE
            curveImg.visibility = View.GONE
            vpAutoText.setPadding(dpToPx(10F), dpToPx(10F), dpToPx(10F), dpToPx(10F))
        }
        vpLabel.text = labelText
        vpLabel.setTextColor(labelTextColor)
        vpLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize.toFloat())
        val labelGD = vpLabel.background as GradientDrawable
        labelGD.setColor(tinColor)
        curveImg.setColorFilter(tinColor, PorterDuff.Mode.SRC_ATOP)

        // Drop
        val dropGD = vpDropFrame.background as GradientDrawable
        val params = vpDropFrame.layoutParams
        params.width = dropSize
        vpDropFrame.layoutParams = params
        vpDropIcon.setImageDrawable(dropIcon)
        vpDropIcon.setColorFilter(dropIconTint, PorterDuff.Mode.SRC_ATOP)
        dropGD.setColor(tinColor)

        // AutoCompleteTextView
        vpAutoText.hint = hint
        vpAutoText.setText(text)
        vpAutoText.setHintTextColor(hintColor)
        vpAutoText.setTextColor(textColor)
        vpAutoText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        vpAutoText.setTypeface(vpAutoText.typeface, textStyle)
        vpAutoText.threshold = threshold
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

    fun setHint(hint: String) {
        this.hint = hint
        updateUI()
    }

    fun getHint(): String {
        return hint
    }

    fun setHintColor(hintColor: Int) {
        this.hintColor = hintColor
        updateUI()
    }

    fun getHintColor(): Int {
        return hintColor
    }

    fun setText(text: String) {
        this.text = text
        updateUI()
    }

    fun getText(): String {
        return text
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        updateUI()
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextSize(size: Int) {
        this.textSize = size
        updateUI()
    }

    fun getTextSize(): Int {
        return textSize
    }

    fun setTextStyle(style: Int) {
        this.textStyle = style
        updateUI()
    }

    fun getTextStyle(): Int {
        return textStyle
    }

    fun setThreshold(threshold: Int) {
        this.threshold = threshold
        updateUI()
    }

    fun getThreshold(): Int {
        return threshold
    }

    fun <T> setAdapter(adapter: T) where T : ListAdapter?, T : Filterable? {
        vpAutoText.setAdapter(adapter)
    }

    fun getAdapter(): ListAdapter? {
        return vpAutoText.adapter
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.resources.displayMetrics).toInt()
    }

    private fun spToPx(sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.resources.displayMetrics).toInt()
    }

    interface OnItemClickListener {
        fun onItemClick(view: VPAutoCompleteTextView, position: Int)
    }

    inner class MyItemClickListener(myAc: VPAutoCompleteTextView) : AdapterView.OnItemClickListener {

        private var ac: VPAutoCompleteTextView = myAc

        override fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(ac.applicationWindowToken, 0)
            itemClickListener?.onItemClick(ac, position)
        }

    }
}