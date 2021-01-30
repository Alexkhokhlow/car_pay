package com.example.mycar.dataBase.car

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1)
abstract class CarDataBase: RoomDatabase() {
    abstract fun carDao(): CarDao

    companion object {
       private val carDataBase: CarDataBase? = null
        fun getInstance(context: Context): CarDataBase {
            synchronized(this) {
                var instance = carDataBase
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CarDataBase::class.java,
                        "CarBD"
                    ).build()
                }
                return instance
            }
        }
    }
}