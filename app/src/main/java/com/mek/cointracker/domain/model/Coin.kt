package com.mek.cointracker.domain.model

data class Coin(
    val symbol: String,
    val lastPrice: Double,
    val priceChangePercent: Double,
)
