package moi.next.Room

import kotlinx.coroutines.flow.Flow

typealias  Drinks = List<Order>
interface OrderRepository {
    fun getDrinksFromRoom(): Flow<Drinks>

    suspend fun getDrinkFromRoom(id: Int): Order

    suspend fun addDrinkToRoom(order: Order)

    suspend fun updateDrinkInRoom(order: Order)

    suspend fun deleteDrinkFromRoom(order: Order)
}



class OrderRepositoryImpl(
    private val orderDao: OrderDao
): OrderRepository {
    override fun getDrinksFromRoom() = orderDao.getOrders()
    override suspend fun getDrinkFromRoom(id: Int) = orderDao.getOrder(id)
    override suspend fun addDrinkToRoom(order: Order) = orderDao.addOrder(order)
    override suspend fun updateDrinkInRoom(order: Order) = orderDao.updateOrder(order)
    override suspend fun deleteDrinkFromRoom(order: Order) = orderDao.deleteDrink(order)

}