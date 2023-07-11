package moi.next.Model

import android.content.Context
import android.util.Log
import android.util.LogPrinter
import android.view.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.json.JSONObject
import java.io.IOException
import java.util.logging.Logger
data class Drink(
    val name: String,
    val price: String,
    val sizes: List<String>,
    val description: String
)

data class Category(
    val name: String,
    val drinks: List<Drink>
)

class DrinksViewModel: ViewModel() {
//    var categories by mutableStateOf<List<Category>>(emptyList())

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    val _selectedDrink = MutableLiveData<Drink>()
    val selectedDrink: LiveData<Drink> = _selectedDrink
    //
//    val categories: List<Category>()
    lateinit var jsonString: String



    fun fetchDrinks(context: Context) {
        try {
            jsonString = context.assets.open("drink.json")
                .bufferedReader()
                .use { it.readText() }
            Log.e("text", "noError")
        } catch (e: Exception) {
            Log.e("text", "Error")
        }
        val typeToken = object : TypeToken<List<Category>>() {}.type
        _categories.value = Gson().fromJson<List<Category>>(jsonString, typeToken)
    }
}