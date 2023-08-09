package moi.next.ViewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import moi.next.Model.Category
import moi.next.Model.Drink
import moi.next.Room.Order
import moi.next.Room.OrderRepository
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(
    private val repo: OrderRepository
): ViewModel() {

//    var book by mutableStateOf(Book(0, NO_VALUE, NO_VALUE))
//        private set
//    var openDialog by mutableStateOf(false)
//
//    val books = repo.getBooksFromRoom()
    var drink by mutableStateOf(Order(0, "", "", "", "", "", ""))
    var openDialog by mutableStateOf(false)
    val orders = repo.getDrinksFromRoom()

    fun getOrder(id: Int) = viewModelScope.launch {
        drink = repo.getDrinkFromRoom(id)
    }

    fun addOrder(order: Order) = viewModelScope.launch {
        repo.addDrinkToRoom(order)
    }

    fun updateOrder(order: Order) = viewModelScope.launch {
        repo.updateDrinkInRoom(order)
    }

    fun deleteOrder(order: Order) = viewModelScope.launch {
        repo.deleteDrinkFromRoom(order)
    }

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    val _selectedDrink = MutableLiveData<Drink>()
    val selectedDrink: LiveData<Drink> = _selectedDrink

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