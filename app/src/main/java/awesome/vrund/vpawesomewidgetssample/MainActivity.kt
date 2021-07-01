package awesome.vrund.vpawesomewidgetssample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import awesome.vrund.vpawesomewidgets.VPAutoCompleteTextView
import awesome.vrund.vpawesomewidgets.VPBaseAdapter
import awesome.vrund.vpawesomewidgets.VPIconTextView
import awesome.vrund.vpawesomewidgets.VPSpinner
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity(), VPAutoCompleteTextView.OnItemClickListener, VPSpinner.OnItemSelectedListener {

    private val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countries = ArrayList<CountryModel>()
//        val countries = ArrayList<HashMap<String, String>>()
        val data = loadJSONFromAsset()
        val jArray = data?.optJSONArray("countries")
        for (i in 0 until jArray!!.length()) {
            val j = jArray.optJSONObject(i)
            val cm = CountryModel(j)
            countries.add(cm)
        }
//        for (i in 0 until jArray!!.length()) {
//            val j = jArray.optJSONObject(i)
//            val map = HashMap<String, String>()
//            map["name"] = j.optString("name")
//            map["dialCode"] = j.optString("dial_code")
//            map["code"] = j.optString("code")
//            countries.add(map)
//        }


        val spinnerAdp = ArrayAdapter<CountryModel>(context, android.R.layout.simple_list_item_1, countries)
//        countriesSpn1.setAdapter(spinnerAdp)
        countriesSpn1.itemSelectedListener = this
        countriesSpn2.setAdapter(spinnerAdp)
        countriesSpn2.itemSelectedListener = this
        countriesSpn3.setAdapter(spinnerAdp)
        countriesSpn3.itemSelectedListener = this
        countriesSpn4.setAdapter(spinnerAdp)
        countriesSpn4.itemSelectedListener = this
        countriesSpn4.setCorners(15)

        val dropAdp = ArrayAdapter<CountryModel>(context, android.R.layout.simple_list_item_1, countries)
//        countriesDrop1.setAdapter(dropAdp)
        countriesDrop1.itemClickListener = this

        val myDropAdp = VPBaseAdapter(context, countries)
        countriesDrop2.setAdapter(myDropAdp)
        countriesDrop2.itemClickListener = this


        demoTxt.icon = R.drawable.vp_drop_icon
        demoTxt.iconTint = Color.RED

//        demoTxt.drawableClickListener = object : VPIconTextView.DrawableClickListener {
//            override fun onClick(target: VPIconTextView.DrawableClickListener.DrawablePosition) {
//                Toast.makeText(context, target.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }

        demoTxt.setOnClickListener {
            Toast.makeText(context, "Working...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadJSONFromAsset(): JSONObject? {
        return try {
            val `is` = context.assets.open("countries.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            val json = String(buffer, Charset.defaultCharset())
            JSONObject(json)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    override fun onItemClick(view: VPAutoCompleteTextView, position: Int) {
        Toast.makeText(context, view.getAdapter()!!.getItem(position).toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(view: VPSpinner, selectedItem: Any?, position: Int) {
        Toast.makeText(context, selectedItem.toString(), Toast.LENGTH_SHORT).show()
    }
}