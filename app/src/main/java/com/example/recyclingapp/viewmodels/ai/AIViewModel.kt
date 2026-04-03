package com.example.recyclingapp.viewmodels.ai

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclingapp.dataClasses.ai.AIPrompter
import com.example.recyclingapp.dataClasses.ai.AIRetrofitApi
import com.example.recyclingapp.dataClasses.ai.OpenFoodFactsApi
import com.example.recyclingapp.dataClasses.ai.OpenFoodFactsContactor
import com.example.recyclingapp.dataClasses.database.Package
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Used to prompt AI to find food package descriptions, etc. based on the barcode
 * entered.
 *
 * Follows the AIRetrofitApi
 */
class AIViewModel(private val api: AIRetrofitApi): ViewModel() {

    private val mLogTag: String = "AIViewModel"
    private val ai = AIPrompter()
    private val _pkg = MutableLiveData<Package?>()
    val pkg: LiveData<Package?> = _pkg

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val openFoodFactsApi: OpenFoodFactsApi = OpenFoodFactsContactor.api //Get the retrofit/moshi connection api!

    /**
     * Returns invalid JSON obj,
     */
    fun generateNotFoundJson(barcode: String): JSONObject {
        return JSONObject(
            """
        {
          "barcode": null,
          "name": null,
          "recycling_pos": null,
          "image_link": null,
          "description": "No product found for this barcode.",
          "verified": false
        }
        """.trimIndent()
        )
    }


    /**
     * Updates the Live Data with the package data found.
     */
    fun scan(barcode: String) {
        if(!barcode.isBlank()) {
            viewModelScope.launch {
                try {
                    val item = openFoodFactsApi.getProduct(barcode)  //Get the nme of the barcode!
                    var data: JSONObject? = null
                    Log.d(mLogTag, "OPEN FOOD FACTS!/n${item.product?.productName}")

                    //Check for the name found!
                    if (item.product?.productName == null || item.status == 0 || item.product == null) {
                        data = generateNotFoundJson(barcode)
                    }

                    if(data == null) {  //A corresponding item was identified from the website!
                        data = ai.lookupItem(item.product!!.productName, barcode, api)   //Find the item via its name
                    }

                    if (!data.getString("barcode").equals("null")) {
                        val pkgFound = Package(
                            barcode = barcode,
                            name = data.getString("name"),
                            recycling_pos = data.getBoolean("recycling_pos"),
                            image_link = null,
                            description = data.getString("description"),
                            verified = false
                        )

                        _pkg.value = pkgFound
                    } else {
                        Log.d(mLogTag, "Barcode could not be found...")
                        _pkg.value = null;
                    }
                } catch (e: Exception) {
                    Log.e(mLogTag, "AI lookup failed", e)

                    when (e) {
                        is java.net.SocketTimeoutException -> {
                            _error.value = "Request timed out. This may be due to too many requests per minute. Please try again later."
                        }
                        is retrofit2.HttpException -> {
                            if (e.code() == 429) {
                                _error.value = "Too many requests today. Please wait a minute and try again."
                            } else {
                                _error.value = "Server error (${e.code()}). Please try again later."
                            }
                        }
                        else -> {
                            _error.value = "Network error. Please check your connection."
                        }
                    }

                    _pkg.value = null;  //On package lookup fail, set the val to null!
                }
            }
        }
    }
}