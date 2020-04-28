package com.example.hw_fragment.ui.new_habit

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.R
import com.example.hw_fragment.db.Habit
import kotlinx.android.synthetic.main.new_habit_fragment.*
import java.util.*


class NewHabitFragment : Fragment() {

    companion object {
        private const val INDEX = "INDEX"
    }

    private lateinit var newHabitViewModel: NewHabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index = requireArguments().getInt(INDEX)

        newHabitViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewHabitViewModel(index) as T
            }
        }).get(NewHabitViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_habit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name.hint = view.context.getString(R.string.habit)
        name.text =
            SpannableStringBuilder(view.context.getString(R.string.habit))

        if (requireArguments().containsKey(INDEX)) {

            newHabitViewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
                habit?.let { fillEditedHabitFields(habit) }
            })
        }

        priority.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                priority_text.text = "$i"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        add.setOnClickListener { _: View ->
            if (!isFilled()) {
                val toast = Toast.makeText(
                    activity?.applicationContext,
                    view.context.getString(R.string.fill_all_params),
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                return@setOnClickListener
            }
            newHabitViewModel.save(createHabit())
            view.findNavController().navigateUp()
        }
    }

    private fun isFilled(): Boolean {
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

    private fun createHabit(): Habit {
        return Habit(
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
