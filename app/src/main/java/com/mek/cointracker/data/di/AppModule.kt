package com.mek.cointracker.data.di

import com.mek.cointracker.data.remote.CoinApi
import com.mek.cointracker.data.remote.CoinApiImpl
import com.mek.cointracker.data.remote.socket.CoinSocketDataSource
import com.mek.cointracker.data.repository.CoinRepositoryImpl
import com.mek.cointracker.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import io.ktor.client.plugins.websocket.WebSockets

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = false
                    }
                )
            }

            install(WebSockets)

        }
    }

    @Provides
    @Singleton
    fun provideCoinApi(
        client: HttpClient
    ): CoinApi {
        return CoinApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        api: CoinApi,
        socket: CoinSocketDataSource
    ): CoinRepository {
        return CoinRepositoryImpl(api, socket)
    }

    @Provides
    @Singleton
    fun provideCoinSocketDataSource(
        client: HttpClient
    ): CoinSocketDataSource {
        return CoinSocketDataSource(client)
    }
}