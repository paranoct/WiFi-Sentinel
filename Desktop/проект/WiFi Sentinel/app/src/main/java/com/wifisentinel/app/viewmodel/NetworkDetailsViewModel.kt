package com.wifisentinel.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.feature.networkdetails.NetworkDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NetworkDetailsViewModel @Inject constructor(
    repository: NetworkRepository
) : ViewModel() {
    val uiState: StateFlow<NetworkDetailsUiState> = repository.latestSnapshot()
        .flatMapLatest { snapshot ->
            val findingsFlow = snapshot?.let { repository.findingsForSnapshot(it.id) } ?: flowOf(emptyList())
            findingsFlow.map { findings ->
                NetworkDetailsUiState(snapshot = snapshot, findings = findings)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NetworkDetailsUiState())
}
