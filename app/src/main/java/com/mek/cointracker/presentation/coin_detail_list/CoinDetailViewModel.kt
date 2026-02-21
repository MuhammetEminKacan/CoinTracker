package com.mek.cointracker.presentation.coin_detail_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mek.cointracker.domain.usecase.GetCoinUseCase
import com.mek.cointracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    private var currentSymbol: String? = null

    private var job: Job? = null

    fun onEvent(event: CoinDetailEvent) {
        when (event) {
            is CoinDetailEvent.LoadCoin -> {
                currentSymbol = event.symbol
                loadCoin(event.symbol)
            }
            CoinDetailEvent.Retry -> {
                currentSymbol?.let { loadCoin(it) }
            }
        }
    }

    private fun loadCoin(symbol: String) {
        job?.cancel()
        job = getCoinUseCase(symbol)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = CoinDetailState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = CoinDetailState(
                            coin = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = CoinDetailState(
                            error = result.message
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}