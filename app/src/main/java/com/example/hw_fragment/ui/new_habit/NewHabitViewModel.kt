package com.example.hw_fragment.ui.new_habit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hw_fragment.internal.HabitApplication
import com.example.hw_fragment.db.Habit


class NewHabitViewModel(private val habitIndex: Int?) : ViewModel() {
    val habit: LiveData<Habit?> = HabitApplication.database.habitDao().getById(habitIndex)

    fun save(habit: Habit) {
        val habitDao = HabitApplication.database.habitDao()
        if (habitIndex == -1) {
            habitDao.insert(habit)
            Log.d("TAGTAGTAG0", habitDao.getAll().toString())
        } else {
            habit.id = habitIndex
            habitDao.update(habit)
            Log.d("TAGTAGTAG1", habitDao.getAll().toString())

        }
    }
}