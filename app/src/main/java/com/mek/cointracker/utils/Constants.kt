package com.mek.cointracker.utils

object Constants {

    const val REST_BASE_URL = "https://api.binance.com"
    const val WS_BASE_URL = "wss://stream.binance.com:9443"

    const val TICKER_24H_ENDPOINT = "/api/v3/ticker/24hr"
    const val STREAM_PATH = "/stream?streams="

    val DEFAULT_SYMBOLS = listOf(
        "BTCUSDT",
        "ETHUSDT",
        "BNBUSDT",
        "SOLUSDT",
        "XRPUSDT",
        "ADAUSDT",
        "DOGEUSDT",
        "AVAXUSDT",
        "DOTUSDT",
        "MATICUSDT",
        "XRPUSDT",

    )

}