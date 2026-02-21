package com.mek.cointracker.data.remote

import com.mek.cointracker.data.remote.dtos.Ticker24hDto

interface CoinApi {
    suspend fun getTickers(symbols: List<String>): List<Ticker24hDto>
    suspend fun getTicker(symbol: String): Ticker24hDto
}