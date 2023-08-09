package moi.next.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import moi.next.Room.Order
import moi.next.Room.OrderDao
import moi.next.Room.OrderDb
import moi.next.Room.OrderRepository
import moi.next.Room.OrderRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideOrderDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        OrderDb::class.java,
        "ORDER_TABLE"
    ).build()

    @Provides
    fun provideOrderDao(
        orderDb: OrderDb
    ) = orderDb.OrderDao

    @Provides
    fun provideOrderRepository(
        orderDao: OrderDao
    ): OrderRepository = OrderRepositoryImpl(
        orderDao = orderDao
    )
}
