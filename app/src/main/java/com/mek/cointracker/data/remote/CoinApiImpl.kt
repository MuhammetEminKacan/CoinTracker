package com.mek.cointracker.data.remote

import com.mek.cointracker.data.remote.dtos.Ticker24hDto
import com.mek.cointracker.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class CoinApiImpl @Inject constructor(
    private val client: HttpClient
) : CoinApi {

    override suspend fun getTickers(symbols: List<String>): List<Ticker24hDto> {

        val allTickers: List<Ticker24hDto> =
            client.get("${Constants.REST_BASE_URL}${Constants.TICKER_24H_ENDPOINT}")
                .body()

        return allTickers.filter { it.symbol in symbols }
    }

    override suspend fun getTicker(symbol: String): Ticker24hDto {
        return client.get("${Constants.REST_BASE_URL}${Constants.TICKER_24H_ENDPOINT}") {
            parameter("symbol", symbol)
        }.body()
    }
}