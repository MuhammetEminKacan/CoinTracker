package com.mek.cointracker.data.remote.socket

import com.mek.cointracker.data.remote.dtos.StreamWrapperDto
import com.mek.cointracker.data.remote.dtos.TickerStreamDto
import com.mek.cointracker.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CoinSocketDataSource @Inject constructor(
    private val client: HttpClient
) {
    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun streamCoins(symbols: List<String>): Flow<TickerStreamDto> = flow {

        val streams = symbols
            .joinToString("/") { "${it.lowercase()}@ticker" }

        val url = "${Constants.WS_BASE_URL}${Constants.STREAM_PATH}$streams"

        client.webSocket(urlString = url) {

            for (frame in incoming) {
                if (frame is Frame.Text) {

                    val json = frame.readText()

                    val wrapper = jsonParser.decodeFromString<StreamWrapperDto>(json)

                    emit(wrapper.data)
                }
            }
        }
    }
}