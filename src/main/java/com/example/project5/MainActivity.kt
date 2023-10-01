package com.example.project5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.project5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SharedViewModel by viewModels()

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, TranslateFragment())
        transaction.commit()

        binding.radioGroupSource.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId)
            {
                R.id.radioEnglishInput -> viewModel.setSourceLanguage("English")
                R.id.radioSpanishInput -> viewModel.setSourceLanguage("Spanish")
                R.id.radioGermanInput -> viewModel.setSourceLanguage("German")
            }
        }

        binding.radioGroupTarget.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId)
            {
                R.id.radioEnglishOutput -> viewModel.setTargetLanguage("English")
                R.id.radioSpanishOutput -> viewModel.setTargetLanguage("Spanish")
                R.id.radioGermanOutput -> viewModel.setTargetLanguage("German")
            }
        }

        // Observe changes in the source language and trigger translation
        viewModel.sourceLanguage.observe(this, { viewModel.translateText() })

        // Observe changes in the target language and trigger translation
        viewModel.targetLanguage.observe(this, { viewModel.translateText() })

        // Observe changes in the translated text and update the UI
        viewModel.translatedText.observe(this, { translatedText -> binding.translatedText.text = translatedText })
    }
}