package com.example.myapplication.Tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Adapter.TestPagerAdapter
import com.example.myapplication.Data.TestViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAllotTestBinding
import com.google.android.material.tabs.TabLayoutMediator

class AllotTestFragment : Fragment() {
    lateinit var binding: FragmentAllotTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllotTestBinding.inflate(inflater, container, false)
        binding.apply {
            viewPagerTest.adapter = TestPagerAdapter(requireActivity())
            TabLayoutMediator(tabTest, viewPagerTest){tab, position ->
                when(position){
                    0 -> tab.text = "Groups"
                    1 -> tab.text = "Students"
                }
            }.attach()
        }

        return binding.root
    }
}