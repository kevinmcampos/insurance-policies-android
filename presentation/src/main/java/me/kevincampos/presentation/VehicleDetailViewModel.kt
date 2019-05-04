package me.kevincampos.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*
import me.kevincampos.domain.interactor.GetVehicleByVrmUseCase
import me.kevincampos.domain.util.Event
import me.kevincampos.domain.util.Result
import me.kevincampos.presentation.mapper.VehicleWithPoliciesViewMapper
import me.kevincampos.presentation.model.VehicleWithPoliciesView
import me.kevincampos.presentation.util.CoroutinesDispatcherProvider
import javax.inject.Inject

class VehicleDetailViewModel @Inject constructor(
    private val getVehicleByVrm: GetVehicleByVrmUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val mapper: VehicleWithPoliciesViewMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<VehicleDetailUiState>()
    val uiState: LiveData<VehicleDetailUiState>
        get() = _uiState

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun loadVehicle(vrm: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            loadVehicleFromCache(vrm)
        }
    }

    private suspend fun loadVehicleFromCache(vrm: String) {
        val cacheResult = getVehicleByVrm(vrm)

        withContext(dispatcherProvider.main) {
            if (cacheResult is Result.Success) {
                _uiState.value = VehicleDetailUiState.successWithLoading(mapper.mapToView(cacheResult.data))
            } else {
                _uiState.value = VehicleDetailUiState.loading()
            }
        }
    }

    fun closeScreen() {
        _uiState.value = _uiState.value?.withClose()
    }

}

class VehicleDetailUiState private constructor(
    val isLoading: Boolean,
    val vehicleWithPolicies: VehicleWithPoliciesView?,
    val shouldClose: Event<Unit>?
) {

    companion object {
        fun success(data: VehicleWithPoliciesView): VehicleDetailUiState {
            return VehicleDetailUiState(false, data, null)
        }

        fun successWithLoading(data: VehicleWithPoliciesView): VehicleDetailUiState {
            return VehicleDetailUiState(true, data, null)
        }

        fun loading(): VehicleDetailUiState {
            return VehicleDetailUiState(true, null, null)
        }

    }

    fun withClose(): VehicleDetailUiState {
        return VehicleDetailUiState(false, vehicleWithPolicies, Event(Unit))
    }

}
