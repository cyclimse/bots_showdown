package com.kletto.bot_tutorial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.kletto.bot_tutorial.R

class LevelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_level, container, false)

        view.findViewById<MaterialButton>(R.id.level2).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_levelFragment_to_level1Fragment)
        }


        return view
    }

}