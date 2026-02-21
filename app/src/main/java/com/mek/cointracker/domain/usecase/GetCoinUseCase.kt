package com.mek.cointracker.domain.usecase

import com.mek.cointracker.domain.model.CoinDetail
import com.mek.cointracker.domain.repository.CoinRepository
import com.mek.cointracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(symbol : String): Flow<Resource<CoinDetail>> {
        return repository.getCoin(symbol = symbol)
    }
}