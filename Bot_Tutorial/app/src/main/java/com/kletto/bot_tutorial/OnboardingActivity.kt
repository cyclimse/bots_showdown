package com.kletto.bot_tutorial

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.kletto.bot_tutorial.models.OnboardingItem
import com.kletto.bot_tutorial.onboarding.OnboardingAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var onboardingViewPager : ViewPager2
    private lateinit var layoutOnboardingIndicators : LinearLayout

    private lateinit var buttonOnboardingAction: MaterialButton
    private var hasSeen = false
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        hasSeen = sharedPref.getBoolean("HAS_SEEN", false)
        if (hasSeen){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            setContentView(R.layout.activity_main)


            layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators)
            buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction)

            setupOnboardingItems()
            onboardingViewPager = findViewById(R.id.onboardingViewPager)
            onboardingViewPager.adapter = onboardingAdapter

            setupOnboardingIndicators()
            setCurrentOnboardingIndicator(0)

            onboardingViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {

                    super.onPageSelected(position)
                    setCurrentOnboardingIndicator(position)
                }
            })

            buttonOnboardingAction.setOnClickListener {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                if (onboardingViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                    onboardingViewPager.currentItem = onboardingViewPager.currentItem + 1
                } else {
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putBoolean("HAS_SEEN", true)
                    editor.apply()
                    startActivity(intent)
                    finish()
                }
            }
        }


    }

    private fun setupOnboardingItems(){
        var onboardingItems = ArrayList<OnboardingItem>()

        val overview = OnboardingItem()

        overview.title = "Welcome to Bot Showdown!"
        overview.description = "Bot Showdown is an app made to help individuals that want to learn programming.\n " + "\n" +
                "Once you open the app you will be presented with tutorials for how to write a small program in the programming language SmallTalk.\n " + "\n" +
                "finish a tutorial and then try your luck against the evil robot lord Galactus"
        overview.image = R.drawable.place_holder

        val tutorials = OnboardingItem()
        tutorials.title = "The tutorials"
        tutorials.description = "Before each level you will be presented with a tutorial to help you get through the levels.\n" + "\n" +
                "Go through the tutorial and try to beat Galactus!"
        tutorials.image = R.drawable.place_holder

        val levels = OnboardingItem()
        levels.title = "The levels"
        levels.description = "After each tutorial you will be presented with a level to beat.\n" +
                "\n" +
                "each level will get incrementally more difficult as you progress so it's important you get a good base to build upon! \n" +
                "\n" +
                "thank you for downloading Bot Showdown, and good luck in your fight againt Galactus\n"
        levels.image = R.drawable.place_holder

        onboardingItems.add(overview)
        onboardingItems.add(tutorials)
        onboardingItems.add(levels)

        onboardingAdapter = OnboardingAdapter(onboardingItems)

    }

    private fun setupOnboardingIndicators(){
        var indicators : Array<ImageView?> = arrayOfNulls(onboardingAdapter.itemCount)

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0,8,0)
        for (x in 0 until (indicators.size)){
            indicators[x] = ImageView(applicationContext)
            indicators[x]?.setImageDrawable(ContextCompat.getDrawable(
                applicationContext,
                R.drawable.onboarding_indicator_inactive
            ))
            indicators[x]!!.layoutParams = layoutParams
            layoutOnboardingIndicators.addView(indicators[x])
        }
    }

    private fun setCurrentOnboardingIndicator(index: Int) {
        var childCount : Int
        var imageView : ImageView
        childCount = layoutOnboardingIndicators.childCount
        for (x in 0 until childCount){
            imageView = layoutOnboardingIndicators.getChildAt(x) as ImageView
            if (x == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.onboarding_indicator_active)
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.onboarding_indicator_inactive)
                )
            }
        }
        if (index == onboardingAdapter.itemCount-1){
            buttonOnboardingAction.setText("Start")
        }
        else{
            buttonOnboardingAction.setText("Next")
        }
    }
}