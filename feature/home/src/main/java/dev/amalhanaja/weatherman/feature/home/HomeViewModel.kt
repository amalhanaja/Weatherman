package dev.amalhanaja.weatherman.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amalhanaja.weatherman.core.model.City
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    val cityListUiState: StateFlow<CityListUiState> = snapshotFlow { searchQuery }
        .flatMapLatest {
            flowOf((1..4).map { City("Name $it", null, "ID", 0.0, 0.0) })
        }.map<List<City>, CityListUiState> { CityListUiState.WithData(true, it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CityListUiState.Empty,
        )

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

}
