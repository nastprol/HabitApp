package com.example.hw_fragment.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.hw_fragment.R
import com.example.hw_fragment.ui.infrastructure.HabitTypeAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_fragment.FAB
import kotlinx.android.synthetic.main.main_fragment.tab_layout
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {
    private val viewModel: HabitListViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager_main_fragment.adapter =
            HabitTypeAdapter(this)
        FAB.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("INDEX", -1)
            }
            view.findNavController()
                .navigate(R.id.from_main_to_new_habit_fragment, bundle)
        }
        TabLayoutMediator(tab_layout, view_pager_main_fragment) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.bad)
                else -> getString(R.string.good)
            }

        }.attach()

        nameSubstr.addTextChangedListener {
            viewModel.updateNameFilter(it.toString())
        }

        sort_date.setOnClickListener {
            viewModel.sortDate()
            sort_date.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOn))
            sort_name.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOff))
            sort_priority.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOff))
        }

        sort_name.setOnClickListener {
            viewModel.sortName()
            sort_date.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOff))
            sort_priority.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOff))
            sort_name.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOn))
        }

        sort_priority.setOnClickListener {
            viewModel.sortPriority()
            sort_date.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOff))
            sort_name.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOff))
            sort_priority.setBackgroundColor(ContextCompat.getColor(view.context, R.color.BtnOn))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.main_fragment, container, false)
}
