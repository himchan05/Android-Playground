package moi.next.Room
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {
    @Query("SELECT * FROM DRINK_TABLE ORDER BY id ASC")
    fun getDrinks(): Flow<Drinks>

    @Query("SELECT * FROM DRINK_TABLE WHERE id = :id")
    suspend fun getDrink(id: Int): Drink

    @Insert(onConflict = IGNORE)
    suspend fun addDrink(drink: Drink)

    @Update
    suspend fun updateDrink(drink: Drink)

    @Delete
    suspend fun deleteDrink(drink: Drink)
}