package com.example.hw_fragment.internal

import android.app.Application
import androidx.room.Room
import com.example.hw_fragment.db.AppDatabase


class HabitApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "HabitDataBase"
            )
            .allowMainThreadQueries()
            .build()
    }
}