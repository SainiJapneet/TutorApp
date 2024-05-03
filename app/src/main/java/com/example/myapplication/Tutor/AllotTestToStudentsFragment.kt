package com.example.myapplication.Tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAllotTestToStudentsBinding

class AllotTestToStudentsFragment : Fragment() {
    lateinit var binding: FragmentAllotTestToStudentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllotTestToStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }
}