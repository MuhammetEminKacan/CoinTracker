package com.mek.cointracker.domain.repository

import com.mek.cointracker.domain.model.Coin
import com.mek.cointracker.domain.model.CoinDetail
import com.mek.cointracker.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoins(symbols: List<String>): Flow<Resource<List<Coin>>>
    fun getCoin(symbol: String): Flow<Resource<CoinDetail>>

}