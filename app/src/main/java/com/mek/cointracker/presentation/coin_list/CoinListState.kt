package com.mek.cointracker.presentation.coin_list

import com.mek.cointracker.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val searchQuery: String = "",
    val error: String = ""
)