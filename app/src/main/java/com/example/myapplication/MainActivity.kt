package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActiviyLearnWorldBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActiviyLearnWorldBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActiviyLearnWorldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            Logo.text = "Galaxy"
            Logo.setTextColor(Color.GRAY)
            closeButton.isVisible = false
        }

        binding.layoutAnswer3.setOnClickListener {
            markAnswerCorrect()
        }

        binding.layoutAnswer1.setOnClickListener {
            markAnswerWrong()
        }
    }

    // Функция-расширение для удобного получения цвета
    fun Context.getAppColor(resId: Int): Int = ContextCompat.getColor(this, resId)

    private fun markAnswerCorrect(){
        val correctColor = getAppColor(R.color.correctAnswerColor)

        with(binding) {
            layoutAnswer3.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_container_correct)
            textViewNumber3.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_variants_correct)
            textViewNumber3.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))

            textViewVariant3.setTextColor(correctColor)
            skipButton.isVisible = false

            layoutResult.setBackgroundColor(correctColor)
            layoutResult.isVisible = true

            resultIcon.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_correct))
            resultText.text = resources.getString(R.string.correct)
            btnContinue.setTextColor(correctColor)
        }

    }

    private fun markAnswerWrong(){
        val wrongColor = getAppColor(R.color.wrongAnswerColor)

        with(binding) {
            layoutAnswer1.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_container_wrong)
            textViewNumber1.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_variants_wrong)
            textViewNumber1.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))

            textViewVariant1.setTextColor(wrongColor)
            skipButton.isVisible = false

            layoutResult.setBackgroundColor(wrongColor)
            layoutResult.isVisible = true

            resultIcon.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_wrong))
            resultText.text = resources.getString(R.string.wrong)
            btnContinue.setTextColor(wrongColor)
        }

    }


}