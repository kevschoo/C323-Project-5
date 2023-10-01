package com.example.project5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class SharedViewModel : ViewModel()
{
    // LiveData to hold the text that needs to be translated
    private val _sourceLanguage = MutableLiveData<String>()
    val sourceLanguage: LiveData<String> get() = _sourceLanguage

    private val _targetLanguage = MutableLiveData<String>()
    val targetLanguage: LiveData<String> get() = _targetLanguage

    private val _textToTranslate = MutableLiveData<String>()
    val textToTranslate: LiveData<String> get() = _textToTranslate

    private val _translatedText = MutableLiveData<String>()
    val translatedText: LiveData<String> get() = _translatedText

    private var translator: Translator? = null

    /**
     * Sets the source language for translation.
     * @param language The language to set as the source.
     */
    fun setSourceLanguage(language: String) {_sourceLanguage.value = language }

    /**
     * Sets the target language for translation.
     * @param language The language to set as the target.
     */
    fun setTargetLanguage(language: String) {_targetLanguage.value = language}

    /**
     * Updates the text to be translated and triggers the translation process.
     * @param text The text to be translated.
     */
    fun setText(text: String)
    {
        _textToTranslate.value = text
        translateText()
    }

    /**
     * Translates the text based on the selected source and target languages.
     * Uses ML Kit's Translation API to perform the translation.
     */
    fun translateText()
    {
        // Set up translation options
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(languageCodeToMlKitLanguage(_sourceLanguage.value))
            .setTargetLanguage(languageCodeToMlKitLanguage(_targetLanguage.value))
            .build()

        val translator = Translation.getClient(options)

        // Conditions for downloading the translation model
        val conditions = DownloadConditions.Builder().requireWifi().build()
        // Download the translation model if needed and perform the translation
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                translator.translate(_textToTranslate.value ?: "")
                    .addOnSuccessListener { translatedText ->_translatedText.postValue(translatedText)}
                    .addOnFailureListener { exception -> }
            }
            .addOnFailureListener { exception -> }
    }

    /**
     * Converts the language name to its corresponding ML Kit language code.
     * @param language The name of the language.
     * @return The ML Kit language code.
     */
    private fun languageCodeToMlKitLanguage(language: String?): String
    {
        return when (language)
        {
            "English" -> TranslateLanguage.ENGLISH
            "Spanish" -> TranslateLanguage.SPANISH
            "German" -> TranslateLanguage.GERMAN
            else -> TranslateLanguage.ENGLISH
        }
    }

}