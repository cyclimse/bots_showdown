package com.kletto.bot_tutorial.fragments.levels

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.kletto.bot_tutorial.R

class Level1Fragment : Fragment() {

    lateinit var forwardCommand: TextView
    lateinit var rightCommand: TextView
    lateinit var dragTo: TextView

    val dragAndDrop: DragAndDrop
    init{
        dragAndDrop = DragAndDrop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_level1, container, false)

        val onClickLong = dragAndDrop.onLongClickListener
        val onClickDrag = dragAndDrop.onDragListener

        val sharedPref =
            this.requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        val code = sharedPref.getString("USER_CODE", "").toString()
        if (code != "") {
            view.findViewById<TextView>(R.id.last_part_of_code).text = code
            val toast = Toast.makeText(this.context, code, Toast.LENGTH_LONG)
            toast.show()
        }

        val compile: MaterialButton
        compile = view.findViewById(R.id.compile)

        compile.setOnClickListener {
            val textFieldCode = view.findViewById<TextView>(R.id.last_part_of_code).text.toString()
            if (textFieldCode == "") {

            } else {
                val editor: SharedPreferences.Editor = sharedPref.edit()
                sharedPref.edit().remove("USER_CODE").commit();
                editor.putString("USER_CODE", textFieldCode)
                editor.apply()
            }
        }

        forwardCommand = view.findViewById(R.id.correctItem)
        rightCommand = view.findViewById(R.id.notcorrectItem)
        dragTo = view.findViewById(R.id.last_part_of_code)

        dragTo.setOnDragListener(onClickDrag)
        forwardCommand.setOnLongClickListener(onClickLong)
        rightCommand.setOnLongClickListener(onClickLong)

        view.findViewById<MaterialButton>(R.id.back_to_tutorial).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_level1Fragment_to_tutorial1Fragment)
        }
        view.findViewById<MaterialButton>(R.id.clear_textView).setOnClickListener{
            dragTo.text = ""
            sharedPref.edit().remove("USER_CODE2").commit();
        }

        return view
    }
}