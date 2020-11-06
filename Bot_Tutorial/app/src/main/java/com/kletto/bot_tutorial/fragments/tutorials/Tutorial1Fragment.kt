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

class Tutorial1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_tutorial1, container, false)

        val overview = TutorialItem()

        overview.title = "Tutorial 1 - The breakdown"

        overview.description = "Welcome to the first tutorial of Bot Showdown! \n" + "\n" +
                "It appears that Galactus has broken down and given you an advantage.\n"+ "\n" + "In this tutorial, your objective is to push Galactus out of the Arena to win the round!\n"+ "\n"+
                "To do this you need to command your robot to move forward until you hit Galactus and push him out.\n"+ "\n" +
                "As for the commands needed to control the robot, you will see a text field with a SmallTalk function, your task is to drag and drop the move forward command which is in the command box," +
                " to the textfield, once you do this you will see the actual smallTalk code for commanding the robot which you can study and and press compile\n"+
                "\n"+ "but you must move quick before Galactus resets!"

        val title = view.findViewById<TextView>(R.id.tutorial1_title)
        val description = view.findViewById<TextView>(R.id.tutorial1_description)

        title.text = overview.title
        description.text = overview.description

        view.findViewById<MaterialButton>(R.id.go_to_level1_button).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_tutorial1Fragment_to_level1Fragment)
        }


        return view
    }

}