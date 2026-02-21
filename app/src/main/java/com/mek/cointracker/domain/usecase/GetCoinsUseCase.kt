package com.mek.cointracker.domain.usecase

import com.mek.cointracker.domain.model.Coin
import com.mek.cointracker.domain.repository.CoinRepository
import com.mek.cointracker.utils.Constants
import com.mek.cointracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(): Flow<Resource<List<Coin>>> {
        return repository.getCoins(Constants.DEFAULT_SYMBOLS)
    }
}