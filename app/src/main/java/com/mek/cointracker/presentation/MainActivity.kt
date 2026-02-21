package com.mek.cointracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.mek.cointracker.presentation.coin_detail_list.CoinDetailScreen
import com.mek.cointracker.presentation.coin_list.CoinListScreen
import com.mek.cointracker.presentation.navigation.CoinDetail
import com.mek.cointracker.presentation.navigation.CoinList
import com.mek.cointracker.presentation.navigation.Navigator
import com.mek.cointracker.presentation.navigation.rememberNavigationState
import com.mek.cointracker.presentation.navigation.toEntries
import com.mek.cointracker.presentation.ui.theme.CoinTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinTrackerTheme {
                //YENİ: Navigation state
                val navigationState = rememberNavigationState(
                    startRoute = CoinList,
                    topLevelRoutes = setOf(CoinList)
                )

                // YENİ: Navigator (NavController yerine)
                val navigator = remember {
                    Navigator(navigationState)
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavDisplay(
                        modifier = Modifier.padding(innerPadding),
                        entries = navigationState.toEntries { key ->
                            when (key) {

                                is CoinList -> NavEntry(key = key) {
                                    CoinListScreen(navigator = navigator)
                                }

                                is CoinDetail -> NavEntry(key = key) {
                                    CoinDetailScreen(
                                        symbol = key.symbol,
                                        onBackClick = {
                                            navigator.goBack()
                                        }
                                    )
                                }

                                else -> error("Unknown route: $key")
                            }
                        },
                        onBack = { navigator.goBack() }
                    )
                }

            }
        }
    }
}