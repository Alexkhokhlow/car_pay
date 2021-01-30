package com.example.mycar.dataBase.event

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Event::class],version = 1)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object{
        private val eventDataBase:EventDatabase? = null
        fun getInstance(context: Context) : EventDatabase{
            synchronized(this){
                var instance = eventDataBase
                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            EventDatabase::class.java,
                            "EventDB"
                    ).build()
                }
                return instance
            }
        }
    }
}