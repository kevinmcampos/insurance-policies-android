package me.kevincampos.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.StringRes
import kotlinx.coroutines.*
import me.kevincampos.domain.interactor.GetFinancialTransactionsByPolicyIdUseCase
import me.kevincampos.domain.util.Event
import me.kevincampos.domain.util.Result
import me.kevincampos.presentation.mapper.FinancialTransactionViewMapper
import me.kevincampos.presentation.model.FinancialTransactionView
import me.kevincampos.presentation.util.CoroutinesDispatcherProvider
import javax.inject.Inject

class PolicyTransactionsViewModel @Inject constructor(
    private val getFinancialTransactionsByPolicyId: GetFinancialTransactionsByPolicyIdUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val mapper: FinancialTransactionViewMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<PolicyTransactionsUiState>()
    val uiState: LiveData<PolicyTransactionsUiState>
        get() = _uiState

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun loadTransactions(policyId: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            loadTransactionsFromCache(policyId)
        }
    }

    private suspend fun loadTransactionsFromCache(policyId: String) {
        val cacheResult = getFinancialTransactionsByPolicyId(policyId)

        withContext(dispatcherProvider.main) {
            if (cacheResult is Result.Success) {
                _uiState.value = PolicyTransactionsUiState.successWithLoading(cacheResult.data.map { mapper.mapToView(it) })
            } else {
                _uiState.value = PolicyTransactionsUiState.loading()
            }
        }
    }

}

class PolicyTransactionsUiState private constructor(
    val isLoading: Boolean,
    val financialTransactions: List<FinancialTransactionView>?,
    val showError: Event<Int>?
) {

    companion object {
        fun success(data: List<FinancialTransactionView>): PolicyTransactionsUiState {
            return PolicyTransactionsUiState(false, data, null)
        }

        fun successWithLoading(data: List<FinancialTransactionView>): PolicyTransactionsUiState {
            return PolicyTransactionsUiState(true, data, null)
        }

        fun loading(): PolicyTransactionsUiState {
            return PolicyTransactionsUiState(true, null, null)
        }
    }

    fun withLoading(): PolicyTransactionsUiState {
        return PolicyTransactionsUiState(true, financialTransactions, null)
    }

    fun withError(@StringRes errorStringRes: Int): PolicyTransactionsUiState {
        return PolicyTransactionsUiState(false, financialTransactions, Event(errorStringRes))
    }

}
