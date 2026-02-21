package com.mek.cointracker.presentation.coin_detail_list

sealed class CoinDetailEvent {
    data class LoadCoin(val symbol: String) : CoinDetailEvent()
    object Retry : CoinDetailEvent()
}