package moi.next.Room
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM ORDER_TABLE ORDER BY id ASC")
    fun getOrders(): Flow<Drinks>

    @Query("SELECT * FROM ORDER_TABLE WHERE id = :id")
    suspend fun getOrder(id: Int): Order

    @Insert
    suspend fun addOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteDrink(order: Order)
}