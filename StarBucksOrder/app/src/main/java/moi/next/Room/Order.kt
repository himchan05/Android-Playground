package moi.next.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ORDER_TABLE")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: String,
    val sizes: String,
    val basic: String,
    val ice: String,
    val description: String
)