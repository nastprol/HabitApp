package com.example.hw_fragment.ui.infrastructure

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType

class HabitListAdapter(var habits: List<Habit>, val clickListener: (Habit) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    fun update(newHabits: List<Habit>){
        habits = newHabits
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position], clickListener)
    }
}


class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(habit: Habit, clickListener: (Habit) -> Unit) {
        habitTitle.text = habit.name
        habitDescription.text = habit.description
        habitPriority.text = "Priority: " + habit.priority.toString()
        habitPeriod.text = habit.periodicity.toString()
        habitQuantity.text = habit.quantity.toString()

        val itemView = view.findViewById(R.id.new_list_item) as ConstraintLayout
        when (habit.type) {
            HabitType.BAD -> itemView.setBackgroundResource(R.drawable.oval_bad)
            else -> itemView.setBackgroundResource(R.drawable.oval_good)
        }

        view.setOnClickListener { clickListener(habit) }
    }

    val habitTitle = view.findViewById(R.id.habitTitle) as TextView
    val habitDescription = view.findViewById(R.id.descriptionText) as TextView
    val habitPriority = view.findViewById(R.id.stars) as TextView
    val habitPeriod = view.findViewById(R.id.habitPeriod) as TextView
    val habitQuantity = view.findViewById(R.id.habitQuantity) as TextView
}