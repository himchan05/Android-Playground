package moi.next.Room
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DRINK_TABLE")
data class Drink(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: String,
    val sizes: List<String>,
    val basic: List<String>,
    val ice: List<String>,
    val description: String
)