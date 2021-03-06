package com.kletto.bot_tutorial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.kletto.bot_tutorial.R


class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<MaterialButton>(R.id.tutorial_button).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_tutorialFragment)
        }

        view.findViewById<MaterialButton>(R.id.level_button).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_levelFragment)
        }

        return view
    }

}