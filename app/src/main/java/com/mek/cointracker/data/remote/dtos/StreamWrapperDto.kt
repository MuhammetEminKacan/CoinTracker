package com.mek.cointracker.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class StreamWrapperDto(
    val stream: String,
    val data: TickerStreamDto
)
