package com.example.mycar.dataBase.car

import androidx.room.*
import com.example.mycar.dataBase.car.Car

@Dao
interface CarDao {

    @Insert
    suspend fun insertCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("delete  from CarDB")
    suspend fun delete()

    @Update
    suspend fun updateCar(car: Car)

    @Query("select * from CarDB")
    suspend fun getCars():List<Car>

    @Query("select * from CarDB where carId = :id")
    suspend fun getCar(id:Long): Car

    @Query("select * from CarDB where selectCar = 1")
    suspend fun getSelectCar(): Car

    @Query("select carId from CarDB where selectCar = 1")
    suspend fun getSelectCarId():Long

    @Query("update CarDB set selectCar = 0")
    suspend fun clearSelectCar()

    @Query("update CarDB set selectCar = 1 where carId = :id")
    suspend fun selectCar(id:Long)
}