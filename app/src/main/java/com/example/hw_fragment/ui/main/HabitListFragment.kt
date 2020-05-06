package com.example.hw_fragment.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.findNavController
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.ui.infrastructure.HabitListAdapter
import kotlinx.android.synthetic.main.habit_list.*


class HabitListFragment : Fragment() {

    private val viewModel: HabitListViewModel by activityViewModels()
    private var habitType = HabitType.BAD

    companion object {
        private const val TYPE = "TYPE"
        private const val INDEX = "INDEX"

        fun newInstance(habitType: HabitType): HabitListFragment {

            return HabitListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TYPE, habitType)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            habitType = it.getSerializable(TYPE) as HabitType
        }

        habit_recycler_view.adapter = HabitListAdapter(listOf()) { itemClicked ->
            val bundle = Bundle().apply {
                putSerializable(INDEX, itemClicked.id)
            }
            view.findNavController()
                .navigate(R.id.from_main_to_new_habit_fragment, bundle)
        }

        habit_recycler_view.layoutManager = LinearLayoutManager(context)
        habit_recycler_view.layoutAnimation = AnimationUtils.loadLayoutAnimation(
            habit_recycler_view.context,
            R.anim.layout_animation_waterfall
        )

        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            (habit_recycler_view.adapter as HabitListAdapter).update(when (habitType) {
                HabitType.GOOD -> habits.filter { it.type == HabitType.GOOD }
                else -> habits.filter { it.type == HabitType.BAD }
            }
            )
            (habit_recycler_view.adapter as HabitListAdapter).notifyDataSetChanged()
        })
    }
}
