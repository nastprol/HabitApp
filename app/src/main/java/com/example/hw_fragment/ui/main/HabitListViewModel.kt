package com.example.hw_fragment.ui.main

import androidx.lifecycle.*
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.Storage
import java.util.*


class HabitListViewModel : ViewModel() {
    val searchedString: MutableLiveData<String> = MutableLiveData()

    val habits: MediatorLiveData<List<Habit>> = MediatorLiveData()

    init {
        habits.addSource(Storage.mutableHabits) { newHabits ->
            habits.value = newHabits.values.toList()
        }
        habits.addSource(searchedString) { updatedSubstring ->
            habits.value = Storage.mutableHabits.value?.values?.filter { updatedSubstring.isNullOrBlank() ||
                        updatedSubstring in it.name.toLowerCase(Locale.getDefault())
            }
        }
    }

    fun sortDate() {
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortName () {
        habits.value = habits.value?.sortedBy { it.name.toLowerCase(Locale.ROOT) }
    }

    fun sortPriority () {
        habits.value = habits.value?.sortedByDescending { it.priority }
    }

    fun updateNameFilter(newString: String) {
        searchedString.value = newString.toLowerCase(Locale.getDefault())
    }
}