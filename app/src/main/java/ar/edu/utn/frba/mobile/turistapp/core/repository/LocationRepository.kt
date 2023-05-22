package ar.edu.utn.frba.mobile.turistapp.core.repository

import android.content.Context
import androidx.lifecycle.LiveData
import ar.edu.utn.frba.mobile.turistapp.core.models.Location

class LocationRepository(private val context: Context) {
    fun loadData(): LiveData<List<Location>> {
        // Load and return your data here
        return TODO("Provide the return value")
    }
}
