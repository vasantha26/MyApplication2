package com.ai.myapplication

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.ai.myapplication.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.action_settings)

        buildLanguageOptions()
    }

    private fun buildLanguageOptions() {
        val names = resources.getStringArray(R.array.language_names)
        val codes = resources.getStringArray(R.array.language_codes)

        val currentTag = AppCompatDelegate.getApplicationLocales()
            .toLanguageTags()
            .substringBefore(",")
            .substringBefore("-")

        names.forEachIndexed { index, name ->
            val radio = RadioButton(this).apply {
                id = View.generateViewId()
                text = name
                textSize = 16f
                tag = codes[index]
                setPadding(paddingLeft, 24, paddingRight, 24)
                isChecked = codes[index] == currentTag
            }
            binding.rgLanguage.addView(radio)
        }

        // listener-a options build panna PIRAKU set pannanum,
        // illena isChecked set pannumbodhu listener fire aagidum
        binding.rgLanguage.setOnCheckedChangeListener { group, checkedId ->
            val code = group.findViewById<RadioButton>(checkedId)?.tag as? String
                ?: return@setOnCheckedChangeListener
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(code)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
