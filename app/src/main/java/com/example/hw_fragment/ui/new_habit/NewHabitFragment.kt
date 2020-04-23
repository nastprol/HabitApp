package com.example.hw_fragment.ui.new_habit

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.Storage
import kotlinx.android.synthetic.main.new_habit_fragment.*
import java.util.*


class NewHabitFragment : Fragment() {

    companion object {
        const val INDEX = "INDEX"
    }

    lateinit var newHabitViewModel: NewHabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index = requireArguments().getInt(INDEX)

        newHabitViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewHabitViewModel(index) as T
            }
        }).get(NewHabitViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_habit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name.hint = "Habit " + Storage.curIndex.toString()
        name.text = SpannableStringBuilder( "Habit " + Storage.curIndex.toString())
        var index : Int? = null
        if (requireArguments().containsKey(INDEX)) {
            index = requireArguments().getInt(INDEX)
            val editedHabit = Storage.get(index)

            newHabitViewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
                habit?.let { fillEditedHabitFields(editedHabit) }
            })
        }

        add.setOnClickListener { _: View ->
            if (!isFilled()) {
                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "Fill all params",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                return@setOnClickListener
            }
            newHabitViewModel.save(createHabit(index))
            view.findNavController().navigateUp()
        }
    }

    private fun isFilled() : Boolean {
        return !(name.text.toString().isEmpty() ||
                periodicity.text.toString().isEmpty() ||
                quantity.text.toString().isEmpty() ||
                type.checkedRadioButtonId == -1)
    }

    private fun fillEditedHabitFields(editedHabit: Habit?) {
        if (editedHabit != null) {
            name.text = SpannableStringBuilder(editedHabit.name)
            description.text = SpannableStringBuilder(editedHabit.description)
            priority.progress = editedHabit.priority
            type.check(if (editedHabit.type == HabitType.GOOD) typeGoodBtn.id else typeBadBtn.id)
            quantity.text = SpannableStringBuilder(editedHabit.quantity.toString())
            periodicity.text = SpannableStringBuilder(editedHabit.periodicity.toString())
        }
    }

    private fun createHabit(index: Int?): Habit {
        return Habit(
            index,
            name.text.toString(),
            description.text.toString(),
            priority.progress,
            if (typeBadBtn.id == type.checkedRadioButtonId) HabitType.BAD else HabitType.GOOD,
            quantity.text.toString().toInt(),
            periodicity.text.toString().toInt(),
            Date()
        )
    }
}
