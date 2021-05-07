package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat

fun Float.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
    ).toInt()
}

fun Float.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this,
            context.resources.displayMetrics
    ).toInt()
}

fun Context.getDrawableFromRes(id: Int): Drawable? {
    return try {
        ContextCompat.getDrawable(this, id)
    } catch (e: Exception) {
        null
    }
}