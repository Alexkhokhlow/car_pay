package com.example.mycar.dataBase.event

import androidx.room.*


@Dao
interface EventDao {

    @Insert
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("select * from EventDB where carId = :id")
    suspend fun getEvents(id:Long):List<Event>

    @Query("select * from EventDB where eventId = :id")
    suspend fun getEvent(id:Long):Event

    @Query("delete from EventDB where carId = :id ")
    suspend fun deleteEventsForCar(id:Long)
}