package com.mek.cointracker.data.repository

import com.mek.cointracker.data.mapper.toCoin
import com.mek.cointracker.data.mapper.toCoinDetail
import com.mek.cointracker.data.remote.CoinApi
import com.mek.cointracker.data.remote.socket.CoinSocketDataSource
import com.mek.cointracker.domain.model.Coin
import com.mek.cointracker.domain.model.CoinDetail
import com.mek.cointracker.domain.repository.CoinRepository
import com.mek.cointracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi,
    private val socket: CoinSocketDataSource
) : CoinRepository {

    override fun getCoins(symbols: List<String>): Flow<Resource<List<Coin>>> = channelFlow {

        send(Resource.Loading())

        try {
            val snapshot = api.getTickers(symbols).map { it.toCoin() }

            val currentList = snapshot.toMutableList()

            send(Resource.Success(currentList.toList()))

            socket.streamCoins(symbols)
                .collect { streamDto ->

                    val updatedCoin = streamDto.toCoin()

                    val index = currentList.indexOfFirst { it.symbol == updatedCoin.symbol }

                    if (index != -1) {
                        currentList[index] = updatedCoin
                        send(Resource.Success(currentList.toList()))
                    }
                }

        } catch (e: Exception) {
            send(Resource.Error(e.message ?: "Socket error"))
        }
    }

    override fun getCoin(symbol: String): Flow<Resource<CoinDetail>> = channelFlow {

        send(Resource.Loading())

        try {
            val snapshot = api.getTicker(symbol).toCoinDetail()
            send(Resource.Success(snapshot))

            socket.streamCoins(listOf(symbol))
                .collect { streamDto ->
                    send(Resource.Success(streamDto.toCoinDetail()))
                }

        } catch (e: Exception) {
            send(Resource.Error(e.message ?: "Socket error"))
        }
    }
}