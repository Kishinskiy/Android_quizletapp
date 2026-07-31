package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActiviyLearnWorldBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActiviyLearnWorldBinding

    // Списки вью для удобного перебора всех 4 вариантов
    private lateinit var answerLayouts: List<View>
    private lateinit var numberTextViews: List<TextView>
    private lateinit var variantTextViews: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActiviyLearnWorldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем списки после inflate binding
        answerLayouts = listOf(binding.layoutAnswer1, binding.layoutAnswer2, binding.layoutAnswer3, binding.layoutAnswer4)
        numberTextViews = listOf(binding.textViewNumber1, binding.textViewNumber2, binding.textViewNumber3, binding.textViewNumber4)
        variantTextViews = listOf(binding.textViewVariant1, binding.textViewVariant2, binding.textViewVariant3, binding.textViewVariant4)

        with(binding) {
            Logo.text = "Galaxy"
            Logo.setTextColor(Color.GRAY)
            closeButton.isVisible = false
        }

        // Назначаем клики для всех 4 вариантов (ответ 3 — верный, остальные — нет)
        answerLayouts.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                val isCorrect = (index == 2) // Индекс 2 соответствует layoutAnswer3
                handleAnswer(index, isCorrect)
            }
        }

        // Кнопка продолжения сбрасывает экраны к начальному состоянию
        binding.btnContinue.setOnClickListener {
            resetAnswersToDefault()
        }
    }

    // Функция-расширение для удобного получения цвета
    fun Context.getAppColor(resId: Int): Int = ContextCompat.getColor(this, resId)

    /**
     * Обработка выбора ответа.
     * @param selectedIndex Индекс нажатого варианта (от 0 до 3).
     * @param isCorrect Флаг, верный ли это ответ.
     */
    private fun handleAnswer(selectedIndex: Int, isCorrect: Boolean) {
        // Выбираем ресурсы в зависимости от правильности ответа
        val colorRes = if (isCorrect) R.color.correctAnswerColor else R.color.wrongAnswerColor
        val containerDrawableRes = if (isCorrect) R.drawable.shape_rounded_container_correct else R.drawable.shape_rounded_container_wrong
        val variantDrawableRes = if (isCorrect) R.drawable.shape_rounded_variants_correct else R.drawable.shape_rounded_variants_wrong
        val iconRes = if (isCorrect) R.drawable.ic_correct else R.drawable.ic_wrong
        val textRes = if (isCorrect) R.string.correct else R.string.wrong

        val mainColor = getAppColor(colorRes)

        // Подсвечиваем только выбранный Layout и его тексты
        answerLayouts[selectedIndex].background = ContextCompat.getDrawable(this, containerDrawableRes)
        numberTextViews[selectedIndex].background = ContextCompat.getDrawable(this, variantDrawableRes)
        numberTextViews[selectedIndex].setTextColor(getAppColor(R.color.white))
        variantTextViews[selectedIndex].setTextColor(mainColor)

        // Блокируем клики по всем вариантам, пока пользователь не нажмет Continue
        answerLayouts.forEach { it.isClickable = false }

        // Показываем плашку результата
        with(binding) {
            skipButton.isVisible = false
            layoutResult.setBackgroundColor(mainColor)
            layoutResult.isVisible = true
            resultIcon.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, iconRes))
            resultText.text = resources.getString(textRes)
            btnContinue.setTextColor(mainColor)
        }
    }

    /**
     * Сброс всех стилей вариантов ответов в изначальное состояние.
     * Замените R.color.default_... и R.drawable.shape_rounded_container_default
     * на ваши реальные ресурсы по умолчанию.
     */
    private fun resetAnswersToDefault() {
        answerLayouts.forEachIndexed { index, layout ->
            // Возвращаем дефолтные фоны и цвета текста (укажите ваши реальные ресурсы вместо дефолтных)
            layout.background = ContextCompat.getDrawable(this, R.drawable.shape_rounded_container)
            layout.isClickable = true // Снова разрешаем клики

            numberTextViews[index].background = ContextCompat.getDrawable(this, R.drawable.shape_rounded_variants)
            numberTextViews[index].setTextColor(getAppColor(R.color.textVariantsColor))
            variantTextViews[index].setTextColor(getAppColor(R.color.textVariantsColor))
        }

        // Скрываем плашку результата обратно
        with(binding) {
            layoutResult.isVisible = false
            skipButton.isVisible = true
        }
    }
}
