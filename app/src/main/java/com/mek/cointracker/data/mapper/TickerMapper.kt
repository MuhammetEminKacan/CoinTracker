package com.mek.cointracker.data.mapper

import com.mek.cointracker.data.remote.dtos.Ticker24hDto
import com.mek.cointracker.data.remote.dtos.TickerStreamDto
import com.mek.cointracker.domain.model.Coin
import com.mek.cointracker.domain.model.CoinDetail

fun Ticker24hDto.toCoin(): Coin {
    return Coin(
        symbol = symbol,
        lastPrice = lastPrice.toDoubleOrNull() ?: 0.0,
        priceChangePercent = priceChangePercent.toDoubleOrNull() ?: 0.0,
    )
}

fun Ticker24hDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        symbol = symbol,
        lastPrice = lastPrice.toDouble(),
        priceChangePercent = priceChangePercent.toDouble(),
        highPrice = highPrice.toDouble(),
        lowPrice = lowPrice.toDouble(),
        volume = volume.toDouble()
    )
}

fun TickerStreamDto.toCoin(): Coin {
    return Coin(
        symbol = symbol,
        lastPrice = lastPrice.toDoubleOrNull() ?: 0.0,
        priceChangePercent = priceChangePercent.toDoubleOrNull() ?: 0.0,
    )
}

fun TickerStreamDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        symbol = symbol,
        lastPrice = lastPrice.toDoubleOrNull() ?: 0.0,
        priceChangePercent = priceChangePercent.toDoubleOrNull() ?: 0.0,
        highPrice = highPrice.toDoubleOrNull() ?: 0.0,
        lowPrice = lowPrice.toDoubleOrNull() ?: 0.0,
        volume = volume.toDoubleOrNull() ?: 0.0
    )
}