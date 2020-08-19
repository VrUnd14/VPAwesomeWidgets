package awesome.vrund.vpawesomewidgetssample

import org.json.JSONObject
import java.io.Serializable

class CountryModel(j: JSONObject) : Serializable {
    var name = ""
    var dialCode = ""
    var code = ""

    init {
        name = j.optString("name")
        dialCode = j.optString("dial_code")
        code = j.optString("code")
    }

    override fun toString(): String {
        return name
    }
}