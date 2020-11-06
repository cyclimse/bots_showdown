package com.kletto.bot_tutorial.fragments.levels

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.kletto.bot_tutorial.GameActivity
import com.kletto.bot_tutorial.R
import com.kletto.bot_tutorial.fragments.levels.DragAndDrop as DragAndDrop

class Level2Fragment : Fragment() {

    lateinit var forwardCommand: TextView
    lateinit var rightCommand: TextView
    lateinit var dragTo: TextView
    lateinit var power: EditText
    lateinit var changePowerBut:MaterialButton

    val dragAndDrop: DragAndDrop
    init{
        dragAndDrop = DragAndDrop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_level2, container, false)



        val onClickLong = dragAndDrop.onLongClickListener
        val onClickDrag = dragAndDrop.onDragListener

        val sharedPref =
            this.requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        val code = sharedPref.getString("USER_CODE2", "").toString()
        if (code != "") {
            view.findViewById<TextView>(R.id.last_part_of_code).text = code
            val toast = Toast.makeText(this.context, code, Toast.LENGTH_LONG)
            toast.show()
        }
        else{
           //code = getString(R.string)
        }

        val compile: MaterialButton
        compile = view.findViewById(R.id.compile)

        compile.setOnClickListener {
            val textFieldCode = view.findViewById<TextView>(R.id.last_part_of_code).text.toString()
            if (textFieldCode == "") {
            } else {
                val editor: SharedPreferences.Editor = sharedPref.edit()
                sharedPref.edit().remove("USER_CODE2").commit();
                editor.putString("USER_CODE2", textFieldCode)
                editor.apply()
            }
            val editor =  this.requireActivity().getSharedPreferences("scripts", Context.MODE_PRIVATE).edit()
            val totalCode = view.findViewById<TextView>(R.id.first_part_of_code).text.toString() + "\n" +  textFieldCode + "\n" + view.findViewById<TextView>(R.id.text_view_last).text.toString();
            editor.putString("USER_SCRIPT2", totalCode)
            editor.apply()
            val intent = Intent(activity, GameActivity::class.java)
            intent.putExtra("codeIndex", "USER_SCRIPT2");
            startActivity(intent)
        }

        forwardCommand = view.findViewById(R.id.command_forward)
        rightCommand = view.findViewById(R.id.command_right)
        dragTo = view.findViewById(R.id.last_part_of_code)
        power = view.findViewById(R.id.power_to_bot)
        changePowerBut = view.findViewById(R.id.change_power)

        dragTo.setOnDragListener(onClickDrag)
        forwardCommand.setOnLongClickListener(onClickLong)
        rightCommand.setOnLongClickListener(onClickLong)


        view.findViewById<MaterialButton>(R.id.back_to_tutorial).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_level2Fragment_to_tutorial2Fragment)
        }
        view.findViewById<MaterialButton>(R.id.clear_textView).setOnClickListener{
            dragTo.text = ""
            sharedPref.edit().remove("USER_CODE2").commit();
        }
        changePowerBut.setOnClickListener {
            if (dragTo.text.toString() == ""){
                val toast = Toast.makeText(this.context, "you have to drag command before changing the value", Toast.LENGTH_LONG)
                toast.show()
            }
            else {
                val powerToBot = power.text.toString()
                val new = code.replace("[0-9]".toRegex(), powerToBot)
                val editor: SharedPreferences.Editor = sharedPref.edit()
                sharedPref.edit().remove("USER_CODE2").commit();
                editor.putString("USER_CODE2", new)
                editor.apply()
                val toast = Toast.makeText(this.context, new, Toast.LENGTH_LONG)
                power.text.clear()
                toast.show()
            }
        }

        return view
    }

}