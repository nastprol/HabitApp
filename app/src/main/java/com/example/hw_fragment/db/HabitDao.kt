package com.example.hw_fragment.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id=:id")
    fun getById(id: Int?): LiveData<Habit?>

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)
}