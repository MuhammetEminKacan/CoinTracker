package com.mek.cointracker.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mek.cointracker.domain.usecase.GetCoinsUseCase
import com.mek.cointracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state: StateFlow<CoinListState> = _state.asStateFlow()

    private val _navigation = MutableSharedFlow<String>()
    val navigation = _navigation.asSharedFlow()

    private var job: Job? = null

    fun onEvent(event: CoinListEvent) {
        when (event) {

            is CoinListEvent.LoadCoins -> {
                loadCoins()
            }

            is CoinListEvent.Refresh -> {
                loadCoins()
            }

            is CoinListEvent.OnCoinClick -> {
                viewModelScope.launch {
                    _navigation.emit(event.symbol)
                }
            }

            is CoinListEvent.OnSearchQueryChange -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }
        }
    }

    private fun loadCoins() {

        job?.cancel()

        job = getCoinsUseCase()
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Resource.Success -> {

                        val coins = result.data ?: emptyList()

                        _state.value = _state.value.copy(
                            isLoading = false,
                            coins = coins,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}