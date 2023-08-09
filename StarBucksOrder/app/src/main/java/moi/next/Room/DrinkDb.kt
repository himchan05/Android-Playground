package moi.next.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Order::class], version = 1, exportSchema = false)
abstract class OrderDb : RoomDatabase() {
    abstract val OrderDao: OrderDao
}