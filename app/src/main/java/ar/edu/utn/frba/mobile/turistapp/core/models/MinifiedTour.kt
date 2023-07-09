package ar.edu.utn.frba.mobile.turistapp.core.models

import org.intellij.lang.annotations.Language

data class MinifiedTour(
    val id: Int,
    val title: String,
    val description: String,
    val languages: Set<String>,
    val distance: Double,
    val image: String
) {
    fun getMinifiedByLanguage() {}
}