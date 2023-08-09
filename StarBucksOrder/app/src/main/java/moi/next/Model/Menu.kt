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
    val basic: List<String>,
    val ice: List<String>,
    val description: String
)

data class Category(
    val name: String,
    val drinks: List<Drink>
)