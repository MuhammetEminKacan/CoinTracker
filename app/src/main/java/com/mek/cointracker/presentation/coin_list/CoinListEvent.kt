package com.mek.cointracker.presentation.coin_list

sealed class CoinListEvent {

    object LoadCoins : CoinListEvent()
    object Refresh : CoinListEvent()
    data class OnCoinClick(val symbol: String) : CoinListEvent()
    data class OnSearchQueryChange(val query: String) : CoinListEvent()
}