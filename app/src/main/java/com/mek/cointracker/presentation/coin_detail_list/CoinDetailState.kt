package com.mek.cointracker.presentation.coin_detail_list

import com.mek.cointracker.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String? = null
)