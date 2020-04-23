package com.example.hw_fragment.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Storage {
    private var id = 0

    val mutableHabits: MutableLiveData<MutableMap<Int, Habit>> =
        MutableLiveData(mutableMapOf())

    val habits: LiveData<MutableMap<Int, Habit>> = mutableHabits

    val curIndex: Int
        get() = id

    fun add(habit: Habit) {
        habit.id = id
        mutableHabits.value?.set(id, habit)
        id++
    }

    fun get(index: Int): Habit? {
        return mutableHabits.value?.get(index)
    }

    fun set(index: Int, habit: Habit) {
        mutableHabits.value?.set(index, habit)
    }

    fun modify(habit: Habit) {
        mutableHabits.value?.set(habit.id!!, habit)
    }
}