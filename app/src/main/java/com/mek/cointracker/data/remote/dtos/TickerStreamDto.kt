package com.mek.cointracker.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TickerStreamDto(
    @SerialName("s") val symbol: String,
    @SerialName("c") val lastPrice: String,
    @SerialName("P") val priceChangePercent: String,
    @SerialName("h") val highPrice: String,
    @SerialName("l") val lowPrice: String,
    @SerialName("v") val volume: String
)
