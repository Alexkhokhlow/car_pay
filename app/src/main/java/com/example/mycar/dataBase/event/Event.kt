package com.example.mycar.dataBase.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EventDB")
data class Event(
        val carId: Long,
        @PrimaryKey(autoGenerate = true)
        val eventId: Long,
        var eventName: String,
        var eventType: String,
        var eventWorkType: String,
        var eventPrise: String,
        var eventMileage: String,
        var eventComment: String
)