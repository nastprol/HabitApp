package com.example.hw_fragment.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}