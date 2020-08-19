package awesome.vrund.vpawesomewidgets

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import kotlinx.android.synthetic.main.vp_awesome_widget.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlinx.android.synthetic.main.vp_drop_item.view.*

class VPBaseAdapter<T>(context: Context, private var data: ArrayList<T>) : BaseAdapter(), Filterable {

    private var filterData = data
    private val inflater = LayoutInflater.from(context)
    private var filterTag = ""
    private var searchString = ""

    init {
        if (data.size>0) {
            if (data[0] is HashMap<*,*>) {
                filterTag = (data[0] as HashMap<*,*>).keys.toTypedArray()[0].toString()
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (convertView == null) {
            view = inflater.inflate(R.layout.vp_drop_item, null)
        }
        val name = if (getItem(position) is HashMap<*, *>) {
            if (filterTag.isNotEmpty()) {
                (getItem(position) as HashMap<*, *>)[filterTag].toString()
            } else {
                ""
            }
        } else {
            getItem(position).toString()
        }
        if (name.toLowerCase(Locale.getDefault()).contains(searchString, ignoreCase = true)) {
            val startPos = name.toLowerCase(Locale.getDefault()).indexOf(searchString.toLowerCase(Locale.getDefault()))
            val endPos = startPos + searchString.length
            val spanString = Spannable.Factory.getInstance().newSpannable(name)
            spanString.setSpan(ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            view?.txt!!.text = spanString
        } else {
            view?.txt!!.text = name
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (convertView == null) {
            view = inflater.inflate(R.layout.vp_drop_item, null)
        }
        val name = if (getItem(position) is HashMap<*, *>) {
            if (filterTag.isNotEmpty()) {
                (getItem(position) as HashMap<*, *>)[filterTag].toString()
            } else {
                ""
            }
        } else {
            getItem(position).toString()
        }
        if (name.toLowerCase(Locale.getDefault()).contains(searchString, ignoreCase = true)) {
            val startPos = name.toLowerCase(Locale.getDefault()).indexOf(searchString)
            val endPos = startPos + searchString.length
            val spanString = Spannable.Factory.getInstance().newSpannable(name)
            spanString.setSpan(ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            view?.txt!!.text = spanString
        } else {
            view?.txt!!.text = name
        }
        return view
    }

    override fun getItem(position: Int): Any? {
        return filterData[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return filterData.size
    }

    fun setFilterTag(tag: String) {
        filterTag = tag
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchString = constraint.toString().trim()
                filterData = if (searchString == "null" || searchString.isEmpty()) {
                    Log.e("mhvbdv","assasavasvavad")
                    data
                } else {
                    val filteredList = ArrayList<T>()
                    for (row in data) {
                        if (row is HashMap<*, *>) {
                            if (filterTag.isNotEmpty()) {
                                Log.e("dvhsd","${row[filterTag]} - $searchString")
                                if (row[filterTag].toString().toLowerCase(Locale.getDefault()).trim().contains(searchString.toLowerCase(Locale.getDefault()).trim())) {
                                    filteredList.add(row)
                                }
                            } else {
                                filteredList.add(row)
                            }
                        } else {
                            if (row.toString().toLowerCase(Locale.getDefault()).trim().contains(searchString.toLowerCase(Locale.getDefault()).trim())) {
                                filteredList.add(row)
                            }
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterData
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterData = results?.values as ArrayList<T>
                notifyDataSetChanged()
            }
        }
    }
}