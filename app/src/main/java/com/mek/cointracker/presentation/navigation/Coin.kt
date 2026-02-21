package com.mek.cointracker.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object CoinList : NavKey

@Serializable
data class CoinDetail(
    val symbol: String
) : NavKey