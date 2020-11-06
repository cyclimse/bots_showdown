package com.kletto.bot_tutorial.fragments.tutorials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.kletto.bot_tutorial.R
import com.kletto.bot_tutorial.models.TutorialItem


class Tutorial2Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tutorial2, container, false)

        val overview = TutorialItem()

        overview.title = "Tutorial 2 - The Revival"

        overview.description = "Welcome to the tutorial for the revival level! \n" + "\n" +
                "In the coming level you have to be on full alert!\n"+ "\n" + "Galactus has regained some of its powers and is now functioning at 50% capacity.\n"+ "\n"+
                "To beat this level you will need to up the power to your robot to push Galactus out\n"+ "\n" +
                "The main objective for you during this level is to figure out the what is enough power to defeat Galactus in order to move on to the next tutorial\n" +"\n" +
                "Like in the previous level you give the robot command by dragging the command into the coloured part of the text editor.\n"+
                "\n"+ "Good luck in your battle with Galactus!"

        val title = view.findViewById<TextView>(R.id.tutorial1_title)
        val description = view.findViewById<TextView>(R.id.tutorial1_description)

        title.text = overview.title
        description.text = overview.description

        view.findViewById<MaterialButton>(R.id.go_to_level2_button).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_tutorial2Fragment_to_level2Fragment)
        }

        return view
    }
}