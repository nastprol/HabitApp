package com.example.hw_fragment.ui.new_habit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_fragment.internal.HabitApplication
import com.example.hw_fragment.db.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewHabitViewModel(private val habitIndex: Int?) : ViewModel() {
    val habit: LiveData<Habit?> = HabitApplication.database.habitDao().getById(habitIndex)

    fun save(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            val habitDao = HabitApplication.database.habitDao()
            if (habitIndex == -1) {
                habitDao.insert(habit)
            } else {
                habit.id = habitIndex
                habitDao.update(habit)
            }
        }
    }
}