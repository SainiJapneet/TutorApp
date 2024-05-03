package com.example.myapplication.Tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAllotTestToGroupBinding

class AllotTestToGroupFragment : Fragment() {
    lateinit var binding: FragmentAllotTestToGroupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllotTestToGroupBinding.inflate(inflater, container, false)
        return binding.root
    }
}