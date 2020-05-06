package com.example.hw_fragment.ui.main

import androidx.lifecycle.*
import com.example.hw_fragment.internal.HabitApplication
import com.example.hw_fragment.db.Habit
import java.util.*


class HabitListViewModel : ViewModel() {
    private val searchedString: MutableLiveData<String> = MutableLiveData()
    private val allHabits: LiveData<List<Habit>> =
        HabitApplication.database.habitDao().getAll()

    val habits: MediatorLiveData<List<Habit>> = MediatorLiveData()

    init {
        habits.addSource(allHabits) { newHabits ->
            habits.value = newHabits.toList()
        }
        habits.addSource(searchedString) { updatedSubstring ->
            habits.value = allHabits.value?.filter {
                updatedSubstring.isNullOrBlank() ||
                        updatedSubstring in it.name.toLowerCase(Locale.getDefault())
            } ?: listOf()
        }
    }

    fun sortDate() {
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortName() {
        habits.value = habits.value?.sortedBy { it.name.toLowerCase(Locale.ROOT) }
    }

    fun sortPriority() {
        habits.value = habits.value?.sortedByDescending { it.priority }
    }

    fun updateNameFilter(newString: String) {
        searchedString.value = newString.toLowerCase(Locale.getDefault())
    }
}