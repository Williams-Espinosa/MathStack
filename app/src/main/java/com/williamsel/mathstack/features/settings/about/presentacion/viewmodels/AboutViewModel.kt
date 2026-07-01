package com.williamsel.mathstack.features.settings.about.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import com.williamsel.mathstack.features.settings.about.presentacion.screens.AboutTab
import com.williamsel.mathstack.features.settings.about.presentacion.screens.AboutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    fun onTabSelected(tab: AboutTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
