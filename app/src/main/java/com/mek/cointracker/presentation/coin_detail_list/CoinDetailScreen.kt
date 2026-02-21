package com.mek.cointracker.presentation.coin_detail_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mek.cointracker.R
import com.mek.cointracker.domain.model.CoinDetail

@Composable
fun CoinDetailScreen(
    symbol: String,
    onBackClick: () -> Unit,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val coin = state.coin

    LaunchedEffect(Unit) {
        viewModel.onEvent(CoinDetailEvent.LoadCoin(symbol))
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "Error",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

            coin != null -> {
                DetailContent(
                    coin = coin,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    coin: CoinDetail,
    onBackClick: () -> Unit
) {

    val isPositive = coin.priceChangePercent >= 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF2F2F2))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back_24px),
                    contentDescription = "Geri",
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Detay",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ÜST HERO ALAN
        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF7B61FF), Color(0xFF5E5CE6))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = coin.symbol.take(2),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = coin.symbol,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${coin.symbol}/USDT",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "$${coin.lastPrice}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(
                    if (isPositive) Color(0xFFDFF6E3)
                    else Color(0xFFFFE2E2)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "${if (isPositive) "↗" else "↘"} ${coin.priceChangePercent}%",
                color = if (isPositive) Color(0xFF00C853) else Color(0xFFD50000)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 24 SAAT İSTATİSTİK KARTI
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "24 Saat İstatistikleri",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                StatItem(
                    title = "En Yüksek",
                    value = "$${coin.highPrice}",
                    iconRes = R.drawable.trending_up_24px,
                    iconBackground = Color(0xFFDFF6E3)
                )
                Divider()

                StatItem(
                    title = "En Düşük",
                    value = "$${coin.lowPrice}",
                    iconRes = R.drawable.trending_down_24px,
                    iconBackground = Color(0xFFFFE2E2)
                )
                Divider()

                StatItem(
                    title = "İşlem Hacmi (24s)",
                    value = "$${coin.volume}",
                    iconRes = R.drawable.bar_chart_4_bars_24px,
                    iconBackground = Color(0xFFE3F2FD)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // FİYAT ARALIĞI
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "24 Saat Fiyat Aralığı",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                val range =
                    (coin.lastPrice - coin.lowPrice) /
                            (coin.highPrice - coin.lowPrice)

                LinearProgressIndicator(
                    progress = range.toFloat().coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Güncel: $${coin.lastPrice}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun StatItem(
    title: String,
    value: String,
    iconRes: Int,
    iconBackground: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = value,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}