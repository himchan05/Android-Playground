package moi.next.Room

import kotlinx.coroutines.flow.Flow

typealias  Drinks = List<Drink>
interface DrinkRepository {

    fun getDrinksFromRoom(): Flow<Drinks>

    suspend fun getDrinkFromRoom(id: Int): Drink

    suspend fun addDrinkToRoom(drink: Drink)

    suspend fun updateDrinkInRoom(drink: Drink)

    suspend fun deleteDrinkFromRoom(drink: Drink)
}

class DrinkRepositoryImpl(
    private val drinkDao: DrinkDao
) : DrinkRepository {
    override fun getDrinksFromRoom() = drinkDao.getDrinks()
    override suspend fun getDrinkFromRoom(id: Int) = drinkDao.getDrink(id)
    override suspend fun addDrinkToRoom(drink: Drink) = drinkDao.addDrink(drink)
    override suspend fun updateDrinkInRoom(drink: Drink) = drinkDao.updateDrink(drink)
    override suspend fun deleteDrinkFromRoom(drink: Drink) = drinkDao.deleteDrink(drink)
    
}