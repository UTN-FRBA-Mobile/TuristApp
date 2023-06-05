package ar.edu.utn.frba.mobile.turistapp.core.utils

import android.content.Context
import java.util.Locale

enum class AvailableLanguages(val code: String, val resStringName: String) {
    English("en", "english"),
    Spanish("es", "spanish")
}

object LocaleUtils {
    private fun updateResources(context: Context, language: AvailableLanguages) {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        val resources = context.resources
        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun setLocale(c: Context, language: AvailableLanguages) = updateResources(c, language)

    fun currentLocale(): AvailableLanguages {
        return AvailableLanguages.values().find { it.code == Locale.getDefault().language } ?: AvailableLanguages.English
    }
}