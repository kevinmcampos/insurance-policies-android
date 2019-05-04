package me.kevincampos.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.StringRes
import kotlinx.coroutines.*
import me.kevincampos.domain.interactor.GetAllVehiclesUseCase
import me.kevincampos.domain.interactor.SynchronizePolicyEventsUseCase
import me.kevincampos.domain.util.Event
import me.kevincampos.domain.util.Result
import me.kevincampos.presentation.mapper.VehicleWithPoliciesViewMapper
import me.kevincampos.presentation.model.VehicleWithPoliciesView
import me.kevincampos.presentation.util.CoroutinesDispatcherProvider
import javax.inject.Inject

class VehicleListViewModel @Inject constructor(
    private val synchronizePolicyEvents: SynchronizePolicyEventsUseCase,
    private val getAllVehiclesUseCase: GetAllVehiclesUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val mapper: VehicleWithPoliciesViewMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<VehicleListUiState>()
    val uiState: LiveData<VehicleListUiState>
        get() = _uiState

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _uiState.value = VehicleListUiState.loading()

        viewModelScope.launch(dispatcherProvider.io) {
            loadVehiclesFromCache()
        }
    }

    private suspend fun loadVehiclesFromCache() {
        val cacheResult = getAllVehiclesUseCase()

        withContext(dispatcherProvider.main) {
            if (cacheResult is Result.Success) {
                _uiState.value = VehicleListUiState.successWithLoading(cacheResult.data.map { mapper.mapToView(it) })
            }
        }

        fetchVehiclesFromRemote()
    }

    private suspend fun fetchVehiclesFromRemote() {
        val result = synchronizePolicyEvents()

        if (result is Result.Success) {
            val cacheResult = getAllVehiclesUseCase()

            if (cacheResult is Result.Success) {
                withContext(dispatcherProvider.main) {
                    _uiState.value = VehicleListUiState.success(cacheResult.data.map { mapper.mapToView(it) })
                }
            }
        } else {
            withContext(dispatcherProvider.main) {
                _uiState.value = _uiState.value?.withError(R.string.failed)
            }
        }

    }

    fun refresh() {
        _uiState.value = _uiState.value?.withLoading()

        viewModelScope.launch(dispatcherProvider.io) {
            fetchVehiclesFromRemote()
        }
    }

}

class VehicleListUiState private constructor(
    val isLoading: Boolean,
    val vehiclesWithActivePolicies: List<VehicleWithPoliciesView>?,
    val showError: Event<Int>?
) {

    companion object {
        fun success(data: List<VehicleWithPoliciesView>): VehicleListUiState {
            return VehicleListUiState(false, data, null)
        }

        fun successWithLoading(data: List<VehicleWithPoliciesView>): VehicleListUiState {
            return VehicleListUiState(true, data, null)
        }

        fun loading(): VehicleListUiState {
            return VehicleListUiState(true, null, null)
        }
    }

    fun withLoading(): VehicleListUiState {
        return VehicleListUiState(true, vehiclesWithActivePolicies, null)
    }

    fun withError(@StringRes errorStringRes: Int): VehicleListUiState {
        return VehicleListUiState(false, vehiclesWithActivePolicies, Event(errorStringRes))
    }

}
